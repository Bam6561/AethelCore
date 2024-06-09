package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.events.SneakingBlockActionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of listeners related to block interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.3
 * @since 0.0.3
 */
public class BlockListener implements Listener {
  /**
   * No parameter constructor.
   */
  public BlockListener() {
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
      SneakingBlockActionEvent sneakingBlockAction = new SneakingBlockActionEvent(player, event.getAction());
      Bukkit.getPluginManager().callEvent(sneakingBlockAction);
    }
  }

  /**
   * Routes sneaking block actions.
   *
   * @param event sneaking block action event
   */
  @EventHandler
  private void onSneakingBlockAction(SneakingBlockActionEvent event) {
  }
}
