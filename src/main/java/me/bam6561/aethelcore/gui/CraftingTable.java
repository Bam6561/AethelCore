package me.bam6561.aethelcore.gui;

import me.bam6561.aethelcore.interfaces.Workstation;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a crafting table's {@link Workstation}.
 *
 * @author Danny Nguyen
 * @version 0.0.5
 * @since 0.0.5
 */
public class CraftingTable implements InventoryHolder, Workstation {
  /**
   * Inventory.
   */
  private final Inventory inventory = Bukkit.createInventory(this, 54);

  /**
   * No parameter constructor.
   */
  public CraftingTable() {
  }

  /**
   * Gets the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  public Inventory getInventory() {
    return this.inventory;
  }
}
