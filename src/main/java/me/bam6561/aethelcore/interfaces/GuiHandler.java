package me.bam6561.aethelcore.interfaces;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Represents base behavior for a GUI.
 *
 * @author Danny Nguyen
 * @version 0.0.7
 * @since 0.0.7
 */
public interface GuiHandler {
  /**
   * What to do when an inventory click occurs.
   *
   * @param event inventory click event
   */
  void onClick(InventoryClickEvent event);

  /**
   * What to do when the GUI is opened.
   *
   * @param event inventory open event
   */
  void onOpen(InventoryOpenEvent event);

  /**
   * What to do when the GUI is closed.
   *
   * @param event inventory close event
   */
  void onClose(InventoryCloseEvent event);
}
