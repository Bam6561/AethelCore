package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.GUIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Collection of {@link GUI} listeners.
 * <p>
 * {@link GUI} behavior is managed through the {@link GUIManager}.
 *
 * @author Danny Nguyen
 * @version 0.0.10
 * @since 0.0.10
 */
public class GUIListener implements Listener {
  /**
   * {@link GUIManager}
   */
  private final GUIManager guiManager;

  /**
   * Associates the listener with its {@link GUIManager}.
   *
   * @param guiManager {@link GUIManager}
   */
  public GUIListener(@NotNull GUIManager guiManager) {
    this.guiManager = Objects.requireNonNull(guiManager, "Null GUIManager");
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