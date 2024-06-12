package me.bam6561.aethelcore.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * Utilities for text.
 *
 * @author Danny Nguyen
 * @version 0.0.22
 * @since 0.0.20
 */
public class TextTool {
  /**
   * Utility methods only.
   */
  private TextTool() {
  }

  /**
   * Colors text.
   *
   * @author Danny Nguyen
   * @version 0.0.22
   * @since 0.0.22
   */
  public static class Color {
    /**
     * Spigot chat color characters.
     */
    private static final Set<Character> CHAT_COLOR_CHARS = Set.of(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r',
        'A', 'B', 'C', 'D', 'E', 'F', 'K', 'L', 'M', 'N', 'O', 'R');

    /**
     * Hexadecimal characters.
     */
    private static final Set<Character> HEX_CHARS = Set.of(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F');

    /**
     * Utility methods only.
     */
    private Color() {
    }

    /**
     * If the text is a hex color code (#ffffff).
     *
     * @param text text
     * @return if the text is a hex color code
     */
    public static boolean isHexColorCode(@NotNull String text) {
      Objects.requireNonNull(text, "Null text");
      if (text.length() != 7) {
        return false;
      }
      if (text.charAt(0) != '#') {
        return false;
      }
      for (int i = 1; i < text.length(); i++) {
        if (!HEX_CHARS.contains(text.charAt(i))) {
          return false;
        }
      }
      return true;
    }

    /**
     * Translates color codes in text with a provided color code symbol.
     * <p>
     * Supports Spigot chat color and hex color codes.
     *
     * @param text   text
     * @param symbol color code symbol
     * @return colored text
     */
    @NotNull
    public static String translate(@NotNull String text, char symbol) {
      Objects.requireNonNull(text, "Null text");
      StringBuilder newText = new StringBuilder();
      char[] characters = text.toCharArray();
      int start = 0;
      int current = 0;

      while (current < characters.length) {
        char character = characters[current];
        if (character != symbol) {
          current++;
          continue;
        }

        newText.append(text, start, current);

        char nextCharacter = characters[current + 1];
        if (CHAT_COLOR_CHARS.contains(nextCharacter)) {
          newText.append(ChatColor.getByChar(nextCharacter));
          current += 2;
        } else if (isHexColorCode(nextCharacter, current, characters)) {
          newText.append(ChatColor.valueOf(new String(Arrays.copyOfRange(characters, current + 2, current + 8))));
          current = current + 8;
        } else {
          newText.append(characters[current]);
          current++;
        }
        start = current;
      }

      return newText.toString();
    }

    /**
     * If the next characters in the character array is a hex color code (#abcdef).
     * <p>
     * Internal usage only.
     *
     * @param nextCharacter next character
     * @param current       current pointer
     * @param characters    character array
     * @return if the next characters in the character array is a hex color code
     */
    private static boolean isHexColorCode(char nextCharacter, int current, char[] characters) {
      if (nextCharacter != '#') {
        return false;
      }
      if (current + 7 > characters.length) {
        return false;
      }
      for (char character : Arrays.copyOfRange(characters, current + 2, current + 8)) {
        if (!HEX_CHARS.contains(character)) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * Formats text.
   *
   * @author Danny Nguyen
   * @version 0.0.22
   * @since 0.0.20
   */
  public static class Format {
    /**
     * Utility methods only.
     */
    private Format() {
    }

    /**
     * Capitalizes every letter and replaces all spaces with underscores.
     *
     * @param text text
     * @return enum formatted text
     */
    @NotNull
    public static String asEnum(@NotNull String text) {
      Objects.requireNonNull(text, "Null text");
      return text.replace(" ", "_").toUpperCase();
    }

    /**
     * Lowercases every letter and replaces all spaces with underscores.
     *
     * @param text text
     * @return identifier formatted text
     */
    @NotNull
    public static String asIdentifier(@NotNull String text) {
      Objects.requireNonNull(text, "Null text");
      return text.replace(" ", "_").toLowerCase();
    }

    /**
     * Properly capitalizes every word and replaces underscores with spaces.
     *
     * @param text text
     * @return title formatted text
     */
    public static String asTitle(@NotNull String text) {
      Objects.requireNonNull(text, "Null text");
      return capitalizeWords(text.replace('_', ' '));
    }

    /**
     * Properly capitalizes every word and replaces any provided characters with spaces.
     *
     * @param text               text
     * @param replacedCharacters characters to replace
     * @return title formatted text
     */
    @NotNull
    public static String asTitle(@NotNull String text, @NotNull Set<Character> replacedCharacters) {
      Objects.requireNonNull(text, "Null text");
      Objects.requireNonNull(replacedCharacters, "Null replaced characters");
      char[] chars = text.toCharArray();
      for (int i = 0; i < chars.length - 1; i++) {
        if (replacedCharacters.contains(chars[i])) {
          chars[i] = ' ';
        }
      }
      return capitalizeWords(new String(chars));
    }

    /**
     * Splits the text into words and capitalizes them.
     *
     * @param text text
     * @return properly capitalized words
     */
    private static String capitalizeWords(String text) {
      StringBuilder newText = new StringBuilder();
      for (String word : text.split(" ")) {
        newText.append(capitalizeWord(word)).append(" ");
      }
      return newText.toString().trim();
    }

    /**
     * Properly capitalizes the word.
     *
     * @param word word
     * @return properly capitalized word
     */
    private static String capitalizeWord(String word) {
      return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
  }
}
