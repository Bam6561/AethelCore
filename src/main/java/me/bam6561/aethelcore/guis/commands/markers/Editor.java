package me.bam6561.aethelcore.guis.commands.markers;

import me.bam6561.aethelcore.guis.GUI;

/**
 * {@link GUI} associated with a command that creates and/or modifies items.
 *
 * @author Danny Nguyen
 * @version 0.1.6
 * @since 0.0.19
 */
public interface Editor {
  /**
   * Adds or removes buttons based on the interacting item.
   */
  void refreshDynamicButtons();
}
