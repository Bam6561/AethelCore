package me.bam6561.aethelcore.guis.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@link me.bam6561.aethelcore.commands.DatabaseCommand} {@link Menu}.
 *
 * @author Danny Nguyen
 * @version 0.0.18
 * @since 0.0.15
 */
public class DatabaseMenu extends Menu {
  /**
   * No parameter constructor.
   */
  public DatabaseMenu() {
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
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null InventoryClickEvent");
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null InventoryOpenEvent");
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory close event
   */
  @Override
  public void onClose(@NotNull InventoryCloseEvent event) {
    Objects.requireNonNull(event, "Null InventoryCloseEvent");
  }
}
