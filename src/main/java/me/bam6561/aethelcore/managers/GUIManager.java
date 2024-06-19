package me.bam6561.aethelcore.managers;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.InventoryHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages inventories created by the {@link Plugin}, also known as {@link GUI GUIs}.
 * <p>
 * Each {@link GUI} is managed by its {@link InventoryHandler} methods.
 *
 * @author Danny Nguyen
 * @version 0.1.18
 * @since 0.0.7
 */
public class GUIManager {
  /**
   * Active {@link GUI GUIs}.
   */
  private final Map<Inventory, InventoryHandler> activeGUIs = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public GUIManager() {
  }

  /**
   * Handles {@link GUI} click actions.
   *
   * @param event inventory click event
   */
  public void handleClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    InventoryHandler handler = activeGUIs.get(event.getInventory());
    if (handler != null) {
      handler.onClick(event);
    }
  }

  /**
   * Handles {@link GUI} drag actions.
   *
   * @param event inventory drag event
   */
  public void handleDrag(@NotNull InventoryDragEvent event) {
    Objects.requireNonNull(event, "Null event");
    InventoryHandler handler = activeGUIs.get(event.getInventory());
    if (handler != null) {
      handler.onDrag(event);
    }
  }

  /**
   * Handles {@link GUI} opening actions.
   *
   * @param event inventory open vent
   */
  public void handleOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null event");
    InventoryHandler handler = activeGUIs.get(event.getInventory());
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
    Objects.requireNonNull(event, "Null event");
    Inventory inventory = event.getInventory();
    InventoryHandler handler = activeGUIs.get(inventory);
    if (handler != null) {
      handler.onClose(event);
      activeGUIs.remove(inventory);
    }
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
    activeGUIs.put(inventory, gui);
    player.openInventory(inventory);
  }
}
