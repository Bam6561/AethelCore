package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.guis.GUIHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * {@link me.bam6561.aethelcore.commands.DatabaseCommand} {@link Menu}.
 *
 * @author Danny Nguyen
 * @version 0.0.15
 * @since 0.0.15
 */
public class Database implements GUIHandler, Menu {
  /**
   * No parameter constructor.
   */
  public Database() {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(InventoryClickEvent event) {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(InventoryOpenEvent event) {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory close event
   */
  @Override
  public void onClose(InventoryCloseEvent event) {
  }
}
