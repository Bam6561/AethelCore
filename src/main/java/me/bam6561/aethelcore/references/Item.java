package me.bam6561.aethelcore.references;

import me.bam6561.aethelcore.Plugin;
import me.bam6561.aethelcore.references.markers.ItemStackValue;
import me.bam6561.aethelcore.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * {@link Plugin} items.
 *
 * @author Danny Nguyen
 * @version 0.2.2
 * @since 0.2.2
 */
public class Item {
  /**
   * Enum usage only.
   */
  private Item() {
  }

  /**
   * Player head textures.
   *
   * @author Danny Nguyen
   * @version 0.2.2
   * @since 0.2.2
   */
  public enum PlayerHead implements ItemStackValue {
    /**
     * Arrow pointing up on an iron block.
     */
    ARROW_UP_IRON_BLOCK(createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA1YTJjYWI4YjY4ZWE1N2UzYWY5OTJhMzZlNDdjOGZmOWFhODdjYzg3NzYyODE5NjZmOGMzY2YzMWEzOCJ9fX0=")),

    /**
     * Arrow pointing left on a red colored block.
     */
    ARROW_LEFT_RED(createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRmNWMyZjg5M2JkM2Y4OWNhNDA3MDNkZWQzZTQyZGQwZmJkYmE2ZjY3NjhjODc4OWFmZGZmMWZhNzhiZjYifX19")),

    /**
     * Arrowing pointing right with a lime colored block.
     */
    ARROW_RIGHT_LIME(createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjUyN2ViYWU5ZjE1MzE1NGE3ZWQ0OWM4OGMwMmI1YTlhOWNhN2NiMTYxOGQ5OTE0YTNkOWRmOGNjYjNjODQifX19"));

    /**
     * Player head.
     */
    private final ItemStack item;

    /**
     * Associates the player head with a texture.
     *
     * @param item player head
     */
    PlayerHead(ItemStack item) {
      this.item = item;
    }

    /**
     * Gets the player head.
     *
     * @return player head
     */
    @Override
    @NotNull
    public ItemStack asItem() {
      return this.item;
    }

    /**
     * Creates a player head from provided texture data.
     *
     * @param textureData encoded texture
     * @return player head with texture
     */
    private static ItemStack createPlayerHead(String textureData) {
      PlayerProfile profile = createProfile(getUrlFromTextureData(textureData));
      if (profile != null) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile);
        head.setItemMeta(meta);
        return head;
      } else {
        return ItemUtils.Create.createItem(Material.BARRIER, org.bukkit.ChatColor.RED + "[!] Error", List.of(ChatColor.RED + "Invalid texture."));
      }
    }

    /**
     * Deserializes a url from encoded texture data.
     *
     * @param textureData encoded texture
     * @return texture url
     */
    private static URL getUrlFromTextureData(String textureData) {
      String urlString = new String(Base64.getDecoder().decode(textureData));
      URL url;
      try {
        url = new URL(urlString.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), urlString.length() - "\"}}}".length()));
      } catch (MalformedURLException ex) {
        Bukkit.getLogger().warning("[Aethel] Invalid player head texture: " + textureData);
        return null;
      }
      return url;
    }

    /**
     * Creates a player profile.
     *
     * @param url texture url
     * @return player profile with desired texture
     */
    private static PlayerProfile createProfile(URL url) {
      if (url != null) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString("58f8c6e4-8e24-4429-badc-ecf76de5bead"));
        PlayerTextures textures = profile.getTextures();
        textures.setSkin(url);
        profile.setTextures(textures);
        return profile;
      }
      return null;
    }
  }
}
