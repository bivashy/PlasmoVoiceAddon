package com.bivashy.plasmovoice.config.settings.command;

import java.io.File;
import java.util.Optional;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.config.PluginConfig;
import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

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
