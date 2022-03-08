package dev._2lstudios.redstonelimiter.placeholders;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;

public class Placeholder {
    public static String replace(String text, Placeholder ...placeholders) {
        text = ChatColor.translateAlternateColorCodes('&', text);

        for (Placeholder placeholder : placeholders) {
            text = text.replace(placeholder.getKey(), placeholder.getValue());
        }

        return text;
    }

    private String key;
    private String value;

    public Placeholder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Placeholder(String key, int value) {
        this(key, new DecimalFormat("###,###,###,###").format(value));
    }

    public Placeholder(String key, long value) {
        this(key, new DecimalFormat("###,###,###,###").format(value));
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
