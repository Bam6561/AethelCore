package me.bam6561.aethelcore.guis;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Outlines base interactions for a plugin managed inventory.
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
  void onClick(@NotNull InventoryClickEvent event);

  /**
   * What to do when the inventory is opened.
   *
   * @param event inventory open event
   */
  void onOpen(@NotNull InventoryOpenEvent event);

  /**
   * What to do when the inventory is closed.
   *
   * @param event inventory close event
   */
  void onClose(@NotNull InventoryCloseEvent event);
}
