package me.bam6561.aethelcore.listeners;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.events.GuiOpenEvent;
import me.bam6561.aethelcore.events.SneakingBlockActionEvent;
import me.bam6561.aethelcore.gui.CraftingTable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Collection of listeners related to block interactions.
 *
 * @author Danny Nguyen
 * @version 0.0.6
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
      SneakingBlockActionEvent sneakingBlockAction = new SneakingBlockActionEvent(player, event.getAction(), event.getClickedBlock());
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
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block block = event.getBlock();
      if (block != null) {
        openWorkstation(event.getPlayer(), block);
      }
    }
  }

  /**
   * Opens the block's {@link me.bam6561.aethelcore.interfaces.Workstation}.
   *
   * @param player interacting player
   * @param block  interacting block
   */
  private void openWorkstation(Player player, Block block) {
    if (block.getType() == Material.CRAFTING_TABLE) {
      CraftingTable craftingTable = new CraftingTable();
      GuiOpenEvent guiOpen = new GuiOpenEvent(player, craftingTable);
      Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> {
        player.openInventory(new CraftingTable().getInventory());
        Bukkit.getPluginManager().callEvent(guiOpen);
      }, 1);
    }
  }
}
