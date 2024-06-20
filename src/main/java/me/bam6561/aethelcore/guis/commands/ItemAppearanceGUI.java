package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.guis.markers.MessageInputReceiver;
import me.bam6561.aethelcore.references.Message;
import me.bam6561.aethelcore.utils.ItemUtils;
import me.bam6561.aethelcore.utils.TextUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Item appearance {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.2.0
 * @since 0.1.2
 */
public class ItemAppearanceGUI extends GUI implements Editor, MessageInputReceiver {
  /**
   * Interacting item.
   * <p>
   * To prevent editor desync, update the item object to refer to
   * the new item in the inventory whenever the item slot is updated.
   */
  private ItemStack item;

  /**
   * Associates the {@link GUI} with its user and interacting item.
   * <p>
   * The inventory creates a new item object from the constructor's item parameter,
   * and all future references to the item refer to the inventory's copy.
   *
   * @param user {@link GUI} user
   * @param item interacting item
   */
  public ItemAppearanceGUI(@NotNull Player user, @Nullable ItemStack item) {
    super(user);
    Inventory inv = getInventory();
    inv.setItem(4, item);
    this.item = inv.getItem(4);
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, "Item Appearance");
  }

  /**
   * Adds item appearance metadata buttons.
   */
  @Override
  protected void addButtons() {
    Inventory inv = getInventory();
    inv.setItem(2, ItemUtils.Create.createItem(Material.POTATO, ChatColor.AQUA + "Item Editor"));
    updateDynamicButtons();
  }

  /**
   * Finishes {@link Plugin} interactions early if the user clicks
   * outside any inventories or uses their player inventory.
   * <p>
   * For shift-clicks inside the player inventory, the
   * item will be set if no current item exists.
   * <p>
   * Disabled player inventory interactions:
   * <ul>
   *   <li>shift click
   *   <li>collect to cursor
   * </ul>
   *
   * @param event inventory click event
   * @return finished interaction
   */
  @Override
  protected boolean isNullOrPlayerInventoryClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    Inventory cInv = event.getClickedInventory();
    if (cInv == null) {
      return true;
    }
    if (cInv.getType() == InventoryType.PLAYER) {
      if (event.getClick().isShiftClick()) {
        ItemStack clickedItem = event.getCurrentItem();
        if (ItemUtils.Read.isNullOrAir(clickedItem)) {
          return true;
        }
        event.setCancelled(true);
        InventoryView view = event.getView();
        if (ItemUtils.Read.isNullOrAir(view.getItem(4))) {
          view.setItem(4, clickedItem.clone());
          view.setItem(event.getRawSlot(), null);
          this.item = view.getItem(4);
          updateDynamicButtons();
        }
      } else if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
        event.setCancelled(true);
      }
      return true;
    }
    return false;
  }

  /**
   * Either:
   * <ul>
   *   <li>opens a {@link ItemEditorGUI}
   *   <li>sets the item being edited
   * </ul>
   * <p>
   * For player inventories, collecting to the cursor and shift clicking is prohibited.
   * Shift clicks are allowed when the item slot is empty.
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    if (isNullOrPlayerInventoryClick(event)) {
      return;
    }
    event.setCancelled(true);
    int rawSlot = event.getRawSlot();
    if (ItemUtils.Read.isNullOrAir(event.getCurrentItem())) {
      if (rawSlot == 4) {
        new Interaction().setItemByClickedSlot(event);
      }
      return;
    }

    switch (event.getRawSlot()) {
      case 2 -> new Interaction().openItemAppearanceGUI();
      case 4 -> new Interaction().setItemByClickedSlot(event);
      case 10 -> new Interaction().queryMessageInput(this, Message.Input.DISPLAY_NAME);
      case 11 -> new Interaction().queryMessageInput(this, Message.Input.CUSTOM_MODEL_DATA);
      case 12 -> new Interaction().toggleEnchantmentGlintOverride();
      case 28 -> new Interaction().queryMessageInput(this, Message.Input.LORE_ADD);
      case 29 -> new Interaction().queryMessageInput(this, Message.Input.LORE_INSERT);
      case 30 -> new Interaction().queryMessageInput(this, Message.Input.LORE_SET);
      case 37 -> new Interaction().queryMessageInput(this, Message.Input.LORE_EDIT);
      case 38 -> new Interaction().queryMessageInput(this, Message.Input.LORE_REMOVE);
      case 39 -> new Interaction().clearLore();
      case 23 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, DynamicButtons.Button.ITEM_FLAG_HIDE_ADDITIONAL_TOOLTIP);
      case 24 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_ARMOR_TRIM, DynamicButtons.Button.ITEM_FLAG_HIDE_ARMOR_TRIM);
      case 25 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_ATTRIBUTES, DynamicButtons.Button.ITEM_FLAG_HIDE_ATTRIBUTES);
      case 32 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_DESTROYS, DynamicButtons.Button.ITEM_FLAG_HIDE_DESTROYS);
      case 33 -> new Interaction().toggleItemFlag(ItemFlag.HIDE_DYE, DynamicButtons.Button.ITEM_FLAG_HIDE_DYE);
      case 34 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_ENCHANTS, DynamicButtons.Button.ITEM_FLAG_HIDE_ENCHANTS);
      case 41 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_PLACED_ON, DynamicButtons.Button.ITEM_FLAG_HIDE_PLACED_ON);
      case 42 ->
          new Interaction().toggleItemFlag(ItemFlag.HIDE_UNBREAKABLE, DynamicButtons.Button.ITEM_FLAG_HIDE_UNBREAKABLE);
      case 43 -> new Interaction().toggleHideTooltip();
    }
  }

  /**
   * Cancels dragging items while the inventory is open.
   *
   * @param event inventory drag event
   */
  @Override
  public void onDrag(@NotNull InventoryDragEvent event) {
    Objects.requireNonNull(event, "Null event");
    event.setCancelled(true);
  }

  /**
   * Adds buttons to the {@link GUI}.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null event");
    addButtons();
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory close event
   */
  @Override
  public void onClose(@NotNull InventoryCloseEvent event) {
    Objects.requireNonNull(event, "Null event");
  }

  /**
   * Shows appearance metadata buttons when an item is being edited.
   */
  @Override
  public void updateDynamicButtons() {
    DynamicButtons buttons = new DynamicButtons();
    if (ItemUtils.Read.isNotNullOrAir(item)) {
      buttons.updateAll();
    } else {
      buttons.clearAll();
    }
  }

  /**
   * Gets the item being edited.
   *
   * @return item being edited
   */
  @Nullable
  @Override
  public ItemStack getItem() {
    return this.item;
  }

  /**
   * Represents dynamic {@link Button buttons}.
   * <p>
   * Check if an item exists first before updating any {@link Button buttons}.
   *
   * @author Danny Nguyen
   * @version 0.1.25
   * @since 0.1.22
   */
  public class DynamicButtons {
    /**
     * {@link Button} positions.
     */
    private static final Set<Integer> POSITIONS = Set.of(10, 11, 12, 20, 28, 29, 30, 37, 38, 39, 15, 23, 24, 25, 32, 33, 34, 41, 42, 43);

    /**
     * {@link GUI} inventory.
     */
    private final Inventory inv = getInventory();

    /**
     * No parameter constructor.
     */
    public DynamicButtons() {
    }

    /**
     * Updates the {@link Button button's} {@link Display}.
     *
     * @param button {@link Button}
     */
    public void update(DynamicButtons.Button button) {
      Display display = new Display(item.getItemMeta());
      switch (button) {
        case DISPLAY_NAME -> inv.setItem(10, display.iconDisplayName());
        case CUSTOM_MODEL_DATA -> inv.setItem(11, display.iconCustomModelData());
        case LORE -> inv.setItem(20, display.iconLore());
        case ENCHANTMENT_GLINT_OVERRIDE -> inv.setItem(12, display.iconEnchantmentGlintOverride());
        case ITEM_FLAG_HIDE_ADDITIONAL_TOOLTIP -> inv.setItem(23, display.iconItemFlagHideAdditionalTooltip());
        case ITEM_FLAG_HIDE_ARMOR_TRIM -> inv.setItem(24, display.iconItemFlagHideArmorTrim());
        case ITEM_FLAG_HIDE_ATTRIBUTES -> inv.setItem(25, display.iconItemFlagHideAttributes());
        case ITEM_FLAG_HIDE_DESTROYS -> inv.setItem(32, display.iconItemFlagHideDestroys());
        case ITEM_FLAG_HIDE_DYE -> inv.setItem(33, display.iconItemFlagHideDye());
        case ITEM_FLAG_HIDE_ENCHANTS -> inv.setItem(34, display.iconItemFlagHideEnchants());
        case ITEM_FLAG_HIDE_PLACED_ON -> inv.setItem(41, display.iconItemFlagHidePlacedOn());
        case ITEM_FLAG_HIDE_UNBREAKABLE -> inv.setItem(42, display.iconItemFlagHideUnbreakable());
        case HIDE_TOOLTIP -> inv.setItem(43, display.iconHideTooltip());
      }
    }

    /**
     * Updates all {@link Button} {@link Display displays}.
     */
    private void updateAll() {
      Display display = new Display(item.getItemMeta());
      inv.setItem(10, display.iconDisplayName());
      inv.setItem(11, display.iconCustomModelData());
      inv.setItem(12, display.iconEnchantmentGlintOverride());

      inv.setItem(20, display.iconLore());
      inv.setItem(28, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Add"));
      inv.setItem(29, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Insert"));
      inv.setItem(30, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Set"));
      inv.setItem(37, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Edit"));
      inv.setItem(38, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Remove"));
      inv.setItem(39, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Clear"));

      inv.setItem(15, ItemUtils.Create.createItem(Material.WHITE_BANNER, ChatColor.AQUA + "Item Flags"));
      inv.setItem(23, display.iconItemFlagHideAdditionalTooltip());
      inv.setItem(24, display.iconItemFlagHideArmorTrim());
      inv.setItem(25, display.iconItemFlagHideAttributes());
      inv.setItem(32, display.iconItemFlagHideDestroys());
      inv.setItem(33, display.iconItemFlagHideDye());
      inv.setItem(34, display.iconItemFlagHideEnchants());
      inv.setItem(41, display.iconItemFlagHidePlacedOn());
      inv.setItem(42, display.iconItemFlagHideUnbreakable());
      inv.setItem(43, display.iconHideTooltip());
    }

    /**
     * Clears all {@link Button buttons}.
     */
    private void clearAll() {
      for (int slot : POSITIONS) {
        inv.setItem(slot, null);
      }
    }

    /**
     * Item appearance metadata.
     *
     * @author Danny Nguyen
     * @version 0.1.25
     * @since 0.1.25
     */
    public enum Button {
      /**
       * Display name.
       */
      DISPLAY_NAME,

      /**
       * Custom model data.
       */
      CUSTOM_MODEL_DATA,

      /**
       * Lore.
       */
      LORE,

      /**
       * Enchantment glint override.
       */
      ENCHANTMENT_GLINT_OVERRIDE,

      /**
       * Hide additional tooltip item flag.
       */
      ITEM_FLAG_HIDE_ADDITIONAL_TOOLTIP,

      /**
       * Hide armor trim item flag.
       */
      ITEM_FLAG_HIDE_ARMOR_TRIM,

      /**
       * Hide attributes item flag.
       */
      ITEM_FLAG_HIDE_ATTRIBUTES,

      /**
       * Hide destroys item flag.
       */
      ITEM_FLAG_HIDE_DESTROYS,

      /**
       * Hide dye item flag.
       */
      ITEM_FLAG_HIDE_DYE,

      /**
       * Hide enchants item flag.
       */
      ITEM_FLAG_HIDE_ENCHANTS,

      /**
       * Hide placed on item flag.
       */
      ITEM_FLAG_HIDE_PLACED_ON,

      /**
       * Hide unbreakable item flag.
       */
      ITEM_FLAG_HIDE_UNBREAKABLE,

      /**
       * Hide tooltip.
       */
      HIDE_TOOLTIP;
    }

    /**
     * Reads item appearance metadata and returns representative display icons.
     *
     * @param meta item metadata
     * @author Danny Nguyen
     * @version 0.2.0
     * @since 0.1.5
     */
    private record Display(ItemMeta meta) {
      /**
       * Sets the item metadata to be read.
       */
      private Display {
      }

      /**
       * Creates an icon for the item's display name.
       *
       * @return display name icon
       */
      private ItemStack iconDisplayName() {
        if (meta.hasDisplayName()) {
          return ItemUtils.Create.createItem(Material.NAME_TAG, ChatColor.AQUA + "Display Name", List.of(meta.getDisplayName()));
        } else {
          return ItemUtils.Create.createItem(Material.NAME_TAG, ChatColor.AQUA + "Display Name");
        }
      }

      /**
       * Creates an icon for the item's custom model data.
       *
       * @return custom model data icon
       */
      private ItemStack iconCustomModelData() {
        if (meta.hasCustomModelData()) {
          return ItemUtils.Create.createItem(Material.SPYGLASS, ChatColor.AQUA + "Custom Model Data", List.of(ChatColor.WHITE + String.valueOf(meta.getCustomModelData())));
        } else {
          return ItemUtils.Create.createItem(Material.SPYGLASS, ChatColor.AQUA + "Custom Model Data");
        }
      }

      /**
       * Creates an icon for the item's enchantment glint override.
       *
       * @return enchantment glint override icon
       */
      private ItemStack iconEnchantmentGlintOverride() {
        if (meta.hasEnchantmentGlintOverride()) {
          if (meta.getEnchantmentGlintOverride()) {
            return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
          } else {
            return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override", List.of(ChatColor.RED + Message.ASCII.CROSS_MARK.asString()));
          }
        }
        return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override");
      }

      /**
       * Creates an icon for the item's lore.
       *
       * @return lore icon
       */
      private ItemStack iconLore() {
        if (meta.hasLore()) {
          List<String> lore = meta.getLore();
          for (int i = 0; i < lore.size(); i++) {
            lore.set(i, ChatColor.LIGHT_PURPLE + "" + (i + 1) + " " + ChatColor.WHITE + lore.get(i));
          }
          return ItemUtils.Create.createItem(Material.BOOK, ChatColor.WHITE + "Lore", lore);
        } else {
          return ItemUtils.Create.createItem(Material.BOOK, ChatColor.WHITE + "Lore");
        }
      }

      /**
       * Creates an icon for the item's hide additional tooltip item flag.
       *
       * @return hide additional tooltip item flag icon
       */
      private ItemStack iconItemFlagHideAdditionalTooltip() {
        if (meta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) {
          return ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Hide Additional Tooltip", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        } else {
          return ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Hide Additional Tooltip", ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        }
      }

      /**
       * Creates an icon for the item's hide armor trim item flag.
       *
       * @return hide armor trim item flag icon
       */
      private ItemStack iconItemFlagHideArmorTrim() {
        if (meta.hasItemFlag(ItemFlag.HIDE_ARMOR_TRIM)) {
          return ItemUtils.Create.createItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.AQUA + "Hide Armor Trim", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        } else {
          return ItemUtils.Create.createItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.AQUA + "Hide Armor Trim", ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        }
      }

      /**
       * Creates an icon for the item's hide attributes item flag.
       *
       * @return hide attributes item flag icon
       */
      private ItemStack iconItemFlagHideAttributes() {
        if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
          return ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Hide Attributes", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()), ItemFlag.HIDE_ATTRIBUTES);
        } else {
          return ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Hide Attributes", ItemFlag.HIDE_ATTRIBUTES);
        }
      }

      /**
       * Creates an icon for the item's hide destroys item flag.
       *
       * @return hide destroys item flag icon
       */
      private ItemStack iconItemFlagHideDestroys() {
        if (meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)) {
          return ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Hide Destroys", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()), ItemFlag.HIDE_ATTRIBUTES);
        } else {
          return ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Hide Destroys", ItemFlag.HIDE_ATTRIBUTES);
        }
      }

      /**
       * Creates an icon for the item's hide dye item flag.
       *
       * @return hide dye item flag icon
       */
      private ItemStack iconItemFlagHideDye() {
        if (meta.hasItemFlag(ItemFlag.HIDE_DYE)) {
          return ItemUtils.Create.createItem(Material.RED_DYE, ChatColor.AQUA + "Hide Dye", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.RED_DYE, ChatColor.AQUA + "Hide Dye");
        }
      }

      /**
       * Creates an icon for the item's hide enchants item flag.
       *
       * @return hide enchants item flag icon
       */
      private ItemStack iconItemFlagHideEnchants() {
        if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
          return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Hide Enchants", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Hide Enchants");
        }
      }

      /**
       * Creates an icon for the item's hide placed on item flag.
       *
       * @return hide placed on flag icon
       */
      private ItemStack iconItemFlagHidePlacedOn() {
        if (meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
          return ItemUtils.Create.createItem(Material.GRASS_BLOCK, ChatColor.AQUA + "Hide Placed On", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.GRASS_BLOCK, ChatColor.AQUA + "Hide Placed On");
        }
      }

      /**
       * Creates an icon for the item's hide unbreakable item flag.
       *
       * @return hide unbreakable item flag icon
       */
      private ItemStack iconItemFlagHideUnbreakable() {
        if (meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
          return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Hide Unbreakable", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Hide Unbreakable");
        }
      }

      /**
       * Creates an icon for the item's hide tooltip.
       *
       * @return hide tooltip icon
       */
      private ItemStack iconHideTooltip() {
        if (meta.isHideTooltip()) {
          return ItemUtils.Create.createItem(Material.INK_SAC, ChatColor.AQUA + "Hide Tooltip", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.INK_SAC, ChatColor.AQUA + "Hide Tooltip");
        }
      }
    }
  }

  /**
   * {@link GUI} interaction.
   *
   * @author Danny Nguyen
   * @version 0.2.0
   * @since 0.1.26
   */
  private class Interaction {
    /**
     * No parameter constructor.
     */
    private Interaction() {
    }

    /**
     * Opens an {@link ItemEditorGUI} with the item currently being edited.
     */
    private void openItemAppearanceGUI() {
      if (ItemUtils.Read.isNotNullOrAir(item)) {
        Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(user, item.clone()));
      } else {
        Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(user, null));
      }
    }

    /**
     * Sets the item currently being edited.
     *
     * @param event inventory click event
     */
    private void setItemByClickedSlot(InventoryClickEvent event) {
      event.setCancelled(false);
      Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> {
        item = getInventory().getItem(4);
        updateDynamicButtons();
      }, 1);
    }

    /**
     * Queries a message input and closes the inventory so the user can respond.
     *
     * @param receiver {@link MessageInputReceiver}
     * @param input    {@link Message.Input}
     */
    private void queryMessageInput(MessageInputReceiver receiver, Message.Input input) {
      Plugin.getMessageManager().queryMessageInput(user, receiver, input);
      user.closeInventory();
    }

    /**
     * Toggles the item's item flag and updates its display.
     *
     * @param itemFlag item flag
     * @param button   {@link DynamicButtons.Button}
     */
    private void toggleItemFlag(ItemFlag itemFlag, DynamicButtons.Button button) {
      ItemMeta meta = item.getItemMeta();
      boolean hasFlagBefore = meta.hasItemFlag(itemFlag);
      if (hasFlagBefore) {
        meta.removeItemFlags(itemFlag);
      } else {
        meta.addItemFlags(itemFlag);
      }
      item.setItemMeta(meta);
      boolean hasFlagAfter = item.getItemMeta().hasItemFlag(itemFlag);
      if (hasFlagBefore != hasFlagAfter) {
        if (hasFlagAfter) {
          user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " " + TextUtils.Format.asTitle(itemFlag.name()) + ChatColor.GRAY + " Always");
        } else {
          user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " " + TextUtils.Format.asTitle(itemFlag.name()) + ChatColor.GRAY + " Never");
        }
      } else {
        user.sendMessage(ChatColor.RED + Message.ASCII.CROSS_MARK.asString() + " " + TextUtils.Format.asTitle(itemFlag.name()) + " Not Applicable");
      }
      new DynamicButtons().update(button);
    }

    /**
     * Toggles the item's enchantment glint override.
     */
    private void toggleEnchantmentGlintOverride() {
      ItemMeta meta = item.getItemMeta();
      if (meta.hasEnchantmentGlintOverride()) {
        if (meta.getEnchantmentGlintOverride()) {
          meta.setEnchantmentGlintOverride(false);
          user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Enchantment Glint" + ChatColor.GRAY + " Never");
        } else {
          meta.setEnchantmentGlintOverride(null);
          user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Enchantment Glint" + ChatColor.GRAY + " Default");
        }
      } else {
        meta.setEnchantmentGlintOverride(true);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Enchantment Glint" + ChatColor.GRAY + " Always");
      }
      item.setItemMeta(meta);
      new DynamicButtons().update(DynamicButtons.Button.ENCHANTMENT_GLINT_OVERRIDE);
    }

    /**
     * Clears the item's lore.
     */
    private void clearLore() {
      ItemMeta meta = item.getItemMeta();
      meta.setLore(null);
      item.setItemMeta(meta);
      user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Cleared Lore");
      new DynamicButtons().update(DynamicButtons.Button.LORE);
    }

    /**
     * Toggles the item's tooltip.
     */
    private void toggleHideTooltip() {
      ItemMeta meta = item.getItemMeta();
      if (meta.isHideTooltip()) {
        meta.setHideTooltip(false);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Hide Tooltip" + ChatColor.GRAY + " Never");
      } else {
        meta.setHideTooltip(true);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Hide Tooltip" + ChatColor.GRAY + " Always");
      }
      item.setItemMeta(meta);
      new DynamicButtons().update(DynamicButtons.Button.HIDE_TOOLTIP);
    }
  }
}