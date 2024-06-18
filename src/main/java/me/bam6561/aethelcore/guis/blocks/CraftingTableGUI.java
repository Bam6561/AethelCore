package me.bam6561.aethelcore.guis.blocks;

import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.blocks.markers.Workstation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Crafting table {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.1.8
 * @since 0.0.11
 */
public class CraftingTableGUI extends GUI implements Workstation {
  /**
   * Associates the {@link GUI} with its user.
   *
   * @param user {@link GUI} user
   */
  public CraftingTableGUI(@NotNull Player user) {
    super(user);
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, "Crafting Table");
  }

  /**
   * Currently does nothing.
   */
  @Override
  protected void addButtons() {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    Inventory cInv = event.getClickedInventory();
    if (cInv == null) {
      return;
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
}
