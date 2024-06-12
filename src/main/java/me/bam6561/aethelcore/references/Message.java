package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} messages.
 *
 * @author Danny Nguyen
 * @version 0.0.21
 * @since 0.0.14
 */
public class Message {
  /**
   * Enum usage only.
   */
  private Message() {
  }

  /**
   * Error messages.
   *
   * @author Danny Nguyen
   * @version 0.0.14
   * @since 0.0.14
   */
  public enum Error {
    /**
     * Insufficient permission.
     */
    INSUFFICIENT_PERMISSION(ChatColor.RED + "Insufficient permission."),

    /**
     * Player-only command.
     */
    PLAYER_ONLY_COMMAND(ChatColor.RED + "Player-only command.");

    /**
     * Error message.
     */
    private final String message;

    /**
     * Associates an error with its message.
     *
     * @param message message
     */
    Error(String message) {
      this.message = message;
    }

    /**
     * Gets the error's message.
     *
     * @return error message
     */
    @NotNull
    public String getMessage() {
      return this.message;
    }
  }
}
