package com.ubivashka.plasmovoice.config.resolver;


import java.util.Optional;

import com.ubivashka.configuration.context.ConfigurationFieldFactoryContext;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolver;
import com.ubivashka.configuration.resolver.field.ConfigurationFieldResolverFactory;
import com.ubivashka.configuration.resolver.field.base.DefaultConfigurationFieldFactory;
import com.ubivashka.configuration.util.ClassMap;

public class OptionalConfigurationFieldResolver implements ConfigurationFieldResolverFactory {
    public static final ConfigurationFieldResolverFactory DEFAULT_FACTORY = new DefaultConfigurationFieldFactory();

    @Override
    public ConfigurationFieldResolver<?> createResolver(ConfigurationFieldFactoryContext context) {
        if (context.getConfigurationObject() == null)
            return (ignored) -> Optional.empty();
        ClassMap<ConfigurationFieldResolverFactory> factoryClassMap = (ClassMap<ConfigurationFieldResolverFactory>) context.processor()
                .getFieldResolverFactories();
        return (ignored) -> Optional.ofNullable(factoryClassMap.getAssignable(context.getGeneric(0), DEFAULT_FACTORY).createResolver(context));
    }
}
