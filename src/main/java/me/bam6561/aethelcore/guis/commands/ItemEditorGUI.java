package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.commands.ItemEditorCommand;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * {@link ItemEditorCommand} {@link GUI}.
 * <p>
 * Collection of item metadata {@link Editor editors}.
 *
 * @author Danny Nguyen
 * @version 0.2.1
 * @since 0.0.27
 */
public class ItemEditorGUI extends GUI implements Editor {
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
  private Player player;

  /**
   * Associates the {@link GUI} with its user and interacting item.
   * <p>
   * The inventory creates a new item object from the constructor's item parameter,
   * and all future references to the item refer to the inventory's copy.
   *
   * @param item interacting item
   */
  public ItemEditorGUI(@Nullable ItemStack item) {
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
    return Bukkit.createInventory(null, 54, "Item Editor");
  }

  /**
   * Adds accessible {@link Editor editors}.
   */
  @Override
  protected void addButtons() {
    Inventory inv = getInventory();
    inv.setItem(10, ItemUtils.Create.createItem(Material.NAME_TAG, ChatColor.AQUA + "Appearance"));
    inv.setItem(11, ItemUtils.Create.createItem(Material.ANVIL, ChatColor.AQUA + "Durability"));
    inv.setItem(19, ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Attributes", ItemFlag.HIDE_ATTRIBUTES));
    inv.setItem(20, ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantments"));
    inv.setItem(21, ItemUtils.Create.createItem(Material.TNT, ChatColor.AQUA + "Skills"));

    inv.setItem(15, ItemUtils.Create.createItem(Material.PAPER, ChatColor.AQUA + "Stack Size"));
    inv.setItem(23, ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Potion Effects", ItemFlag.HIDE_ADDITIONAL_TOOLTIP));
    inv.setItem(24, ItemUtils.Create.createItem(Material.GOLDEN_APPLE, ChatColor.AQUA + "Food"));
    inv.setItem(25, ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Tool", ItemFlag.HIDE_ATTRIBUTES));
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
   *   <li>sets the item being edited
   *   <li>opens a {@link ItemAppearanceGUI}
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
    if (ItemUtils.Read.isNullOrAir(event.getCurrentItem())) {
      return;
    }
    this.player = (Player) event.getWhoClicked();
    switch (event.getRawSlot()) {
      case 4 -> new Interaction().setItemByClickedSlot(event);
      case 10 -> new Interaction().openItemAppearanceGUI();
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
   * Shows a "Save to Database" button when an item is being edited.
   */
  @Override
  public void updateDynamicButtons() {
    Inventory inv = getInventory();
    if (ItemUtils.Read.isNotNullOrAir(item)) {
      inv.setItem(40, ItemUtils.Create.createItem(Material.BOOKSHELF, ChatColor.AQUA + "Save to Database"));
    } else {
      inv.setItem(40, null);
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
   * {@link GUI} interaction.
   *
   * @author Danny Nguyen
   * @version 0.2.1
   * @since 0.1.26
   */
  private class Interaction {
    /**
     * No parameter constructor.
     */
    private Interaction() {
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
     * Opens an {@link ItemAppearanceGUI} with the item currently being edited.
     */
    private void openItemAppearanceGUI() {
      if (ItemUtils.Read.isNotNullOrAir(item)) {
        Plugin.getGUIManager().openGUI(player, new ItemAppearanceGUI(item.clone()));
      } else {
        Plugin.getGUIManager().openGUI(player, new ItemAppearanceGUI(null));
      }
    }
  }
}
