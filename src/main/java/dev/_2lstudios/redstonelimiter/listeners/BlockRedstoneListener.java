package dev._2lstudios.redstonelimiter.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import dev._2lstudios.redstonelimiter.RedstoneBlocker;

public class BlockRedstoneListener implements Listener {
    private RedstoneBlocker redstoneBlocker;

    public BlockRedstoneListener(RedstoneBlocker redstoneBlocker) {
        this.redstoneBlocker = redstoneBlocker;
    }

    @EventHandler
    public void onPlayerJoin(BlockRedstoneEvent event) {
        int oldCurrent = event.getOldCurrent();
        int newCurrent = event.getNewCurrent();

        if (oldCurrent == 0 && newCurrent > 0) {
            Block block = event.getBlock();

            if (redstoneBlocker.reachedThreshold(block)) {
                event.setNewCurrent(0);
                redstoneBlocker.block(block);
            } else {
                redstoneBlocker.activate(block);
            }
        }
    }
}
