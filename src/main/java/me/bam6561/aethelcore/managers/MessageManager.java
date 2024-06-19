package me.bam6561.aethelcore.managers;

import me.bam6561.aethelcore.guis.markers.MessageInputReceiver;
import me.bam6561.aethelcore.references.Message;
import me.bam6561.aethelcore.references.Permission;
import me.bam6561.aethelcore.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages messages sent by players.
 * <p>
 * Messages may:
 * <ul>
 *   <li>contain {@link MessageFlag message flags}
 *   <li>respond to a {@link MessageInputReceiver}
 * </ul>
 *
 * @author Danny Nguyen
 * @version 0.1.14
 * @since 0.1.10
 */
public class MessageManager {
  /**
   * Users prompted for a {@link Response}.
   */
  private final Map<UUID, Message.Input> inputs = new HashMap<>();

  /**
   * No parameter constructor.
   */
  public MessageManager() {
  }

  /**
   * Handles player messages.
   *
   * @param event async player chat event
   */
  public void handleMessage(@NotNull AsyncPlayerChatEvent event) {
    Objects.requireNonNull(event, "Null event");
    Player player = event.getPlayer();
    String message = event.getMessage();

    Message.Input input = inputs.get(player.getUniqueId());
    if (input != null) {
      Response response = new Response(player, message);
      event.setCancelled(true);
      return;
    }

    if (message.startsWith("-c") && event.getPlayer().hasPermission(Permission.Message.COLOR.asString())) {
      event.setMessage(TextUtils.Color.translate(message.substring(3), '&'));
    }
  }

  /**
   * Non-command action flags.
   *
   * @param player  interacting player
   * @param message interacting message
   * @author Danny Nguyen
   * @version 0.1.14
   * @since 0.1.14
   */
  private record MessageFlag(Player player, String message) {

  }

  /**
   * Response to a {@link MessageInputReceiver}.
   *
   * @param player  interacting player
   * @param message interacting message
   * @author Danny Nguyen
   * @version 0.1.14
   * @since 0.1.13
   */
  private record Response(Player player, String message) {

  }
}
