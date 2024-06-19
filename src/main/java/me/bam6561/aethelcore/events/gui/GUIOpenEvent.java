package me.bam6561.aethelcore.events.gui;

import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.managers.MessageManager.MessageInputRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called before opening a {@link GUI} from a neutral state.
 * <p>
 * Will not be called when a player:
 * <ul>
 *   <li>navigates between {@link GUI GUIs} through buttons
 *   <li>reopens a {@link GUI} by responding to a {@link MessageInputRequest}
 * </ul>
 * <p>
 * Cancellation prevents the {@link GUI} from opening.
 *
 * @author Danny Nguyen
 * @version 0.0.26
 * @since 0.0.4
 */
public class GUIOpenEvent extends Event implements Cancellable {
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
   * {@link Cause}
   */
  private final Cause cause;

  /**
   * Associates the event with its player and {@link Cause}.
   *
   * @param player interacting player
   * @param cause  {@link Cause}
   */
  public GUIOpenEvent(@NotNull Player player, @NotNull Cause cause) {
    this.player = Objects.requireNonNull(player, "Null player");
    this.cause = Objects.requireNonNull(cause, "Null cause");
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
   * Gets the {@link Cause}.
   *
   * @return {@link Cause}
   */
  @NotNull
  public Cause getCause() {
    return this.cause;
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

  /**
   * Cause of the {@link GUI} open event.
   *
   * @author Danny Nguyen
   * @version 0.0.26
   * @since 0.0.26
   */
  public enum Cause {
    /**
     * By command.
     */
    COMMAND,

    /**
     * By world interaction.
     */
    INTERACTION
  }
}
