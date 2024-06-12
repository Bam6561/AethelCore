package me.bam6561.aethelcore.listeners.interactions;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.events.gui.GUIOpenEvent;
import me.bam6561.aethelcore.events.player.SneakingInteractEvent;
import me.bam6561.aethelcore.guis.blocks.CraftingTableGUI;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * {@link SneakingInteractEvent} interaction.
 *
 * @author Danny Nguyen
 * @version 0.0.24
 * @since 0.0.9
 */
public class SneakingInteraction {
  /**
   * Player interact event.
   */
  private final PlayerInteractEvent event;

  /**
   * Associates the interaction with its source.
   *
   * @param event player interact event
   */
  public SneakingInteraction(PlayerInteractEvent event) {
    this.event = event;
  }

  /**
   * Opens a {@link me.bam6561.aethelcore.guis.blocks.Workstation} when the player's hand is empty.
   */
  public void interpretAction() {
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
   * Opens the {@link me.bam6561.aethelcore.guis.blocks.Workstation} associated with the block type.
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
        Plugin.getGUIManager().openGUI(player, new CraftingTableGUI());
      }
    }
  }
}
