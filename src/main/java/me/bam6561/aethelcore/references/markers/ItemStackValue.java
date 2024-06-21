package me.bam6561.aethelcore.references.markers;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * References used primarily as ItemStacks.
 *
 * @author Danny Nguyen
 * @version 0.2.2
 * @since 0.2.2
 */
public interface ItemStackValue {
  /**
   * Gets the enum's ItemStack value.
   *
   * @return enum's ItemStack value
   */
  @NotNull
  ItemStack asItem();
}
