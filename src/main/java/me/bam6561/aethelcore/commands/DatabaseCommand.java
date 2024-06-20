package me.bam6561.aethelcore.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.events.gui.GUIOpenEvent;
import me.bam6561.aethelcore.guis.commands.DatabaseGUI;
import me.bam6561.aethelcore.references.Permission;
import me.bam6561.aethelcore.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command invocation that provides information on key terms and items.
 *
 * @author Danny Nguyen
 * @version 0.2.1
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
    if (!CommandUtils.isPlayerWithPermission(sender, Permission.Command.DATABASE)) {
      return true;
    }

    Player player = (Player) sender;
    GUIOpenEvent guiOpen = new GUIOpenEvent(player, GUIOpenEvent.Cause.COMMAND);
    Bukkit.getPluginManager().callEvent(guiOpen);
    if (guiOpen.isCancelled()) {
      return true;
    }

    Plugin.getGUIManager().openGUI(player, new DatabaseGUI());
    return true;
  }
}
