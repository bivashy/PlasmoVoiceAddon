package com.ubivashka.plasmovoice.config.settings.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;
import com.ubivashka.plasmovoice.config.PluginConfig;

public class UrlCommandSettings implements ConfigurationHolder {
    @ConfigField
    private boolean enabled = true;
    @ConfigField
    private Optional<String> permission = Optional.empty();
    @ConfigField("whitelist-schemes")
    private List<String> whitelistSchemes = Arrays.asList("http", "https");
    @ConfigField("size-limit")
    private long sizeLimit = -1;
    @ConfigField("block-undefined-size")
    private boolean blockUndefinedSize = false;
    @ConfigField("caching")
    private CachingSettings cachingSettings = new CachingSettings();

    public UrlCommandSettings(ConfigurationSectionHolder sectionHolder) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(sectionHolder, this);
    }

    public UrlCommandSettings(){
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Optional<String> getPermission() {
        return permission;
    }

    public List<String> getWhitelistSchemes() {
        return whitelistSchemes;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public boolean shouldBlockUndefinedSize() {
        return blockUndefinedSize;
    }

    public CachingSettings getCachingSettings() {
        return cachingSettings;
    }

    public static class CachingSettings implements ConfigurationHolder {
        @ConfigField
        private boolean enabled;

        public CachingSettings(ConfigurationSectionHolder sectionHolder) {
            PluginConfig.CONFIGURATION_PROCESSOR.resolve(sectionHolder, this);
        }

        public CachingSettings() {
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
