package dev._2lstudios.redstonelimiter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import dev._2lstudios.redstonelimiter.RedstoneBlocker;
import dev._2lstudios.redstonelimiter.RedstoneLimiter;
import dev._2lstudios.redstonelimiter.placeholders.Placeholder;

public class RedstoneLimiterCommand implements CommandExecutor {
    private RedstoneLimiter redstoneLimiter;
    private RedstoneBlocker redstoneBlocker;

    public RedstoneLimiterCommand(RedstoneLimiter redstoneLimiter, RedstoneBlocker redstoneBlocker) {
        this.redstoneLimiter = redstoneLimiter;
        this.redstoneBlocker = redstoneBlocker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Configuration config = redstoneLimiter.getConfig();

        if (args.length <= 0) {
            if (sender.hasPermission("redstonelimiter.stats")) {
                sender.sendMessage(Placeholder.replace(config.getString("statistics"),
                        new Placeholder("%total_redstone%", redstoneBlocker.getTotalRedstoneActivated()),
                        new Placeholder("%blocked_redstone%", redstoneBlocker.getTotalRedstoneBlocked()),
                        new Placeholder("%current_period%", redstoneBlocker.getPeriod()),
                        new Placeholder("%block_period%", config.getString("block_period")),
                        new Placeholder("%block_threshold%", config.getString("block_threshold"))));
            } else {
                sender.sendMessage(Placeholder.replace(config.getString("no_permission")));
            }
        } else {
            if (args[0].equals("reload")) {
                if (sender.hasPermission("redstonelimiter.reload")) {
                    redstoneLimiter.reloadConfig();
                    sender.sendMessage(Placeholder.replace(config.getString("reload")));
                } else {
                    sender.sendMessage(Placeholder.replace(config.getString("no_permission")));
                }
            } else {
                sender.sendMessage(Placeholder.replace(config
                        .getString("argument_not_found"), new Placeholder("%argument%", args[0])));
            }
        }

        return true;
    }
}
