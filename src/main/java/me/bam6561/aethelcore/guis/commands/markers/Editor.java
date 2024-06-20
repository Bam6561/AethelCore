package me.bam6561.aethelcore.guis.commands.markers;

import me.bam6561.aethelcore.guis.GUI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * {@link GUI} associated with a command that creates and/or modifies items.
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
