package com.bivashy.plasmovoice.config.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.annotation.ImportantField;
import com.bivashy.plasmovoice.config.PluginConfig;

public class BossbarConfiguration implements ConfigurationHolder {
    @ImportantField
    @ConfigField
    private String title;
    @ConfigField
    private BarColor color = BarColor.PURPLE;
    @ConfigField
    private BarStyle style = BarStyle.SEGMENTED_6;
    @ConfigField
    private boolean disabled;

    public BossbarConfiguration() {
        disabled = true;
    }

    public BossbarConfiguration(ConfigurationSection configurationSection) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(configurationSection, this);
    }

    public String getTitle() {
        return title;
    }

    public BarColor getColor() {
        return color;
    }

    public BarStyle getStyle() {
        return style;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public BossBar createBossbar() {
        if (disabled)
            return null;
        return Bukkit.createBossBar(title, color, style);
    }

}
