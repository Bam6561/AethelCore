package me.bam6561.aethelcore.templates;

import me.bam6561.aethelcore.guis.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.1.9
 * @since 0.0.27
 */
public class GUITemplate extends GUI {
  /**
   * Associates the {@link GUI} with its user.
   *
   * @param user {@link GUI} user
   */
  public GUITemplate(@NotNull Player user) {
    super(user);
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, "Template");
  }

  /**
   * Currently does nothing.
   */
  @Override
  protected void addButtons() {
  }

  /**
   * Finishes interactions early if the user clicks
   * outside any inventories or uses their player inventory.
   *
   * @param event inventory click event
   * @return finished interaction
   */
  @Override
  protected boolean handleInventoryViewInteraction(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    Inventory cInv = event.getClickedInventory();
    if (cInv == null) {
      return true;
    }
    if (cInv.getType() == InventoryType.PLAYER) {
      return true;
    }
    return false;
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    if (handleInventoryViewInteraction(event)) {
      return;
    }

    event.setCancelled(true);
  }

  /**
   * Cancels dragging items while the inventory is open.
   *
   * @param event inventory drag event
   */
  @Override
  public void onDrag(@NotNull InventoryDragEvent event) {
    Objects.requireNonNull(event, "Null event");
    event.setCancelled(true);
  }

  /**
   * Adds buttons to the {@link GUI}.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null event");
    addButtons();
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory close event
   */
  @Override
  public void onClose(@NotNull InventoryCloseEvent event) {
    Objects.requireNonNull(event, "Null event");
  }
}
