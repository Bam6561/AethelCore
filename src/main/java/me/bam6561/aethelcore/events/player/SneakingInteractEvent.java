package me.bam6561.aethelcore.events.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called when a player interacts while sneaking.
 * <p>
 * May be cancelled without cancelling its source PlayerInteractEvent.
 *
 * @author Danny Nguyen
 * @version 0.0.7
 * @since 0.0.2
 */
public class SneakingInteractEvent extends Event implements Cancellable {
  /**
   * Event handlers.
   */
  private static final HandlerList HANDLERS = new HandlerList();

  /**
   * Cancellation state.
   */
  private boolean isCancelled = false;

  /**
   * Event source.
   */
  private final PlayerInteractEvent source;

  /**
   * Associates the event with its source.
   *
   * @param source player interact event
   */
  public SneakingInteractEvent(@NotNull PlayerInteractEvent source) {
    this.source = Objects.requireNonNull(source, "Null source");
  }

  /**
   * Gets the source event.
   *
   * @return player interact event
   */
  @NotNull
  public PlayerInteractEvent getSource() {
    return this.source;
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
    return HANDLERS;
  }

  /**
   * Gets the event's handlers.
   *
   * @return event handlers
   */
  @NotNull
  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
}
