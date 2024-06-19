package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.commands.DatabaseCommand;
import me.bam6561.aethelcore.commands.ItemEditorCommand;
import me.bam6561.aethelcore.references.markers.StringValue;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin permissions.
 *
 * @author Danny Nguyen
 * @version 0.0.27
 * @since 0.0.14
 */
public class Permission {
  /**
   * Enum usage only.
   */
  private Permission() {
  }

  /**
   * Chat flag permissions.
   *
   * @author Danny Nguyen
   * @version 0.1.11
   * @since 0.0.22
   */
  public enum ChatFlag implements StringValue {
    /**
     * Ability to color chat messages.
     */
    COLOR(Header.CHATFLAG.getValue() + "color");

    /**
     * Permission value.
     */
    private final String value;

    /**
     * Associates a permission with its value.
     *
     * @param value value
     */
    ChatFlag(String value) {
      this.value = value;
    }

    /**
     * Gets the permission value.
     *
     * @return permission value
     */
    @NotNull
    public String asString() {
      return this.value;
    }
  }

  /**
   * Command permissions.
   *
   * @author Danny Nguyen
   * @version 0.1.11
   * @since 0.0.21
   */
  public enum Command implements StringValue {
    /**
     * {@link DatabaseCommand}
     */
    DATABASE(Header.COMMAND.getValue() + "database"),

    /**
     * {@link ItemEditorCommand}
     */
    ITEMEDITOR(Header.COMMAND.getValue() + "itemeditor");

    /**
     * Permission value.
     */
    private final String value;

    /**
     * Associates a permission with its value.
     *
     * @param value value
     */
    Command(String value) {
      this.value = value;
    }

    /**
     * Gets the permission value.
     *
     * @return permission value
     */
    @NotNull
    public String asString() {
      return this.value;
    }
  }

  /**
   * Permission headers.
   *
   * @author Danny Nguyen
   * @version 0.0.27
   * @since 0.0.27
   */
  private enum Header {
    /**
     * {@link ChatFlag}
     */
    CHATFLAG("aethel.chatflag."),

    /**
     * {@link Command}
     */
    COMMAND("aethel.command.");

    /**
     * Permission header value.
     */
    private final String value;

    /**
     * Associates a permission header with its value.
     *
     * @param value value
     */
    Header(String value) {
      this.value = value;
    }

    /**
     * Gets the permission header's value.
     *
     * @return permission header's value
     */
    private String getValue() {
      return this.value;
    }
  }
}
