package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.events.GuiOpenEvent;
import me.bam6561.aethelcore.events.SneakingInteractEntityEvent;
import me.bam6561.aethelcore.events.SneakingInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of listeners related to player interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.9
 * @since 0.0.8
 */
public class PlayerListener implements Listener {
  /**
   * No parameter constructor.
   */
  public PlayerListener() {
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

  /**
   * Represents a non-cancelled {@link SneakingInteractEvent}.
   *
   * @param event player interact event
   * @author Danny Nguyen
   * @version 0.0.10
   * @since 0.0.9
   */
  private record SneakingInteraction(PlayerInteractEvent event) {
    /**
     * Opens a workstation when the player's hand is empty.
     */
    private void interpretAction() {
      switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK -> {
          if (event.isBlockInHand()) {
            return;
          }

          openWorkstation(event().getClickedBlock());
        }
      }
    }

    /**
     * Opens the workstation associated with the block type.
     *
     * @param block interacting block
     */
    private void openWorkstation(Block block) {
      switch (block.getType()) {
        case CRAFTING_TABLE -> {
          Player player = event.getPlayer();
          GuiOpenEvent guiOpen = new GuiOpenEvent(player);
          Bukkit.getPluginManager().callEvent(guiOpen);
          if (guiOpen.isCancelled()) {
            return;
          }

          event.setCancelled(true);
          player.openInventory(Bukkit.createInventory(null, 54, "Crafting Table"));
        }
      }
    }
  }

  /**
   * Represents a successful {@link SneakingInteractEntityEvent}.
   *
   * @param event player interact entity event
   * @author Danny Nguyen
   * @version 0.0.9
   * @since 0.0.9
   */
  private record SneakingEntityInteraction(PlayerInteractEntityEvent event) {
    /**
     * Currently doesn't do anything.
     */
    private void interpretAction() {
    }
  }
}
