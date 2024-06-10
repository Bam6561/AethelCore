package me.bam6561.aethelcore.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a block action done while a player is sneaking.
 *
 * @author Danny Nguyen
 * @version 0.0.6
 * @since 0.0.2
 */
public class SneakingBlockActionEvent extends Event implements Cancellable {
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
   * Block action.
   */
  private final Action action;

  /**
   * Interacting block.
   */
  private final Block block;

  /**
   * Associates the event with its player and action.
   *
   * @param player interacting player
   * @param action block action
   * @param block  interacting block
   */
  public SneakingBlockActionEvent(@NotNull Player player, @NotNull Action action, @Nullable Block block) {
    this.player = Objects.requireNonNull(player, "Null player");
    this.action = Objects.requireNonNull(action, "Null action");
    this.block = block;
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
   * Gets the block action.
   *
   * @return block action
   */
  @NotNull
  public Action getAction() {
    return this.action;
  }

  /**
   * Gets the interacting block.
   *
   * @return interacting block
   */
  @Nullable
  public Block getBlock() {
    return this.block;
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
