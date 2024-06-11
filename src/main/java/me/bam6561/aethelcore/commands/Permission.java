package me.bam6561.aethelcore.commands;

import org.jetbrains.annotations.NotNull;

/**
 * Command permissions.
 *
 * @author Danny Nguyen
 * @version 0.0.14
 * @since 0.0.14
 */
public enum Permission {
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
  Permission(String value) {
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
