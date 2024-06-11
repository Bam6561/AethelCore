package me.bam6561.aethelcore.guis;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Describes basic interactions for an inventory.
 *
 * @author Danny Nguyen
 * @version 0.0.7
 * @since 0.0.7
 */
public interface InventoryHandler {
  /**
   * What to do when a click occurs.
   *
   * @param event inventory click event
   */
  void onClick(InventoryClickEvent event);

  /**
   * What to do when the inventory is opened.
   *
   * @param event inventory open event
   */
  void onOpen(InventoryOpenEvent event);

  /**
   * What to do when the inventory is closed.
   *
   * @param event inventory close event
   */
  void onClose(InventoryCloseEvent event);
}
