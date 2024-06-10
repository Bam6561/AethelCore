package me.bam6561.aethelcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command invocation that references all items in memory.
 *
 * @author Danny Nguyen
 * @version 0.0.13
 * @since 0.0.13
 */
public class ItemDatabaseCommand implements CommandExecutor {
  /**
   * No parameter constructor.
   */
  public ItemDatabaseCommand() {
  }

  /**
   * Executes the ItemDatabase command.
   *
   * @param sender  command source
   * @param command executed command
   * @param label   command alias used
   * @param args    command parameters
   * @return true if valid execution
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player user) {
      if (user.hasPermission("aethel.itemdatabase")) {
        // TODO
      } else {
        user.sendMessage("Insufficient permission.");
      }
    } else {
      sender.sendMessage("Player-only command.");
    }
    return false;
  }
}
