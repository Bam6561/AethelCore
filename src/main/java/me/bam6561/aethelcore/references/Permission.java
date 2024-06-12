package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.commands.DatabaseCommand;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} permissions.
 *
 * @author Danny Nguyen
 * @version 0.0.22
 * @since 0.0.14
 */
public class Permission {
  /**
   * Enum usage only.
   */
  private Permission() {
  }

  /**
   * Chat permissions.
   *
   * @author Danny Nguyen
   * @version 0.0.22
   * @since 0.0.22
   */
  public enum Chat {
    /**
     * Ability to color chat messages.
     */
    COLOR("aethel.chat.color");

    /**
     * Permission value.
     */
    private final String value;

    /**
     * Associates a permission with its value.
     *
     * @param value value
     */
    Chat(String value) {
      this.value = value;
    }

    /**
     * Gets the permission value.
     *
     * @return permission value
     */
    @NotNull
    public String getValue() {
      return this.value;
    }
  }

  /**
   * Command permissions.
   *
   * @author Danny Nguyen
   * @version 0.0.21
   * @since 0.0.21
   */
  public enum Command {
    /**
     * {@link DatabaseCommand}
     */
    DATABASE("aethel.command.database");

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
    public String getValue() {
      return this.value;
    }
  }
}
