package me.bam6561.aethelcore.utils;

import me.bam6561.aethelcore.references.Message;
import me.bam6561.aethelcore.references.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities for commands.
 *
 * @author Danny Nguyen
 * @version 0.0.26
 * @since 0.0.26
 */
public class CommandUtils {
  /**
   * Utility methods only.
   */
  private CommandUtils() {
  }

  /**
   * If the command sender is a player and has a provided permission.
   *
   * @param sender     command source
   * @param permission permission
   * @return if the command sender is a player and has a provided permission
   */
  public static boolean isPlayerWithPermission(@NotNull CommandSender sender, @NotNull Permission.Command permission) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage(Message.Error.PLAYER_ONLY_COMMAND.getMessage());
      return false;
    }
    if (!player.hasPermission(Permission.Command.DATABASE.getValue())) {
      player.sendMessage(Message.Error.INSUFFICIENT_PERMISSION.getMessage());
      return false;
    }
    return true;
  }
}
