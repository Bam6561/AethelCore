package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.commands.DatabaseCommand;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Plugin} permissions.
 *
 * @author Danny Nguyen
 * @version 0.0.21
 * @since 0.0.14
 */
public class Permission {
  /**
   * Enum usage only.
   */
  private Permission() {
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
    DATABASE("aethel.database");

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
