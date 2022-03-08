package dev._2lstudios.redstonelimiter.placeholders;

import org.bukkit.ChatColor;

public class Placeholders {
    public static String replace(String text, Placeholder ...placeholders) {
        text = ChatColor.translateAlternateColorCodes('&', text);

        for (Placeholder placeholder : placeholders) {
            text = text.replace(placeholder.getKey(), placeholder.getValue());
        }

        return text;
    }
}
