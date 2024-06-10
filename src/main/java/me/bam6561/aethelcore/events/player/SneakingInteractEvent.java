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
 * Cancellation prevents any {@link me.bam6561.aethelcore.Plugin}
 * features related to the event from happening.
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
  private static final HandlerList handlers = new HandlerList();

  /**
   * Cancellation state.
   */
  private boolean isCancelled = false;

  /**
   * Interaction.
   */
  private final PlayerInteractEvent interaction;

  /**
   * Associates the event with its interaction.
   *
   * @param interaction player interact event
   */
  public SneakingInteractEvent(@NotNull PlayerInteractEvent interaction) {
    this.interaction = Objects.requireNonNull(interaction, "Null interaction");
  }

  /**
   * Gets the player interaction.
   *
   * @return player interact event
   */
  @NotNull
  public PlayerInteractEvent getInteraction() {
    return this.interaction;
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
