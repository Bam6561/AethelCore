package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.GUIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Collection of {@link GUI} listeners.
 * <p>
 * {@link GUI} behavior is managed through the {@link GUIManager}.
 *
 * @author Danny Nguyen
 * @version 0.1.3
 * @since 0.0.10
 */
public class GUIListener implements Listener {
  /**
   * {@link GUIManager}
   */
  private final GUIManager guiManager = Plugin.getGUIManager();

  /**
   * No parameter constructor.
   */
  public GUIListener() {
  }

  /**
   * Routes {@link GUI} click interactions.
   *
   * @param event inventory click event
   */
  @EventHandler
  private void onInventoryClick(InventoryClickEvent event) {
    guiManager.handleClick(event);
  }

  /**
   * Routes {@link GUI} drag interactions.
   *
   * @param event inventory drag event
   */
  @EventHandler
  private void onInventoryDrag(InventoryDragEvent event) {
    guiManager.handleDrag(event);
  }

  /**
   * Routes {@link GUI} open interactions.
   *
   * @param event inventory open event
   */
  @EventHandler
  private void onInventoryOpen(InventoryOpenEvent event) {
    guiManager.handleOpen(event);
  }

  /**
   * Routes {@link GUI} close interactions.
   *
   * @param event inventory close event
   */
  @EventHandler
  private void onInventoryClose(InventoryCloseEvent event) {
    guiManager.handleClose(event);
  }
}
