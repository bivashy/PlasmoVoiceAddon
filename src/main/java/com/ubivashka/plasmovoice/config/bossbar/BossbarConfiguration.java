package com.ubivashka.plasmovoice.config.bossbar;

import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.configuration.annotations.ImportantField;
import com.ubivashka.plasmovoice.config.ConfigurationHolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;

public class BossbarConfiguration implements ConfigurationHolder {
    @ImportantField
    @ConfigField("title")
    private String title;
    @ConfigField("color")
    private BarColor color = BarColor.PURPLE;
    @ConfigField("style")
    private BarStyle style = BarStyle.SEGMENTED_6;
    @ConfigField("disabled")
    private boolean isDisabled;

    public BossbarConfiguration() {
        isDisabled = true;
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
        return isDisabled;
    }

    public BossBar createBossbar() {
        if (isDisabled)
            return null;
        return Bukkit.createBossBar(title, color, style);
    }

}
