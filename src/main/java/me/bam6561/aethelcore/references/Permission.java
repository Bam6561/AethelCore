package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.commands.DatabaseCommand;
import me.bam6561.aethelcore.commands.ItemEditorCommand;
import me.bam6561.aethelcore.references.markers.StringValue;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} permissions.
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
   * Commands.
   *
   * @author Danny Nguyen
   * @version 0.1.15
   * @since 0.0.21
   */
  public enum Command implements StringValue {
    /**
     * {@link DatabaseCommand}
     */
    DATABASE("database"),

    /**
     * {@link ItemEditorCommand}
     */
    ITEMEDITOR("itemeditor");

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
      this.value = Header.COMMAND.getValue() + value;
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
   * Message flags.
   *
   * @author Danny Nguyen
   * @version 0.1.15
   * @since 0.0.22
   */
  public enum Message implements StringValue {
    /**
     * Colored chat messages.
     */
    COLOR("color");

    /**
     * Permission value.
     */
    private final String value;

    /**
     * Associates a permission with its value.
     *
     * @param value value
     */
    Message(String value) {
      this.value = Header.MESSAGE.getValue() + value;
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
   * @version 0.1.15
   * @since 0.0.27
   */
  private enum Header {
    /**
     * {@link Command}
     */
    COMMAND("command."),

    /**
     * {@link Message}
     */
    MESSAGE("message.");

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
      this.value = "aethel." + value;
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
