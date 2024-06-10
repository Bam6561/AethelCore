package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.events.GuiOpenEvent;
import me.bam6561.aethelcore.events.SneakingBlockInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of listeners related to block interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.8
 * @since 0.0.3
 */
public class BlockListener implements Listener {
  /**
   * No parameter constructor.
   */
  public BlockListener() {
  }

  /**
   * Routes {@link SneakingBlockInteractEvent} interactions.
   *
   * @param event {@link SneakingBlockInteractEvent}
   */
  @EventHandler
  private void onSneakingBlockInteract(SneakingBlockInteractEvent event) {
    PlayerInteractEvent interaction = event.getInteraction();
    if (interaction.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block block = interaction.getClickedBlock();
      if (block != null) {
        openWorkstation(interaction);
      }
    }
  }

  /**
   * Opens the block's {@link me.bam6561.aethelcore.interfaces.Workstation}.
   *
   * @param event player interact event
   */
  private void openWorkstation(PlayerInteractEvent event) {
    if (event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
      GuiOpenEvent guiOpen = new GuiOpenEvent(event.getPlayer());
      Bukkit.getPluginManager().callEvent(guiOpen);
      if (guiOpen.isCancelled()) {
        return;
      }
      event.setCancelled(true);
      event.getPlayer().openInventory(Bukkit.createInventory(null, 54));
    }
  }
}
