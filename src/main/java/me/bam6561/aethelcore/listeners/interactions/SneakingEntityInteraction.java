package me.bam6561.aethelcore.listeners.interactions;

import me.bam6561.aethelcore.events.player.SneakingInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * {@link SneakingInteractEntityEvent} interaction.
 *
 * @author Danny Nguyen
 * @version 0.0.24
 * @since 0.0.9
 */
public class SneakingEntityInteraction {
  /**
   * Player interact entity event.
   */
  private final PlayerInteractEntityEvent event;

  /**
   * Associates the interaction with its source.
   *
   * @param event player interact entity event
   */
  public SneakingEntityInteraction(PlayerInteractEntityEvent event) {
    this.event = event;
  }

  /**
   * Currently does nothing.
   */
  public void interpretAction() {
  }
}
