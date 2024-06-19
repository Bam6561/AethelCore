package me.bam6561.aethelcore.references;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin messages.
 *
 * @author Danny Nguyen
 * @version 0.1.10
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

  /**
   * Message inputs.
   *
   * @author Danny Nguyen
   * @version 0.1.10
   * @since 0.1.10
   */
  public class Input {
    /**
     * {@link me.bam6561.aethelcore.guis.commands.ItemAppearanceGUI}
     *
     * @author Danny Nguyen
     * @version 0.1.8
     * @since 0.1.8
     */
    public enum ItemAppearance {
      /**
       * Sets the display name.
       */
      DISPLAY_NAME,

      /**
       * Sets the custom model data.
       */
      CUSTOM_MODEL_DATA,

      /**
       * Adds a line of lore.
       */
      LORE_ADD,

      /**
       * Inserts a line of lore.
       */
      LORE_INSERT,

      /**
       * Sets the lore.
       */
      LORE_SET,

      /**
       * Edit a line of lore.
       */
      LORE_EDIT,

      /**
       * Removes a line of lore.
       */
      LORE_REMOVE,
    }
  }
}
