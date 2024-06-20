package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.markers.MessageInputReceiver;
import me.bam6561.aethelcore.references.markers.StringValue;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} managed messages.
 *
 * @author Danny Nguyen
 * @version 0.1.26
 * @since 0.0.14
 */
public class Message {
  /**
   * Enum usage only.
   */
  private Message() {
  }

  /**
   * ASCII characters.
   *
   * @author Danny Nguyen
   * @version 0.1.26
   * @since 0.1.26
   */
  public enum ASCII implements StringValue {
    /**
     * Checkmark.
     */
    CHECKMARK("✅"),

    /**
     * Cross mark.
     */
    CROSS_MARK("✕"),

    /**
     * Hand holding a pencil.
     */
    WRITING("✍");

    /**
     * ASCII character.
     */
    private final String character;

    /**
     * Associates the ASCII with its character.
     *
     * @param character character
     */
    ASCII(String character) {
      this.character = character;
    }

    /**
     * Gets the ASCII character.
     *
     * @return ASCII character
     */
    @NotNull
    @Override
    public String asString() {
      return this.character;
    }
  }

  /**
   * Error messages.
   *
   * @author Danny Nguyen
   * @version 0.1.26
   * @since 0.0.14
   */
  public enum Error implements StringValue {
    /**
     * Insufficient permission.
     */
    INSUFFICIENT_PERMISSION("Insufficient permission."),

    /**
     * Player-only command.
     */
    PLAYER_ONLY_COMMAND("Player-only command."),

    /**
     * Unrecognized parameters.
     */
    UNRECOGNIZED_PARAMETERS("Unrecognized parameters"),

    /**
     * Non-integer input.
     */
    NON_INTEGER_INPUT("Non-integer input."),

    /**
     * Invalid line number.
     */
    INVALID_LINE("Invalid line number.");

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
      this.message = ChatColor.RED + ASCII.CROSS_MARK.asString() + " " + ChatColor.WHITE + message;
    }

    /**
     * Gets the error's message.
     *
     * @return error message
     */
    @NotNull
    @Override
    public String asString() {
      return this.message;
    }
  }

  /**
   * {@link MessageInputReceiver} inputs.
   *
   * @author Danny Nguyen
   * @version 0.1.26
   * @since 0.1.10
   */
  public enum Input {
    /**
     * Sets the custom model data.
     */
    CUSTOM_MODEL_DATA("Custom Model Data " + ChatColor.WHITE + "#"),

    /**
     * Sets the display name.
     */
    DISPLAY_NAME("Display Name " + ChatColor.WHITE + "Text"),

    /**
     * Adds a line of lore.
     */
    LORE_ADD("Add Lore " + ChatColor.WHITE + "Text"),

    /**
     * Edit a line of lore.
     */
    LORE_EDIT("Edit Lore " + ChatColor.WHITE + "#, Text"),

    /**
     * Inserts a line of lore.
     */
    LORE_INSERT("Insert Lore " + ChatColor.WHITE + "#, Text"),

    /**
     * Removes a line of lore.
     */
    LORE_REMOVE("Remove Lore " + ChatColor.WHITE + "#"),

    /**
     * Sets the lore.
     */
    LORE_SET("Set Lore " + ChatColor.WHITE + "Text (Use \" ; \" for new lines.)");

    /**
     * Input message.
     * <p>
     * Used to tell the player what to type as a valid input.
     */
    private final String notificationMessage;

    /**
     * Associates the input with its notification message.
     *
     * @param notificationMessage notification message
     */
    Input(String notificationMessage) {
      this.notificationMessage = ChatColor.GOLD + ASCII.WRITING.asString() + " " + ChatColor.AQUA + notificationMessage;
    }

    /**
     * Gets the notification message.
     *
     * @return notification message
     */
    public String getNotificationMessage() {
      return this.notificationMessage;
    }
  }
}
