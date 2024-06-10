package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Collection of listeners related to GUIs.
 * <p>
 * GUI behavior is managed through the {@link GuiManager}, which associates
 * inventories with {@link me.bam6561.aethelcore.interfaces.GuiHandler GUI handlers}.
 *
 * @author Danny Nguyen
 * @version 0.0.10
 * @since 0.0.10
 */
public class GuiListener implements Listener {
  /**
   * {@link GuiManager}
   */
  private final GuiManager guiManager;

  /**
   * Associates the listener with its {@link GuiManager}.
   *
   * @param guiManager {@link GuiManager}
   */
  public GuiListener(@NotNull GuiManager guiManager) {
    this.guiManager = Objects.requireNonNull(guiManager, "Null GUI manager");
  }

  /**
   * Routes GUI click interactions.
   *
   * @param event inventory click event
   */
  @EventHandler
  private void onInventoryClick(InventoryClickEvent event) {
    guiManager.handleClick(event);
  }

  /**
   * Routes GUI open interactions.
   *
   * @param event inventory open event
   */
  @EventHandler
  private void onInventoryOpen(InventoryOpenEvent event) {
    guiManager.handleOpen(event);
  }

  /**
   * Routes GUI close interactions.
   *
   * @param event inventory close event
   */
  @EventHandler
  private void onInventoryClose(InventoryCloseEvent event) {
    guiManager.handleClose(event);
  }
}
