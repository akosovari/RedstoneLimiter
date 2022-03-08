package dev._2lstudios.redstonelimiter;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.redstonelimiter.commands.RedstoneLimiterCommand;
import dev._2lstudios.redstonelimiter.listeners.BlockPistonExtendListener;
import dev._2lstudios.redstonelimiter.listeners.BlockRedstoneListener;

public class RedstoneLimiter extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();
        RedstoneBlocker redstoneBlocker = new RedstoneBlocker(this);

        getCommand("redstonelimiter").setExecutor(new RedstoneLimiterCommand(this, redstoneBlocker));

        pluginManager.registerEvents(new BlockPistonExtendListener(this, redstoneBlocker), this);
        pluginManager.registerEvents(new BlockRedstoneListener(redstoneBlocker), this);
    }
}