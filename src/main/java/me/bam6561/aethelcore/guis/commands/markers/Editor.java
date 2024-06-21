package me.bam6561.aethelcore.guis.commands.markers;

import me.bam6561.aethelcore.guis.GUI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * {@link GUI} associated with a command that creates and/or modifies items.
 * <p>
 * {@link GUI GUIs} that implement this interface:
 * <ul>
 *   <li>contain a DynamicButtons class with a nested Button enumeration and Display record
 *   <li>handle situations where a player clicks on a dynamic button slot that has been hidden
 * </ul>
 *
 * @author Danny Nguyen
 * @version 0.1.25
 * @since 0.0.19
 */
public interface Editor {
  /**
   * Adds or removes buttons based on the interacting item.
   */
  void updateDynamicButtons();

  /**
   * Gets the item being edited.
   *
   * @return item being edited
   */
  @Nullable
  ItemStack getItem();
}
