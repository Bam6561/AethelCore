package me.bam6561.aethelcore.events.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called when a player interacts with an entity while sneaking.
 * <p>
 * Cancellation prevents any {@link me.bam6561.aethelcore.Plugin}
 * features related to the event from happening.
 * <p>
 * May be cancelled without cancelling its source PlayerInteractEntityEvent.
 *
 * @author Danny Nguyen
 * @version 0.0.8
 * @since 0.0.8
 */
public class SneakingInteractEntityEvent extends Event implements Cancellable {
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
  private final PlayerInteractEntityEvent interaction;

  /**
   * Associates the event with its interaction.
   *
   * @param interaction player interact event
   */
  public SneakingInteractEntityEvent(@NotNull PlayerInteractEntityEvent interaction) {
    this.interaction = Objects.requireNonNull(interaction, "Null PlayerInteractEntityEvent");
  }

  /**
   * Gets the player entity interaction.
   *
   * @return player interact entity event
   */
  @NotNull
  public PlayerInteractEntityEvent getInteraction() {
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
