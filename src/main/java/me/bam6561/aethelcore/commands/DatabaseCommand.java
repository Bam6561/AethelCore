package me.bam6561.aethelcore.commands;

import me.bam6561.aethelcore.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command invocation that provides information on key terms and items.
 *
 * @author Danny Nguyen
 * @version 0.0.14
 * @since 0.0.13
 */
public class DatabaseCommand implements CommandExecutor {
  /**
   * No parameter constructor.
   */
  public DatabaseCommand() {
  }

  /**
   * Executes the Database command.
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
      if (user.hasPermission(Permission.DATABASE.getValue())) {
        // TODO
      } else {
        user.sendMessage(Message.Error.INSUFFICIENT_PERMISSION.getMessage());
      }
    } else {
      sender.sendMessage(Message.Error.PLAYER_ONLY_COMMAND.getMessage());
    }
    return false;
  }
}
