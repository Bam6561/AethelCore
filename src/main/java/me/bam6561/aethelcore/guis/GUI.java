package me.bam6561.aethelcore.guis;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Inventory managed by the {@link me.bam6561.aethelcore.Plugin}.
 *
 * @author Danny Nguyen
 * @version 0.0.16
 * @since 0.0.11
 */
public abstract class GUI implements InventoryHandler {
  /**
   * Inventory.
   */
  private final Inventory inventory = createInventory();

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  protected abstract Inventory createInventory();

  /**
   * Gets the inventory.
   *
   * @return inventory
   */
  @NotNull
  public Inventory getInventory() {
    return this.inventory;
  }
}
