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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Item appearance {@link GUI}.
 *
 * @author Danny Nguyen
 * @version 0.1.4
 * @since 0.1.2
 */
public class ItemAppearanceGUI extends GUI implements Editor {
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
  public ItemAppearanceGUI(@NotNull Player user, @Nullable ItemStack item) {
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
    Inventory inv = Bukkit.createInventory(null, 54, "Item Appearance");
    inv.setItem(2, ItemUtils.Create.createItem(Material.POTATO, ChatColor.AQUA + "Item Editor"));
    inv.setItem(4, item);

    inv.setItem(10, ItemUtils.Create.createItem(Material.NAME_TAG, ChatColor.AQUA + "Display Name"));
    inv.setItem(11, ItemUtils.Create.createItem(Material.SPYGLASS, ChatColor.AQUA + "Custom Model Data"));
    inv.setItem(12, ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchantment Glint Override"));

    inv.setItem(20, ItemUtils.Create.createItem(Material.BOOK, ChatColor.AQUA + "Lore"));
    inv.setItem(28, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Add"));
    inv.setItem(29, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Insert"));
    inv.setItem(30, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Edit"));
    inv.setItem(37, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Set"));
    inv.setItem(38, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Remove"));
    inv.setItem(39, ItemUtils.Create.createItem(Material.WRITABLE_BOOK, ChatColor.AQUA + "Clear"));

    inv.setItem(15, ItemUtils.Create.createItem(Material.WHITE_BANNER, ChatColor.AQUA + "Item Flags"));
    inv.setItem(23, ItemUtils.Create.createItem(Material.POTION, ChatColor.AQUA + "Additional Tooltip", ItemFlag.HIDE_ADDITIONAL_TOOLTIP));
    inv.setItem(24, ItemUtils.Create.createItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.AQUA + "Armor Trim", ItemFlag.HIDE_ADDITIONAL_TOOLTIP));
    inv.setItem(25, ItemUtils.Create.createItem(Material.IRON_SWORD, ChatColor.AQUA + "Attributes", ItemFlag.HIDE_ATTRIBUTES));
    inv.setItem(32, ItemUtils.Create.createItem(Material.IRON_PICKAXE, ChatColor.AQUA + "Destroys", ItemFlag.HIDE_ATTRIBUTES));
    inv.setItem(33, ItemUtils.Create.createItem(Material.RED_DYE, ChatColor.AQUA + "Dyes"));
    inv.setItem(34, ItemUtils.Create.createItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "Enchants"));
    inv.setItem(41, ItemUtils.Create.createItem(Material.GRASS_BLOCK, ChatColor.AQUA + "Placed On"));
    inv.setItem(42, ItemUtils.Create.createItem(Material.BEDROCK, ChatColor.AQUA + "Unbreakable"));
    inv.setItem(43, ItemUtils.Create.createItem(Material.INK_SAC, ChatColor.AQUA + "Hide Tooltip"));
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
   * @param event inventory drag event
   */
  @Override
  public void onDrag(@NotNull InventoryDragEvent event) {
    Objects.requireNonNull(event, "Null event");
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
