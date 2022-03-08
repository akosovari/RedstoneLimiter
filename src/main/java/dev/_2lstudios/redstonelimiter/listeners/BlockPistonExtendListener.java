package dev._2lstudios.redstonelimiter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import dev._2lstudios.redstonelimiter.RedstoneBlocker;
import dev._2lstudios.redstonelimiter.RedstoneLimiter;

public class BlockPistonExtendListener implements Listener {
    private RedstoneLimiter redstoneLimiter;
    private RedstoneBlocker redstoneBlocker;

    public BlockPistonExtendListener(RedstoneLimiter redstoneLimiter, RedstoneBlocker redstoneBlocker) {
        this.redstoneLimiter = redstoneLimiter;
        this.redstoneBlocker = redstoneBlocker;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (event.getBlocks().size() > redstoneLimiter.getConfig().getInt("max_piston_push")) {
            redstoneBlocker.block(event.getBlock());
            event.setCancelled(true);
        }
    }
}
