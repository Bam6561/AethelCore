package me.bam6561.aethelcore.references.markers;

import org.jetbrains.annotations.NotNull;

/**
 * References used primarily as Strings.
 *
 * @author Danny Nguyen
 * @version 0.1.11
 * @since 0.1.11
 */
public interface StringValue {
  /**
   * Gets the enum's String value.
   *
   * @return enum's String value
   */
  @NotNull
  String asString();
}
