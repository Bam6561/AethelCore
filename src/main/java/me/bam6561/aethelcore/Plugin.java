package me.bam6561.aethelcore;

import me.bam6561.aethelcore.commands.DatabaseCommand;
import me.bam6561.aethelcore.commands.ItemEditorCommand;
import me.bam6561.aethelcore.guis.GUIManager;
import me.bam6561.aethelcore.listeners.GUIListener;
import me.bam6561.aethelcore.listeners.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the plugin as an object.
 * <p>
 * Through command executors and event listeners, the plugin can
 * handle various requests given to it by its users and the server.
 *
 * @author Danny Nguyen
 * @version 0.1.1
 * @since 0.0.1
 */
public class Plugin extends JavaPlugin {
  /**
   * {@link GUIManager}
   */
  private static final GUIManager guiManager = new GUIManager();

  /**
   * No parameter constructor.
   */
  public Plugin() {
  }

  /**
   * On enable:
   * <ul>
   *   <li>{@link #registerCommandExecutors() Registers commands}.
   *   <li>{@link #registerEventListeners() Registers event listeners}.
   * </ul>
   */
  @Override
  public void onEnable() {
    registerCommandExecutors();
    registerEventListeners();
  }

  /**
   * On disable:
   * <ul>
   *   <li>
   * </ul>
   */
  @Override
  public void onDisable() {
  }

  /**
   * Registers the plugin's commands.
   */
  private void registerCommandExecutors() {
    getCommand("database").setExecutor(new DatabaseCommand());
    getCommand("itemeditor").setExecutor(new ItemEditorCommand());
  }

  /**
   * Registers the plugin's event listeners.
   */
  private void registerEventListeners() {
    PluginManager manager = getServer().getPluginManager();
    manager.registerEvents(new GUIListener(), this);
    manager.registerEvents(new PlayerListener(), this);
  }

  /**
   * Gets the plugin.
   *
   * @return plugin instance
   */
  @NotNull
  public static Plugin getInstance() {
    return getPlugin(Plugin.class);
  }

  /**
   * Gets the {@link GUIManager}.
   *
   * @return {@link GUIManager}
   */
  @NotNull
  public static GUIManager getGUIManager() {
    return guiManager;
  }
}
