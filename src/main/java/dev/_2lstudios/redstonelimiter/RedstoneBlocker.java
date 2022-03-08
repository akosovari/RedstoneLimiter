package dev._2lstudios.redstonelimiter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;

public class RedstoneBlocker {
    private RedstoneLimiter redstoneLimiter;

    public RedstoneBlocker(RedstoneLimiter redstoneLimiter) {
        this.redstoneLimiter = redstoneLimiter;
    }

    private Map<Block, Integer> activations = new HashMap<>();
    private long lastPeriod = 0;
    private int totalRedstoneActivated = 0;
    private int totalRedstoneBlocked = 0;

    public int getActivations(Block block) {
        return activations.getOrDefault(block, 0);
    }

    private void updatePeriod() {
        long currentMillis = System.currentTimeMillis();
        long period = currentMillis - lastPeriod;

        if (lastPeriod == 0 || period >= redstoneLimiter.getConfig().getInt("block_period")) {
            activations.clear();
            lastPeriod = currentMillis - period % 500;
        }
    }

    public long getPeriod() {
        updatePeriod();
        return System.currentTimeMillis() - lastPeriod;
    }

    private void updateActivations() {
        if (getPeriod() >= redstoneLimiter.getConfig().getInt("block_period")) {
            activations.clear();
            lastPeriod = System.currentTimeMillis();
        }
    }

    public void activate(Block block) {
        updateActivations();
        activations.put(block, getActivations(block) + 1);
        totalRedstoneActivated++;
    }

    private void playEffect(Block block, String effectName, int effectData) {
        if (effectName != null && !effectName.isEmpty()) {
            block.getWorld().playEffect(block.getLocation().add(0.5, 0, 0.5), Effect.valueOf(effectName), effectData);
        }
    }

    private void playBlockEffect(Block block) {
        Configuration config = redstoneLimiter.getConfig();
        String effectName = config.getString("block_effect");
        int effectData = config.getInt("block_effect_data");

        playEffect(block, effectName, effectData);
    }

    public void block(Block block) {
        totalRedstoneBlocked++;
        playBlockEffect(block);
    }

    public int getTotalRedstoneActivated() {
        return totalRedstoneActivated;
    }

    public int getTotalRedstoneBlocked() {
        return totalRedstoneBlocked;
    }

    public boolean reachedThreshold(Block block) {
        updateActivations();
        return getActivations(block) >= redstoneLimiter.getConfig().getInt("block_threshold");
    }
}
