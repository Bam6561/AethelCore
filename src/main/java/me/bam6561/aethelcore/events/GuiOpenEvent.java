package me.bam6561.aethelcore.events;

import me.bam6561.aethelcore.interfaces.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a {@link me.bam6561.aethelcore.interfaces.GUI} open event.
 *
 * @author Danny Nguyen
 * @version 0.0.5
 * @since 0.0.4
 */
public class GuiOpenEvent extends Event implements Cancellable {
  /**
   * Event handlers.
   */
  private static final HandlerList handlers = new HandlerList();

  /**
   * Cancellation state.
   */
  private boolean isCancelled = false;

  /**
   * Interacting player.
   */
  private final Player player;

  /**
   * {@link GUI}
   */
  private final GUI gui;

  /**
   * Associates the event with its player.
   *
   * @param player interacting player
   * @param gui    {@link GUI}
   */
  public GuiOpenEvent(@NotNull Player player, @NotNull GUI gui) {
    this.player = Objects.requireNonNull(player, "Null player");
    this.gui = Objects.requireNonNull(gui, "Null gui");
  }

  /**
   * Gets the interacting player.
   *
   * @return interacting player
   */
  @NotNull
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Gets the {@link GUI}.
   *
   * @return {@link GUI}
   */
  @NotNull
  public GUI getGui() {
    return this.gui;
  }

  /**
   * If the event is cancelled.
   *
   * @return if the event is cancelled
   */
  @Override
  public boolean isCancelled() {
    return this.isCancelled;
  }

  /**
   * Sets the event's cancellation state.
   *
   * @param b cancellation state
   */
  @Override
  public void setCancelled(boolean b) {
    this.isCancelled = b;
  }

  /**
   * Gets the event's handlers.
   *
   * @return event handlers
   */
  @NotNull
  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  /**
   * Gets the event's handlers.
   *
   * @return event handlers
   */
  @NotNull
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
