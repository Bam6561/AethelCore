package me.bam6561.aethelcore.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Formats text.
 *
 * @author Danny Nguyen
 * @version 0.0.20
 * @since 0.0.20
 */
public class TextFormatter {
  /**
   * Utility methods only.
   */
  private TextFormatter() {
  }

  /**
   * Capitalizes every letter and replaces all spaces with underscores.
   *
   * @param text text
   * @return enum formatted text
   */
  @NotNull
  public static String formatEnum(@NotNull String text) {
    Objects.requireNonNull(text, "Null text");
    if (text.isEmpty() || text.isBlank()) {
      return "";
    }
    return text.replace(" ", "_").toUpperCase();
  }

  /**
   * Lowercases every letter and replaces all spaces with underscores.
   *
   * @param text text
   * @return identifier formatted text
   */
  @NotNull
  public static String formatIdentifier(@NotNull String text) {
    Objects.requireNonNull(text, "Null text");
    if (text.isEmpty() || text.isBlank()) {
      return "";
    }
    return text.replace(" ", "_").toLowerCase();
  }

  /**
   * Properly capitalizes every word and replaces any provided characters with spaces.
   *
   * @param text text
   * @return title formatted text
   */
  @NotNull
  public static String formatTitle(@NotNull String text, char[] characters) {
    Objects.requireNonNull(text, "Null text");
    if (text.isEmpty() || text.isBlank()) {
      return "";
    }

    for (char character : characters) {
      text = text.replace(character, ' ');
    }

    String[] words = text.split(" ");
    if (text.length() == 1) {
      return capitalizeWord(text);
    }

    StringBuilder newText = new StringBuilder();
    for (String word : words) {
      newText.append(capitalizeWord(word)).append(" ");
    }
    return newText.toString().trim();
  }

  /**
   * Properly capitalizes the word.
   *
   * @param word properly capitalized word
   */
  private static String capitalizeWord(String word) {
    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
  }
}
