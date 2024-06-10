package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.events.SneakingBlockInteractEvent;
import me.bam6561.aethelcore.events.SneakingEntityInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of listeners related to player interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.8
 * @since 0.0.8
 */
public class PlayerListener implements Listener {
  /**
   * No parameter constructor.
   */
  public PlayerListener() {
  }

  /**
   * Routes player interactions with blocks.
   *
   * @param event player interact event
   */
  @EventHandler
  private void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      SneakingBlockInteractEvent sneakingBlockInteract = new SneakingBlockInteractEvent(event);
      Bukkit.getPluginManager().callEvent(sneakingBlockInteract);
    }
  }

  /**
   * Routes player interactions with entities.
   */
  @EventHandler
  private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      SneakingEntityInteractEvent sneakingEntityInteract = new SneakingEntityInteractEvent(event);
      Bukkit.getPluginManager().callEvent(sneakingEntityInteract);
    }
  }
}
