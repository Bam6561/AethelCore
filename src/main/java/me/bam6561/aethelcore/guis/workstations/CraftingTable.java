package me.bam6561.aethelcore.guis.workstations;

import me.bam6561.aethelcore.interfaces.GUIHandler;
import me.bam6561.aethelcore.interfaces.Workstation;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Represents a crafting table {@link Workstation}.
 *
 * @author Danny Nguyen
 * @version 0.0.11
 * @since 0.0.11
 */
public class CraftingTable implements GUIHandler, Workstation {
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
