package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.references.Item;
import me.bam6561.aethelcore.utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Item durability {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.2.3
 * @since 0.2.3
 */
public class ItemDurabilityGUI extends GUI implements Editor {
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
}
