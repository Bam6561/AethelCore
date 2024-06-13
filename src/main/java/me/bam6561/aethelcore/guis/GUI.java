package me.bam6561.aethelcore.guis;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin managed inventory.
 *
 * @author Danny Nguyen
 * @version 0.0.18
 * @since 0.0.11
 */
public abstract class GUI implements InventoryHandler {
  /**
   * Inventory.
   */
  private final Inventory inventory = createInventory();

  /**
   * No parameter constructor.
   */
  public GUI() {
  }

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
