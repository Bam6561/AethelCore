package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.guis.markers.MessageInputReceiver;
import me.bam6561.aethelcore.utils.ItemUtils;
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
 * @version 0.1.10
 * @since 0.1.2
 */
public class ItemAppearanceGUI extends GUI implements Editor, MessageInputReceiver {
  /**
   * Interacting item.
   */
  private ItemStack item;

  /**
   * Associates the {@link GUI} with its user and interacting item.
   *
   * @param user {@link GUI} user
   * @param item interacting item
   */
  public ItemAppearanceGUI(@NotNull Player user, @Nullable ItemStack item) {
    super(user);
    this.item = item;
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
    inv.setItem(4, item);
    refreshDynamicButtons();
  }

  /**
   * Finishes {@link Plugin} interactions early if the user clicks
   * outside any inventories or uses their player inventory.
   * <p>
   * For shift-clicks inside the player inventory, the
   * {@link #item} will be set if no current {@link #item} exists.
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
        event.setCancelled(true);
        InventoryView view = event.getView();
        if (ItemUtils.Read.isNullOrAir(view.getItem(4))) {
          this.item = event.getCurrentItem();
          view.setItem(4, event.getCurrentItem());
          view.setItem(event.getRawSlot(), null);
          refreshDynamicButtons();
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
   *   <li>sets the {@link #item} being edited
   * </ul>
   * <p>
   * For player inventories, collecting to the cursor and shift clicking is prohibited.
   * Shift clicks are allowed when the {@link #item} slot is empty.
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

    switch (event.getRawSlot()) {
      case 2 -> {
        if (ItemUtils.Read.isNotNullOrAir(item)) {
          Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(user, item.clone()));
        } else {
          Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(user, item));
        }
      }
      case 4 -> {
        event.setCancelled(false);
        Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> {
          this.item = getInventory().getItem(4);
          refreshDynamicButtons();
        }, 1);
      }
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
   * Shows appearance metadata buttons when an {@link #item} is being edited.
   */
  @Override
  public void refreshDynamicButtons() {
    Inventory inv = getInventory();
    if (ItemUtils.Read.isNullOrAir(item)) {
      Set<Integer> empty = Set.of(
          10, 11, 12,
          20, 28, 29, 30, 37, 38, 39,
          15, 23, 24, 25, 32, 33, 34, 41, 42, 43);
      for (int slot : empty) {
        inv.setItem(slot, null);
      }
      return;
    }

    MetaDisplay metaDisplay = new MetaDisplay(item.getItemMeta());
    inv.setItem(10, metaDisplay.iconDisplayName());
    inv.setItem(11, metaDisplay.iconCustomModelData());
    inv.setItem(12, metaDisplay.iconEnchantmentGlintOverride());

    inv.setItem(20, metaDisplay.iconLore());
    inv.setItem(28, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Add"));
    inv.setItem(29, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Insert"));
    inv.setItem(30, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Set"));
    inv.setItem(37, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Edit"));
    inv.setItem(38, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Remove"));
    inv.setItem(39, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Clear"));

    inv.setItem(15, ItemUtils.Create.createItem(Material.WHITE_BANNER, ChatColor.AQUA + "Item Flags"));
    inv.setItem(23, metaDisplay.iconItemFlagHideAdditionalToolTip());
    inv.setItem(24, metaDisplay.iconItemFlagHideArmorTrim());
    inv.setItem(25, metaDisplay.iconItemFlagHideAttributes());
    inv.setItem(32, metaDisplay.iconItemFlagHideDestroys());
    inv.setItem(33, metaDisplay.iconItemFlagHideDye());
    inv.setItem(34, metaDisplay.iconItemFlagHideEnchants());
    inv.setItem(41, metaDisplay.iconItemFlagHidePlacedOn());
    inv.setItem(42, metaDisplay.iconItemFlagHideUnbreakable());
    inv.setItem(43, metaDisplay.iconHideTooltip());
  }

  /**
   * Reads item appearance metadata and returns representative display icons.
   *
   * @param meta item metadata
   * @author Danny Nguyen
   * @version 0.1.17
   * @since 0.1.5
   */
  private record MetaDisplay(ItemMeta meta) {
    /**
     * Sets the item metadata to be read.
     */
    private MetaDisplay {
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
        return ItemUtils.Create.createItem(Material.SPYGLASS, ChatColor.AQUA + "Custom Model Data", List.of(String.valueOf(meta.getCustomModelData())));
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
        return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override", List.of(ChatColor.RED + "False"));
      }
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
          lore.set(i, ChatColor.WHITE + "" + i + " " + lore.get(i));
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
    private ItemStack iconItemFlagHideAdditionalToolTip() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) {
        return ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Hide Additional Tooltip", List.of(ChatColor.GREEN + "True"), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
      } else {
        return ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Hide Additional Tooltip", List.of(ChatColor.RED + "False"), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
      }
    }

    /**
     * Creates an icon for the item's hide armor trim item flag.
     *
     * @return hide armor trim item flag icon
     */
    private ItemStack iconItemFlagHideArmorTrim() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ARMOR_TRIM)) {
        return ItemUtils.Create.createItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.AQUA + "Hide Armor Trim", List.of(ChatColor.GREEN + "True"), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
      } else {
        return ItemUtils.Create.createItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.AQUA + "Hide Armor Trim", List.of(ChatColor.RED + "False"), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
      }
    }

    /**
     * Creates an icon for the item's hide attributes item flag.
     *
     * @return hide attributes item flag icon
     */
    private ItemStack iconItemFlagHideAttributes() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
        return ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Hide Attributes", List.of(ChatColor.GREEN + "True"), ItemFlag.HIDE_ATTRIBUTES);
      } else {
        return ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Hide Attributes", List.of(ChatColor.RED + "False"), ItemFlag.HIDE_ATTRIBUTES);
      }
    }

