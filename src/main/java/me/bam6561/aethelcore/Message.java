package me.bam6561.aethelcore;

import net.md_5.bungee.api.ChatColor;

/**
 * Commonly used {@link Plugin} messages.
 *
 * @author Danny Nguyen
 * @version 0.0.14
 * @since 0.0.14
 */
public enum Message {
  ;

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
    public String getMessage() {
      return this.message;
    }
  }
}
