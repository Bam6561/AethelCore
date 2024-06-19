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
 *   <li>respond to a {@link MessageInput.Request}
 * </ul>
 *
 * @author Danny Nguyen
 * @version 0.1.20
 * @since 0.1.10
 */
public class MessageManager {
  /**
   * Active {@link MessageInput.Request message input requests}.
   */
  private final Map<UUID, MessageInput.Request> activeInputRequests = new HashMap<>();

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

    MessageInput.Request request = activeInputRequests.get(player.getUniqueId());
    if (request != null) {
      MessageInput.Response response = new MessageInput.Response(request, player, message);
      response.interpretInput();
      event.setCancelled(true);
      return;
    }

    if (MessageFlag.hasMessageFlag(message)) {
      MessageFlag flag = new MessageFlag(event, player, message);
      flag.interpretAction();
    }
  }

  /**
   * Queries a message input from the interacting player.
   *
   * @param player   interacting player
   * @param receiver {@link MessageInputReceiver}
   * @param input    {@link Message.Input}
   */
  public void queryMessageInput(@NotNull Player player, @NotNull MessageInputReceiver receiver, @NotNull Message.Input input) {
    Objects.requireNonNull(player, "Null player");
    Objects.requireNonNull(receiver, "Null receiver");
    Objects.requireNonNull(input, "Null input");
    activeInputRequests.put(player.getUniqueId(), new MessageInput.Request(receiver, input));
  }

  /**
   * Message input {@link Request} and {@link Response}.
   *
   * @author Danny Nguyen
   * @version 0.1.20
   * @since 0.1.19
   */
  private static class MessageInput {
    /**
     * Nested classes only.
     */
    private MessageInput() {
    }

    /**
     * {@link Message.Input} requested by a {@link MessageInputReceiver}.
     *
     * @param receiver {@link MessageInputReceiver}
     * @param input    {@link Message.Input}
     * @author Danny Nguyen
     * @version 0.1.20
     * @since 0.1.17
     */
    private record Request(@NotNull MessageInputReceiver receiver, @NotNull Message.Input input) {
      /**
       * Associates the {@link MessageInputReceiver} with its requested {@link Message.Input}.
       */
      private Request {
      }

      /**
       * Gets the {@link MessageInputReceiver}.
       *
       * @return {@link MessageInputReceiver}
       */
      private MessageInputReceiver getReceiver() {
        return this.receiver;
      }

      /**
       * Gets the {@link Message.Input}.
       *
       * @return {@link Message.Input}
       */
      private Message.Input getInput() {
        return this.input;
      }
    }

    /**
     * Response to a {@link Request}.
     *
     * @author Danny Nguyen
     * @version 0.1.21
     * @since 0.1.13
     */
    private static class Response {
      /**
       * {@link MessageInputReceiver}
       */
      private final MessageInputReceiver receiver;

      /**
       * {@link Message.Input}
       */
      private final Message.Input input;

      /**
       * Interacting player.
       */
      private final Player player;

      /**
       * Interacting message.
       */
      private final String message;

      /**
       * Unboxes the {@link Request} into its {@link MessageInputReceiver},
       * {@link Message.Input}, player, and message.
       *
       * @param request {@link Request}
       * @param player  interacting player
       * @param message interacting message
       */
      private Response(Request request, Player player, String message) {
        this.receiver = request.getReceiver();
        this.input = request.getInput();
        this.player = player;
        this.message = message;
      }

      /**
       * Interprets the {@link Message.Input}.
       */
      private void interpretInput() {
        switch (input) {
          case CUSTOM_MODEL_DATA -> inputCustomModelData();
          case DISPLAY_NAME -> inputDisplayName();
          case LORE_ADD -> inputLoreAdd();
          case LORE_EDIT -> inputLoreEdit();
          case LORE_INSERT -> inputLoreInsert();
          case LORE_REMOVE -> inputLoreRemove();
          case LORE_SET -> inputLoreSet();
        }
      }

      /**
       * {@link Message.Input#CUSTOM_MODEL_DATA}
       */
      private void inputCustomModelData() {
      }

      /**
       * {@link Message.Input#DISPLAY_NAME}
       */
      private void inputDisplayName() {
      }

      /**
       * {@link Message.Input#LORE_ADD}
       */
      private void inputLoreAdd() {
      }

      /**
       * {@link Message.Input#LORE_EDIT}
       */
      private void inputLoreEdit() {
      }

      /**
       * {@link Message.Input#LORE_INSERT}
       */
      private void inputLoreInsert() {
      }

      /**
       * {@link Message.Input#LORE_REMOVE}
       */
      private void inputLoreRemove() {
      }

      /**
       * {@link Message.Input#LORE_SET}
       */
      private void inputLoreSet() {
      }
    }
  }

  /**
   * Message flags.
   *
   * @author Danny Nguyen
   * @version 0.1.20
   * @since 0.1.14
   */
  private static class MessageFlag {
    /**
     * Message event.
     */
    private final AsyncPlayerChatEvent event;

    /**
     * Interacting player.
     */
    private final Player player;

    /**
     * Interacting message.
     */
    private final String message;

    /**
     * Message flag.
     */
    private final Character flag;

    /**
     * Associates the event with its player, message, and message flag to be used.
     *
     * @param event   async player chat event
     * @param player  interacting player
     * @param message interacting message
     */
    private MessageFlag(AsyncPlayerChatEvent event, Player player, String message) {
      this.event = event;
      this.player = player;
      this.message = message.substring(3);
      this.flag = message.charAt(1);
    }

    /**
     * Either:
     * <ul>
     *   <li>color codes messages
     * </ul>
     */
    private void interpretAction() {
      switch (flag) {
        case 'c' -> {
          if (player.hasPermission(Permission.Message.COLOR.asString())) {
            event.setMessage(TextUtils.Color.translate(message, '&'));
          } else {
            player.sendMessage(Message.Error.INSUFFICIENT_PERMISSION.asString());
            event.setCancelled(true);
          }
        }
      }
    }

    /**
     * If the message contains a {@link MessageFlag}.
     *
     * @param message interacting message
     * @return if the message contains a {@link MessageFlag}
     */
    private static boolean hasMessageFlag(String message) {
      return message.startsWith("-") && message.length() > 3 && message.charAt(2) == ' ';
    }
  }
}
