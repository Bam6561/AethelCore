package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.events.player.SneakingInteractEntityEvent;
import me.bam6561.aethelcore.events.player.SneakingInteractEvent;
import me.bam6561.aethelcore.managers.MessageManager;
import me.bam6561.aethelcore.listeners.interactions.SneakingEntityInteraction;
import me.bam6561.aethelcore.listeners.interactions.SneakingInteraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of player interaction listeners.
 *
 * @author Danny Nguyen
 * @version 0.1.13
 * @since 0.0.8
 */
public class PlayerListener implements Listener {
  /**
   * {@link MessageManager}
   */
  private final MessageManager messageManager = Plugin.getMessageManager();

  /**
   * No parameter constructor.
   */
  public PlayerListener() {
  }

  /**
   * Routes messages sent.
   *
   * @param event async player chat event
   */
  @EventHandler
  private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    messageManager.handleMessage(event);
  }

  /**
   * Routes player interactions.
   *
   * @param event player interact event
   */
  @EventHandler
  private void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      SneakingInteractEvent sneakingInteractEvent = new SneakingInteractEvent(event);
      Bukkit.getPluginManager().callEvent(sneakingInteractEvent);
      if (sneakingInteractEvent.isCancelled()) {
        return;
      }

      SneakingInteraction interaction = new SneakingInteraction(event);
      interaction.interpretAction();
    }
  }

  /**
   * Routes player interactions with entities.
   *
   * @param event player interact entity event
   */
  @EventHandler
  private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      SneakingInteractEntityEvent sneakingEntityInteract = new SneakingInteractEntityEvent(event);
      Bukkit.getPluginManager().callEvent(sneakingEntityInteract);
      if (sneakingEntityInteract.isCancelled()) {
        return;
      }

      SneakingEntityInteraction interaction = new SneakingEntityInteraction(event);
      interaction.interpretAction();
    }
  }
}
