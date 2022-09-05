package com.ubivashka.plasmovoice.config.settings.command;

import java.io.File;
import java.util.Optional;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.config.PluginConfig;

public class FileCommandSettings implements ConfigurationHolder {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    @ConfigField
    private boolean enabled = true;
    @ConfigField
    private Optional<String> permission = Optional.empty();
    @ConfigField
    private File folder = PLUGIN.getDataFolder();
    @ConfigField("size-limit")
    private long sizeLimit = -1;

    public FileCommandSettings(ConfigurationSectionHolder sectionHolder) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(sectionHolder, this);
    }

    public FileCommandSettings(){
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Optional<String> getPermission() {
        return permission;
    }

    public File getFolder() {
        return folder;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }
}
