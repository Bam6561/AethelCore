package me.bam6561.aethelcore.guis.commands;

import me.bam6561.aethelcore.guis.GUI;
import me.bam6561.aethelcore.guis.commands.markers.Editor;
import me.bam6561.aethelcore.utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link me.bam6561.aethelcore.commands.ItemEditorCommand} {@link GUI}.
 * <p>
 * Collection of item metadata {@link Editor editors}.
 *
 * @author Danny Nguyen
 * @version 0.1.2
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
  }

  /**
   * Creates the inventory.
   *
   * @return inventory
   */
  @NotNull
  @Override
  protected Inventory createInventory() {
    Inventory inv = Bukkit.createInventory(null, 54, "Item Editor");
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
    return inv;
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory click event
   */
  @Override
  public void onClick(@NotNull InventoryClickEvent event) {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory open event
   */
  @Override
  public void onOpen(@NotNull InventoryOpenEvent event) {
  }

  /**
   * Currently does nothing.
   *
   * @param event inventory close event
   */
  @Override
  public void onClose(@NotNull InventoryCloseEvent event) {
  }
}
