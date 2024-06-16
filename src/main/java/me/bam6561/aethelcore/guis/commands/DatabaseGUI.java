package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Database;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.guis.commands.markers.Paginated;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@link me.bam6561.aethelcore.commands.DatabaseCommand} {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.1.2
 * @since 0.0.15
 */
public class DatabaseGUI extends GUI implements Database, Editor, Paginated {
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
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null event");
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
