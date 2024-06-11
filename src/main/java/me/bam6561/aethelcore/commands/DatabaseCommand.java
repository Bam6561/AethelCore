package me.bam6561.aethelcore.commands;

import me.bam6561.aethelcore.Message;
import me.bam6561.aethelcore.events.gui.GUIOpenEvent;
import me.bam6561.aethelcore.guis.GUIManager;
import me.bam6561.aethelcore.guis.commands.DatabaseMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Command invocation that provides information on key terms and items.
 *
 * @author Danny Nguyen
 * @version 0.0.17
 * @since 0.0.13
 */
public class DatabaseCommand implements CommandExecutor {
  /**
   * {@link GUIManager}
   */
  private final GUIManager guiManager;

  /**
   * Associates the command with its {@link GUIManager}.
   *
   * @param guiManager {@link GUIManager}
   */
  public DatabaseCommand(@NotNull GUIManager guiManager) {
    this.guiManager = Objects.requireNonNull(guiManager, "Null GUIManager");
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
    if (!(sender instanceof Player player)) {
      sender.sendMessage(Message.Error.PLAYER_ONLY_COMMAND.getMessage());
      return true;
    }
    if (!player.hasPermission(Permission.DATABASE.getValue())) {
      player.sendMessage(Message.Error.INSUFFICIENT_PERMISSION.getMessage());
      return true;
    }

    GUIOpenEvent guiOpen = new GUIOpenEvent(player);
    Bukkit.getPluginManager().callEvent(guiOpen);
    if (guiOpen.isCancelled()) {
      return true;
    }
    guiManager.openGUI(player, new DatabaseMenu());
    return true;
  }
}
