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
 *   <li>respond to a {@link MessageInputRequest}
 * </ul>
 *
 * @author Danny Nguyen
 * @version 0.1.17
 * @since 0.1.10
 */
public class MessageManager {
  /**
   * Active {@link MessageInputRequest message input requests}.
   */
  private final Map<UUID, MessageInputRequest> activeInputRequests = new HashMap<>();

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

    MessageInputRequest request = activeInputRequests.get(player.getUniqueId());
    if (request != null) {
      MessageInputResponse response = new MessageInputResponse(player, message);
      event.setCancelled(true);
      return;
    }

    if (message.startsWith("-c") && event.getPlayer().hasPermission(Permission.Message.COLOR.asString())) {
      event.setMessage(TextUtils.Color.translate(message.substring(3), '&'));
    }
  }

  /**
   * {@link Message.Input} requested by a {@link MessageInputReceiver}.
   *
   * @param receiver {@link MessageInputReceiver}
   * @param input    {@link Message.Input}
   * @author Danny Nguyen
   * @version 0.1.17
   * @since 0.1.17
   */
  public record MessageInputRequest(@NotNull MessageInputReceiver receiver, @NotNull Message.Input input) {
    /**
     * Associates the {@link MessageInputReceiver} with its requested {@link Message.Input}.
     *
     * @param receiver {@link MessageInputReceiver}
     * @param input    {@link Message.Input}
     */
    public MessageInputRequest {
      Objects.requireNonNull(receiver, "Null receiver");
      Objects.requireNonNull(input, "Null input");
    }
  }

  /**
   * Response to a {@link MessageInputRequest}.
   *
   * @param player  interacting player
   * @param message interacting message
   * @author Danny Nguyen
   * @version 0.1.14
   * @since 0.1.13
   */
  private record MessageInputResponse(Player player, String message) {

  }

  /**
   * Message action flags.
   *
   * @param player  interacting player
   * @param message interacting message
   * @author Danny Nguyen
   * @version 0.1.14
   * @since 0.1.14
   */
  private record MessageFlag(Player player, String message) {

  }
}