    /**
     * Creates an icon for the item's hide destroys item flag.
     *
     * @return hide destroys item flag icon
     */
    private ItemStack iconItemFlagHideDestroys() {
      if (meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)) {
        return ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Hide Destroys", List.of(ChatColor.GREEN + "True"), ItemFlag.HIDE_ATTRIBUTES);
      } else {
        return ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Hide Destroys", List.of(ChatColor.RED + "False"), ItemFlag.HIDE_ATTRIBUTES);
      }
    }

    /**
     * Creates an icon for the item's hide dye item flag.
     *
     * @return hide dye item flag icon
     */
    private ItemStack iconItemFlagHideDye() {
      if (meta.hasItemFlag(ItemFlag.HIDE_DYE)) {
        return ItemUtils.Create.createItem(Material.RED_DYE, ChatColor.AQUA + "Hide Dye", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.RED_DYE, ChatColor.AQUA + "Hide Dye", List.of(ChatColor.RED + "False"));
      }
    }

    /**
     * Creates an icon for the item's hide enchants item flag.
     *
     * @return hide enchants item flag icon
     */
    private ItemStack iconItemFlagHideEnchants() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) {
        return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchants", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchants", List.of(ChatColor.RED + "False"));
      }
    }

    /**
     * Creates an icon for the item's hide placed on item flag.
     *
     * @return hide placed on flag icon
     */
    private ItemStack iconItemFlagHidePlacedOn() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) {
        return ItemUtils.Create.createItem(Material.GRASS_BLOCK, ChatColor.AQUA + "Placed On", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.GRASS_BLOCK, ChatColor.AQUA + "Placed On", List.of(ChatColor.RED + "False"));
      }
    }

    /**
     * Creates an icon for the item's hide unbreakable item flag.
     *
     * @return hide unbreakable item flag icon
     */
    private ItemStack iconItemFlagHideUnbreakable() {
      if (meta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)) {
        return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Unbreakable", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Unbreakable", List.of(ChatColor.RED + "False"));
      }
    }

    /**
     * Creates an icon for the item's hide tooltip.
     *
     * @return hide tooltip icon
     */
    private ItemStack iconHideTooltip() {
      if (meta.isHideTooltip()) {
        return ItemUtils.Create.createItem(Material.INK_SAC, ChatColor.AQUA + "Hide Tooltip", List.of(ChatColor.GREEN + "True"));
      } else {
        return ItemUtils.Create.createItem(Material.INK_SAC, ChatColor.AQUA + "Hide Tooltip", List.of(ChatColor.RED + "False"));
      }
    }
  }
}
