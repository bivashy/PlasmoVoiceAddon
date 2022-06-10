package com.ubivashka.plasmovoice.config.factories;

import com.ubivashka.configuration.contexts.ConfigurationFieldContext;
import com.ubivashka.configuration.holders.ConfigurationSectionHolder;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolvers.ConfigurationFieldResolverFactory;
import com.ubivashka.plasmovoice.config.ConfigurationHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConfigurationHolderResolverFactory implements ConfigurationFieldResolverFactory {
    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldContext factoryContext) {
        if (factoryContext.isValueCollection())
            throw new UnsupportedOperationException("Collection unsupported for ConfigurationHolder");
        ConfigurationSectionHolder sectionHolder = getSectionHolder(factoryContext.configuration(),factoryContext.path());
        Class<?> fieldClass = factoryContext.valueType();
        try {
            for (Constructor<?> constructor : fieldClass.getDeclaredConstructors()) {
                if (constructor.getParameterCount() != 1)
                    continue;
                if (ConfigurationSectionHolder.class.isAssignableFrom(constructor.getParameterTypes()[0]))
                    return (context) -> {
                        try {
                            return (ConfigurationHolder) constructor.newInstance(sectionHolder);
                        } catch(InstantiationException |
                                IllegalAccessException |
                                IllegalArgumentException |
                                InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return null;
                    };
                if (constructor.getParameterTypes()[0].isAssignableFrom(factoryContext.configuration().getOriginalHolder().getClass()))
                    return (context) -> {
                        try {
                            return constructor.newInstance(sectionHolder.getOriginalHolder());
                        } catch(InstantiationException |
                                IllegalAccessException |
                                IllegalArgumentException |
                                InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return null;
                    };
            }
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ConfigurationSectionHolder getSectionHolder(ConfigurationSectionHolder root,String path){
        String[] fullPath = path.split("\\.");
        ConfigurationSectionHolder sectionHolder = root;
        for(String pathPiece:fullPath)
            sectionHolder = sectionHolder.getSection(pathPiece);
        return sectionHolder;
    }
}
