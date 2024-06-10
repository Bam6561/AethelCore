package me.bam6561.aethelcore;

import me.bam6561.aethelcore.interfaces.GuiHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages inventories created by the {@link Plugin}, also known as GUIs.
 *
 * @author Danny Nguyen
 * @version 0.0.9.1
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
  public void registerGui(Inventory inventory, GuiHandler handler) {
    guis.put(inventory, handler);
  }

  /**
   * Disassociates an inventory from the manager.
   *
   * @param inventory interacting inventory
   */
  public void unregisterGui(Inventory inventory) {
    guis.remove(inventory);
  }

  /**
   * Handles clicks for GUIs.
   *
   * @param event inventory click event
   */
  public void handleClick(InventoryClickEvent event) {
    GuiHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onClick(event);
    }
  }

  /**
   * Handles GUIs opening.
   *
   * @param event inventory open vent
   */
  public void handleOpen(InventoryOpenEvent event) {
    GuiHandler handler = guis.get(event.getInventory());
    if (handler != null) {
      handler.onOpen(event);
    }
  }

  /**
   * Handles GUIs closing.
   *
   * @param event inventory close event
   */
  public void handleClose(InventoryCloseEvent event) {
    Inventory gui = event.getInventory();
    GuiHandler handler = guis.get(gui);
    if (handler != null) {
      handler.onClose(event);
      unregisterGui(gui);
    }
  }
}
