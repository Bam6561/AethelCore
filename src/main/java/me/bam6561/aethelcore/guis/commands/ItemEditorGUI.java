package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * {@link me.bam6561.aethelcore.commands.ItemEditorCommand} {@link GUI}.
 * <p>
 * Collection of item metadata {@link Editor editors}.
 *
 * @author Danny Nguyen
 * @version 0.1.6
 * @since 0.0.27
 */
public class ItemEditorGUI extends GUI implements Editor {
  /**
   * Interacting item.
   */
  private ItemStack item;

  /**
   * Associates the {@link GUI} with its user and interacting item.
   *
   * @param user {@link GUI} user
   * @param item interacting item
   */
  public ItemEditorGUI(@NotNull Player user, @Nullable ItemStack item) {
    super(user);
    this.item = item;
    addButtons();
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, "Item Editor");
  }

  /**
   * Adds accessible {@link Editor editors}.
   */
  @Override
  protected void addButtons() {
    Inventory inv = getInventory();
    inv.setItem(4, item);

    inv.setItem(10, ItemUtils.Create.createItem(Material.NAME_TAG, ChatColor.AQUA + "Appearance"));
    inv.setItem(11, ItemUtils.Create.createItem(Material.ANVIL, ChatColor.AQUA + "Durability"));
    inv.setItem(19, ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Attributes", ItemFlag.HIDE_ATTRIBUTES));
    inv.setItem(20, ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantments"));
    inv.setItem(21, ItemUtils.Create.createItem(Material.TNT, ChatColor.AQUA + "Skills"));

    inv.setItem(15, ItemUtils.Create.createItem(Material.PAPER, ChatColor.AQUA + "Stack Size"));
    inv.setItem(23, ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Potion Effects", ItemFlag.HIDE_ADDITIONAL_TOOLTIP));
    inv.setItem(24, ItemUtils.Create.createItem(Material.GOLDEN_APPLE, ChatColor.AQUA + "Food"));
    inv.setItem(25, ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Tool", ItemFlag.HIDE_ATTRIBUTES));
    refreshDynamicButtons();
  }

  /**
   * Either:
   * <ul>
   *   <li>sets the interacting item
   *   <li>sets the item's max stack size
   *   <li>opens a item metadata {@link Editor}
   * </ul>
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
    Objects.requireNonNull(event, "Null event");
    Inventory cInv = event.getClickedInventory();
    if (cInv == null) {
      return;
    }

    if (cInv.getType() == InventoryType.PLAYER) {
      if (event.getClick().isShiftClick()) {
        event.setCancelled(true);
        InventoryView view = event.getView();
        if (ItemUtils.Read.isNullOrAir(view.getItem(4))) {
          this.item = event.getCurrentItem();
          view.setItem(4, event.getCurrentItem());
          view.setItem(event.getRawSlot(), null);
          refreshDynamicButtons();
          return;
        }
      } else if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
        event.setCancelled(true);
      }
    } else {
      event.setCancelled(true);
    }

    switch (event.getRawSlot()) {
      case 4 -> {
        event.setCancelled(false);
        Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> {
          this.item = getInventory().getItem(4);
          refreshDynamicButtons();
        }, 1);
      }
      case 10 -> {
        if (ItemUtils.Read.isNotNullOrAir(item)) {
          Plugin.getGUIManager().openGUI(user, new ItemAppearanceGUI(user, item.clone()));
        } else {
          Plugin.getGUIManager().openGUI(user, new ItemAppearanceGUI(user, item));
        }
      }
    }
  }

  /**
   * Cancels dragging items in the inventory.
   *
   * @param event inventory drag event
   */
  @Override
  public void onDrag(@NotNull InventoryDragEvent event) {
    Objects.requireNonNull(event, "Null event");
    event.setCancelled(true);
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
    Objects.requireNonNull(event, "Null event");
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

  /**
   * Shows or hides an "Add to Database" button.
   */
  @Override
  public void refreshDynamicButtons() {
    Inventory inv = getInventory();
    if (ItemUtils.Read.isNotNullOrAir(item)) {
      inv.setItem(40, ItemUtils.Create.createItem(Material.BOOKSHELF, ChatColor.AQUA + "Save to Database"));
    } else {
      inv.setItem(40, null);
    }
  }
}
