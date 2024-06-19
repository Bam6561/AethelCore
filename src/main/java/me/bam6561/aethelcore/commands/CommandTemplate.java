package me.bam6561.aethelcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Command invocation.
 *
 * @author Danny Nguyen
 * @version 0.0.26
 * @since 0.0.26
 */
public class CommandTemplate implements CommandExecutor {
  /**
   * No parameter constructor.
   */
  public CommandTemplate() {
  }

  /**
   * Executes the command.
   *
   * @param sender  command source
   * @param command executed command
   * @param label   command alias used
   * @param args    command parameters
   * @return true if valid execution
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    /*
    if (!CommandUtils.isPlayerWithPermission(sender, Permission.Command.PERMISSION)) {
      return true;
    }
    */
    return true;
  }
}
