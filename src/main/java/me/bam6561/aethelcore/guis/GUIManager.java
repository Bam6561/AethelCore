package me.bam6561.aethelcore.guis;

import me.bam6561.aethelcore.Plugin;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages inventories created by the {@link Plugin}, also known as {@link GUI GUIs}.
 * <p>
 * Each {@link GUI} is managed by a {@link GUIHandler}.
 *
 * @author Danny Nguyen
 * @version 0.0.10
 * @since 0.0.7
 */
public class GUIManager {
  /**
   * Managed inventories.
   */
  private final Map<Inventory, GUIHandler> guis = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public GUIManager() {
  }

  /**
   * Associates an inventory with a {@link GUIHandler}.
   *
   * @param inventory interacting inventory
   * @param handler   {@link GUIHandler}
   */
  public void registerGUI(@NotNull Inventory inventory, @NotNull GUIHandler handler) {
    Objects.requireNonNull(inventory, "Null inventory");
    Objects.requireNonNull(handler, "Null GUI handler");
    guis.put(inventory, handler);
  }

  /**
   * Disassociates an inventory from the manager.
   *
   * @param inventory interacting inventory
   */
  public void unregisterGUI(@NotNull Inventory inventory) {
    Objects.requireNonNull(inventory, "Null inventory");
    guis.remove(inventory);
  }

  /**
   * Handles {@link GUI} click actions.
   *
   * @param event inventory click event
   */
  public void handleClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null inventory click event");
    GUIHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onClick(event);
    }
  }

  /**
   * Handles {@link GUI} opening actions.
   *
   * @param event inventory open vent
   */
  public void handleOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null inventory open event");
    GUIHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onOpen(event);
    }
  }

  /**
   * Handles {@link GUI} closing actions.
   *
   * @param event inventory close event
   */
  public void handleClose(@NotNull InventoryCloseEvent event) {
    Objects.requireNonNull(event, "Null inventory close event");
    Inventory inventory = event.getInventory();
    GUIHandler handler = guis.get(inventory);
    if (handler != null) {
      handler.onClose(event);
      unregisterGUI(inventory);
    }
  }
}
