package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.markers.ChatInput;
import me.bam6561.aethelcore.references.markers.StringValue;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} managed messages.
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
   * @version 0.1.11
   * @since 0.0.14
   */
  public enum Error implements StringValue {
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
    public String asString() {
      return this.message;
    }
  }

  /**
   * {@link ChatInput} inputs.
   *
   * @author Danny Nguyen
   * @version 0.1.13
   * @since 0.1.10
   */
  public enum Input {
    /**
     * Sets the custom model data.
     */
    CUSTOM_MODEL_DATA,

    /**
     * Sets the display name.
     */
    DISPLAY_NAME,

    /**
     * Adds a line of lore.
     */
    LORE_ADD,

    /**
     * Edit a line of lore.
     */
    LORE_EDIT,

    /**
     * Inserts a line of lore.
     */
    LORE_INSERT,

    /**
     * Removes a line of lore.
     */
    LORE_REMOVE,

    /**
     * Sets the lore.
     */
    LORE_SET,
  }
}
