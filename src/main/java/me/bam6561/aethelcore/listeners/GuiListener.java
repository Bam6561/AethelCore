package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.guis.GUIManager;
import me.bam6561.aethelcore.interfaces.GUIHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Collection of listeners related to {@link me.bam6561.aethelcore.interfaces.GUI GUIs}.
 * <p>
 * {@link me.bam6561.aethelcore.interfaces.GUI} behavior is managed through the
 * {@link GUIManager}, which associates inventories with {@link GUIHandler GUI handlers}.
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
    this.guiManager = Objects.requireNonNull(guiManager, "Null GUI manager");
  }

  /**
   * Routes {@link me.bam6561.aethelcore.interfaces.GUI} click interactions.
   *
   * @param event inventory click event
   */
  @EventHandler
  private void onInventoryClick(InventoryClickEvent event) {
    guiManager.handleClick(event);
  }

  /**
   * Routes {@link me.bam6561.aethelcore.interfaces.GUI} open interactions.
   *
   * @param event inventory open event
   */
  @EventHandler
  private void onInventoryOpen(InventoryOpenEvent event) {
    guiManager.handleOpen(event);
  }

  /**
   * Routes {@link me.bam6561.aethelcore.interfaces.GUI} close interactions.
   *
   * @param event inventory close event
   */
  @EventHandler
  private void onInventoryClose(InventoryCloseEvent event) {
    guiManager.handleClose(event);
  }
}
