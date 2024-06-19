package me.bam6561.aethelcore.guis;

import me.bam6561.aethelcore.guis.markers.ChatInput;
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
 * Messages may contain:
 * <ul>
 *   <li>chat flags
 *   <li>responses for {@link ChatInput}
 * </ul>
 *
 * @author Danny Nguyen
 * @version 0.1.13
 * @since 0.1.10
 */
public class MessageManager {
  /**
   * Users prompted for a {@link ChatResponse}.
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
    Message.Input input = inputs.get(event.getPlayer().getUniqueId());
    if (input != null) {
      event.setCancelled(true);
      return;
    }

    String message = event.getMessage();
    if (message.startsWith("-c") && event.getPlayer().hasPermission(Permission.ChatFlag.COLOR.asString())) {
      event.setMessage(TextUtils.Color.translate(message.substring(3), '&'));
    }
  }

  /**
   * {@link ChatInput} responses.
   *
   * @author Danny Nguyen
   * @version 0.1.13
   * @since 0.1.13
   */
  public class ChatResponse {
    /**
     * Message source.
     */
    private final Player player;

    /**
     * Message contents.
     */
    private final String response;

    /**
     * Associates a message with its source.
     *
     * @param player  interacting player
     * @param message interacting message
     */
    public ChatResponse(@NotNull Player player, @NotNull String message) {
      Objects.requireNonNull(player, "Null player");
      Objects.requireNonNull(message, "Null message");
      this.player = player;
      this.response = message;
    }
  }
}
