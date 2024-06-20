package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.commands.DatabaseCommand;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Database;
import me.bam6561.aethelcore.guis.commands.markers.Paginated;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@link DatabaseCommand} {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.1.25
 * @since 0.0.15
 */
public class DatabaseGUI extends GUI implements Database, Paginated {
  /**
   * Associates the {@link GUI} with its user.
   *
   * @param user {@link GUI} user
   */
  public DatabaseGUI(@NotNull Player user) {
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
    return Bukkit.createInventory(null, 54, "Database");
  }

  /**
   * Currently does nothing.
   */
  @Override
  protected void addButtons() {
  }

  /**
   * Finishes {@link Plugin} interactions early if the user clicks
   * outside any inventories or uses their player inventory.
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
      return true;
    }
    return false;
  }

  /**
   * Currently does nothing.
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
