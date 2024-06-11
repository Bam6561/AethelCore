package me.bam6561.aethelcore.guis;

import me.bam6561.aethelcore.Plugin;
import org.bukkit.entity.Player;
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
 * Each {@link GUI} is managed by a {@link InventoryHandler}.
 *
 * @author Danny Nguyen
 * @version 0.0.16
 * @since 0.0.7
 */
public class GUIManager {
  /**
   * Managed inventories.
   */
  private final Map<Inventory, InventoryHandler> guis = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public GUIManager() {
  }

  /**
   * Opens a {@link GUI} for the interacting player.
   *
   * @param player interacting player
   * @param gui    {@link GUI}
   */
  public void openGUI(@NotNull Player player, @NotNull GUI gui) {
    Objects.requireNonNull(player, "Null player");
    Objects.requireNonNull(gui, "Null GUI");
    Inventory inventory = gui.getInventory();
    registerGUI(inventory, gui);
    player.openInventory(inventory);
  }

  /**
   * Handles {@link GUI} click actions.
   *
   * @param event inventory click event
   */
  public void handleClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null inventory click event");
    InventoryHandler handler = guis.get(event.getInventory());
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
    InventoryHandler handler = guis.get(event.getInventory());
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
    InventoryHandler handler = guis.get(inventory);
    if (handler != null) {
      handler.onClose(event);
      unregisterGUI(inventory);
    }
  }

  /**
   * Associates an inventory with an {@link InventoryHandler}.
   *
   * @param inventory interacting inventory
   * @param handler   {@link InventoryHandler}
   */
  private void registerGUI(Inventory inventory, InventoryHandler handler) {
    guis.put(inventory, handler);
  }

  /**
   * Disassociates an inventory from the manager.
   *
   * @param inventory interacting inventory
   */
  private void unregisterGUI(Inventory inventory) {
    guis.remove(inventory);
  }
}
