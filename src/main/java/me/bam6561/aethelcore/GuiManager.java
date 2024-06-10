package me.bam6561.aethelcore;

import me.bam6561.aethelcore.interfaces.GuiHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages inventories created by the {@link Plugin}, also known as GUIs.
 * <p>
 * GUIs are managed by {@link GuiHandler GUI handlers}.
 *
 * @author Danny Nguyen
 * @version 0.0.10
 * @since 0.0.7
 */
public class GuiManager {
  /**
   * Managed inventories.
   */
  private final Map<Inventory, GuiHandler> guis = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public GuiManager() {
  }

  /**
   * Associates an inventory with a {@link GuiHandler}.
   *
   * @param inventory interacting inventory
   * @param handler   {@link GuiHandler}
   */
  public void registerGui(@NotNull Inventory inventory, @NotNull GuiHandler handler) {
    Objects.requireNonNull(inventory, "Null inventory");
    Objects.requireNonNull(handler, "Null GUI handler");
    guis.put(inventory, handler);
  }

  /**
   * Disassociates an inventory from the manager.
   *
   * @param inventory interacting inventory
   */
  public void unregisterGui(@NotNull Inventory inventory) {
    Objects.requireNonNull(inventory, "Null inventory");
    guis.remove(inventory);
  }

  /**
   * Handles GUI click actions.
   *
   * @param event inventory click event
   */
  public void handleClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null inventory click event");
    GuiHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onClick(event);
    }
  }

  /**
   * Handles GUI opening actions.
   *
   * @param event inventory open vent
   */
  public void handleOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null inventory open event");
    GuiHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onOpen(event);
    }
  }

  /**
   * Handles GUI closing actions.
   *
   * @param event inventory close event
   */
  public void handleClose(@NotNull InventoryCloseEvent event) {
    Objects.requireNonNull(event, "Null inventory close event");
    Inventory gui = event.getInventory();
    GuiHandler handler = guis.get(gui);
    if (handler != null) {
      handler.onClose(event);
      unregisterGui(gui);
    }
  }
}
