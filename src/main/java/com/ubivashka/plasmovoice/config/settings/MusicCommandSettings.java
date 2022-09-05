package com.ubivashka.plasmovoice.config.settings;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.config.settings.command.FileCommandSettings;
import com.ubivashka.plasmovoice.config.settings.command.UrlCommandSettings;

public class MusicCommandSettings implements ConfigurationHolder {
    @ConfigField("url")
    private UrlCommandSettings urlCommandSettings = new UrlCommandSettings();
    @ConfigField("file")
    private FileCommandSettings fileCommandSettings = new FileCommandSettings();

    public MusicCommandSettings(ConfigurationSectionHolder sectionHolder) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(sectionHolder, this);
    }

    public MusicCommandSettings() {
    }

    public UrlCommandSettings getUrlCommandSettings() {
        return urlCommandSettings;
    }

    public FileCommandSettings getFileCommandSettings() {
        return fileCommandSettings;
    }
}
