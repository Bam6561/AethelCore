package me.bam6561.aethelcore.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Plugin managed inventory.
 *
 * @author Danny Nguyen
 * @version 0.1.9
 * @since 0.0.11
 */
public abstract class GUI implements InventoryHandler {
  /**
   * Inventory.
   */
  private final Inventory inventory = createInventory();

  /**
   * {@link GUI} user.
   */
  protected final Player user;

  /**
   * Associates the {@link GUI} with its user.
   *
   * @param user {@link GUI} user
   */
  public GUI(@NotNull Player user) {
    Objects.requireNonNull(user, "Null user");
    this.user = user;
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  protected abstract Inventory createInventory();

  /**
   * Adds inventory buttons.
   */
  protected abstract void addButtons();

  /**
   * Handles what to do with interactions in the inventory view.
   * <p>
   * Should only define clicks outside the inventory
   * view and clicks inside the player's inventory.
   *
   * @return finished interaction
   */
  protected abstract boolean handleInventoryViewInteraction(@NotNull InventoryClickEvent event);

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
