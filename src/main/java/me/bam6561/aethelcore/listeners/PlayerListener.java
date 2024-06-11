package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.events.gui.GUIOpenEvent;
import me.bam6561.aethelcore.events.player.SneakingInteractEntityEvent;
import me.bam6561.aethelcore.events.player.SneakingInteractEvent;
import me.bam6561.aethelcore.guis.GUIManager;
import me.bam6561.aethelcore.guis.workstations.CraftingTableWorkstation;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Collection of listeners related to player interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.15
 * @since 0.0.8
 */
public class PlayerListener implements Listener {
  /**
   * {@link GUIManager}
   */
  private final GUIManager guiManager;

  /**
   * Associates the listener with a {@link GUIManager}.
   *
   * @param guiManager {@link GUIManager}
   */
  public PlayerListener(@NotNull GUIManager guiManager) {
    this.guiManager = Objects.requireNonNull(guiManager, "Null GUI manager.");
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
   * {@link me.bam6561.aethelcore.Plugin} {@link SneakingInteractEvent} interaction.
   *
   * @author Danny Nguyen
   * @version 0.0.15
   * @since 0.0.9
   */
  private class SneakingInteraction {
    /**
     * Player interact event.
     */
    private final PlayerInteractEvent event;

    /**
     * Associates the interaction with its player interact event.
     *
     * @param event player interact event
     */
    SneakingInteraction(PlayerInteractEvent event) {
      this.event = event;
    }

    /**
     * Opens a workstation when the player's hand is empty.
     */
    private void interpretAction() {
      switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK -> {
          if (event.isBlockInHand()) {
            return;
          }

          openWorkstation(event.getClickedBlock());
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
          GUIOpenEvent guiOpen = new GUIOpenEvent(player);
          Bukkit.getPluginManager().callEvent(guiOpen);
          if (guiOpen.isCancelled()) {
            return;
          }

          event.setCancelled(true);
          guiManager.registerGUI(Bukkit.createInventory(null, 54, "Crafting Table"), new CraftingTableWorkstation());
        }
      }
    }
  }

  /**
   * {@link me.bam6561.aethelcore.Plugin} {@link SneakingInteractEntityEvent} interaction.
   *
   * @author Danny Nguyen
   * @version 0.0.15
   * @since 0.0.9
   */
  private class SneakingEntityInteraction {
    /**
     * Player interact entity event.
     */
    private final PlayerInteractEntityEvent event;

    /**
     * Associates the interaction with its player interact entity event.
     *
     * @param event player interact entity event
     */
    SneakingEntityInteraction(PlayerInteractEntityEvent event) {
      this.event = event;
    }

    /**
     * Currently does nothing.
     */
    private void interpretAction() {
    }
  }
}
