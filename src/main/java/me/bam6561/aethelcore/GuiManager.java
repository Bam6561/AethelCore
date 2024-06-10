package me.bam6561.aethelcore;

import me.bam6561.aethelcore.interfaces.GuiHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Registers inventories to GUIs that are managed by the {@link Plugin}.
 *
 * @author Danny Nguyen
 * @version 0.0.9
 * @since 0.0.7
 */
public class GuiManager {
  /**
   * Inventories managed by the {@link Plugin}.
   */
  private final Map<Inventory, GuiHandler> guis = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public GuiManager() {
  }

  /**
   * Pairs the {@link me.bam6561.aethelcore.interfaces.GUI} with its {@link GuiHandler}.
   *
   * @param gui     {@link me.bam6561.aethelcore.interfaces.GUI}
   * @param handler {@link GuiHandler}
   */
  public void registerGui(Inventory gui, GuiHandler handler) {
    guis.put(gui, handler);
  }

  /**
   * Stops managing the {@link me.bam6561.aethelcore.interfaces.GUI}.
   *
   * @param gui {@link me.bam6561.aethelcore.interfaces.GUI}.
   */
  public void unregisterGui(Inventory gui) {
    guis.remove(gui);
  }

  /**
   * Handles clicks for {@link me.bam6561.aethelcore.interfaces.GUI guis}.
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
   * Handles {@link me.bam6561.aethelcore.interfaces.GUI guis} opening.
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
   * Handles {@link me.bam6561.aethelcore.interfaces.GUI guis} closing.
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
