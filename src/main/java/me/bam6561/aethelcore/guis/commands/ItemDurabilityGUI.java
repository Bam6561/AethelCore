package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.guis.markers.MessageInputReceiver;
import me.bam6561.aethelcore.references.Item;
import me.bam6561.aethelcore.references.Message;
import me.bam6561.aethelcore.utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Item durability {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.2.5
 * @since 0.2.3
 */
public class ItemDurabilityGUI extends GUI implements Editor, MessageInputReceiver {
  /**
   * Interacting item.
   * <p>
   * To prevent editor desync, update the item object to refer to
   * the new item in the inventory whenever the item slot is updated.
   */
  private ItemStack item;

  /**
   * Interacting player.
   * <p>
   * To prevent user desync, update the player object to refer
   * to the player whenever the {@link GUI} is clicked.
   */
  private Player user;

  /**
   * Associates the {@link GUI} with its user and interacting item.
   * <p>
   * The inventory creates a new item object from the constructor's item parameter,
   * and all future references to the item refer to the inventory's copy.
   *
   * @param item interacting item
   */
  public ItemDurabilityGUI(@Nullable ItemStack item) {
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
    return Bukkit.createInventory(null, 54, "Item Durability");
  }

  /**
   * Adds item durability metadata buttons.
   */
  @Override
  protected void addButtons() {
    Inventory inv = getInventory();
    inv.setItem(2, ItemUtils.Create.createPluginPlayerHead(Item.PlayerHead.ARROW_UP_IRON_BLOCK, ChatColor.AQUA + "Item Editor"));
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
   *   <li>modifies item durability metadata
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
    this.user = (Player) event.getWhoClicked();
    switch (rawSlot) {
      case 2 -> new Interaction().openItemAppearanceGUI();
      case 4 -> new Interaction().setItemByClickedSlot(event);
      case 10 -> new Interaction().isDurabilityApplicable(event, this, Message.Input.DAMAGE);
      case 11 -> new Interaction().isDurabilityApplicable(event, this, Message.Input.DURABILITY);
      case 12 -> new Interaction().isDurabilityApplicable(event, this, Message.Input.MAX_DURABILITY);
      case 14 -> new Interaction().queryMessageInput(this, Message.Input.REPAIR_COST);
      case 15 -> new Interaction().toggleIsFireResistant();
      case 16 -> new Interaction().toggleIsUnbreakable();
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
   * Shows durability metadata buttons when an item is being edited.
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
   * Represent dynamic {@link Button buttons}.
   * <p>
   * Check if an item exists first before updating any {@link Button buttons}.
   *
   * @author Danny Nguyen
   * @version 0.2.5
   * @since 0.2.4
   */
  public class DynamicButtons {
    /**
     * {@link Button} positions.
     */
    private static final Set<Integer> POSITIONS = Set.of(10, 11, 12, 14, 15, 16);

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
      Display display = new Display();
      switch (button) {
        case DAMAGE -> inv.setItem(10, display.iconDamage());
        case DURABILITY -> inv.setItem(11, display.iconDurability());
        case MAX_DURABILITY -> inv.setItem(12, display.iconMaxDurability());
        case REPAIR_COST -> inv.setItem(14, display.iconRepairCost());
        case FIRE_RESISTANT -> inv.setItem(15, display.iconFireResistant());
        case UNBREAKABLE -> inv.setItem(16, display.iconUnbreakable());
      }
    }

    /**
     * Updates all {@link Button} {@link Display displays}.
     */
    private void updateAll() {
      Display display = new Display();
      inv.setItem(10, display.iconDamage());
      inv.setItem(11, display.iconDurability());
      inv.setItem(12, display.iconMaxDurability());

      inv.setItem(14, display.iconRepairCost());
      inv.setItem(15, display.iconFireResistant());
      inv.setItem(16, display.iconUnbreakable());
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
     * Item durability metadata.
     *
     * @author Danny Nguyen
     * @version 0.2.4
     * @since 0.2.4
     */
    public enum Button {
      /**
       * Durability damage.
       */
      DAMAGE,

      /**
       * Remaining durability.
       */
      DURABILITY,

      /**
       * Max durability.
       */
      MAX_DURABILITY,

      /**
       * Anvil repair cost.
       */
      REPAIR_COST,

      /**
       * Fire resistant.
       */
      FIRE_RESISTANT,

      /**
       * Unbreakable.
       */
      UNBREAKABLE
    }

    /**
     * Reads item durability metadata and returns representative display icons.
     *
     * @author Danny Nguyen
     * @version 0.2.5
     * @since 0.2.4
     */
    private class Display {
      /**
       * Item metadata.
       */
      private final ItemMeta meta = item.getItemMeta();

      /**
       * No parameter constructor.
       */
      private Display() {
      }

      /**
       * Creates an icon for the item's damage.
       *
       * @return damage icon
       */
      private ItemStack iconDamage() {
        short materialMaxDurability = item.getType().getMaxDurability();
        if (materialMaxDurability != 0 && meta instanceof Damageable damageable) {
          return ItemUtils.Create.createItem(Material.COBBLESTONE, ChatColor.AQUA + "Damage", List.of(ChatColor.WHITE + "" + damageable.getDamage()));
        }
        return ItemUtils.Create.createItem(Material.BARRIER, ChatColor.WHITE + "Not Damageable");
      }

      /**
       * Creates an icon for the item's durability.
       *
       * @return durability icon
       */
      private ItemStack iconDurability() {
        short materialMaxDurability = item.getType().getMaxDurability();
        if (materialMaxDurability != 0 && meta instanceof Damageable damageable) {
          if (damageable.hasMaxDamage()) {
            return ItemUtils.Create.createItem(Material.STONE, ChatColor.AQUA + "Durability", List.of(ChatColor.WHITE + "" + (damageable.getMaxDamage() - damageable.getDamage())));
          } else {
            return ItemUtils.Create.createItem(Material.STONE, ChatColor.AQUA + "Durability", List.of(ChatColor.WHITE + "" + (materialMaxDurability - damageable.getDamage())));
          }
        }
        return ItemUtils.Create.createItem(Material.BARRIER, ChatColor.WHITE + "Not Damageable");
      }

      /**
       * Creates an icon for the item's max durability.
       *
       * @return max durability icon
       */
      private ItemStack iconMaxDurability() {
        short materialMaxDurability = item.getType().getMaxDurability();
        if (materialMaxDurability != 0 && meta instanceof Damageable damageable) {
          if (damageable.hasMaxDamage()) {
            return ItemUtils.Create.createItem(Material.STONE_BRICKS, ChatColor.AQUA + "Max Durability", List.of(ChatColor.WHITE + "" + damageable.getMaxDamage()));
          } else {
            return ItemUtils.Create.createItem(Material.STONE_BRICKS, ChatColor.AQUA + "Max Durability", List.of(ChatColor.WHITE + "" + materialMaxDurability));
          }
        }
        return ItemUtils.Create.createItem(Material.BARRIER, ChatColor.WHITE + "Not Damageable");
      }

      /**
       * Creates an icon for the item's repair cost.
       *
       * @return repair cost icon
       */
      private ItemStack iconRepairCost() {
        if (meta instanceof Repairable repairable) {
          return ItemUtils.Create.createItem(Material.ANVIL, ChatColor.AQUA + "Repair Cost", List.of(ChatColor.WHITE + "" + repairable.getRepairCost()));
        }
        return ItemUtils.Create.createItem(Material.BARRIER, ChatColor.WHITE + "Not Repairable");
      }

      /**
       * Creates an icon for the item's fire-resistant.
       *
       * @return fire-resistant icon
       */
      private ItemStack iconFireResistant() {
        if (meta.isFireResistant()) {
          return ItemUtils.Create.createItem(Material.LAVA_BUCKET, ChatColor.AQUA + "Fire Resistant", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.LAVA_BUCKET, ChatColor.AQUA + "Fire Resistant");
        }
      }

      /**
       * Creates an icon for the item's unbreakable.
       *
       * @return unbreakable icon
       */
      private ItemStack iconUnbreakable() {
        if (meta.isUnbreakable()) {
          return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Unbreakable", List.of(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString()));
        } else {
          return ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Unbreakable");
        }
      }
    }
  }

  /**
   * {@link GUI} interaction.
   *
   * @author Danny Nguyen
   * @version 0.2.5
   * @since 0.2.4
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
        Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(item.clone()));
      } else {
        Plugin.getGUIManager().openGUI(user, new ItemEditorGUI(null));
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
     * Verifies that the item has a durability to
     * interact with before querying a message input.
     *
     * @param event    inventory click event
     * @param receiver {@link MessageInputReceiver}
     * @param input    {@link Message.Input}
     */
    private void isDurabilityApplicable(InventoryClickEvent event, MessageInputReceiver receiver, Message.Input input) {
      if (event.getCurrentItem().getType() != Material.BARRIER) {
        new Interaction().queryMessageInput(receiver, input);
      } else {
        user.sendMessage(Message.Error.NOT_APPLICABLE.asString());
      }
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
     * Toggles the item's fire-resistant.
     */
    private void toggleIsFireResistant() {
      ItemMeta meta = item.getItemMeta();
      if (meta.isFireResistant()) {
        meta.setFireResistant(false);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Fire Resistant" + ChatColor.GRAY + " Never");
      } else {
        meta.setFireResistant(true);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Fire Resistant" + ChatColor.GRAY + " Always");
      }
      item.setItemMeta(meta);
      new DynamicButtons().update(DynamicButtons.Button.FIRE_RESISTANT);
    }

    /**
     * Toggles the item's unbreakable.
     */
    private void toggleIsUnbreakable() {
      ItemMeta meta = item.getItemMeta();
      if (meta.isUnbreakable()) {
        meta.setUnbreakable(false);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Unbreakable" + ChatColor.GRAY + " Never");
      } else {
        meta.setUnbreakable(true);
        user.sendMessage(ChatColor.GREEN + Message.ASCII.CHECKMARK.asString() + " Unbreakable" + ChatColor.GRAY + " Always");
      }
      item.setItemMeta(meta);
      new DynamicButtons().update(DynamicButtons.Button.UNBREAKABLE);
    }
  }
}
