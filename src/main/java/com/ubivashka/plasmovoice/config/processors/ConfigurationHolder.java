package com.ubivashka.plasmovoice.config.processors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.configuration.ConfigurationSection;

public abstract class ConfigurationHolder {
	protected final ConfigurationSection configurationSection;

	public ConfigurationHolder(ConfigurationSection configurationSection) {
		this.configurationSection = configurationSection;
		setupFields();
	}

	public ConfigurationHolder(ConfigurationSection configurationSection, boolean isSetupField) {
		this.configurationSection = configurationSection;
		if (isSetupField)
			setupFields();
	}

	protected void setupFields(Class<? extends ConfigurationHolder> clazz) {
		for (Field f : clazz.getDeclaredFields())
			setupField(f, clazz);

	}

	protected void setupFields() {
		setupFields(getClass());
	}

	private boolean setupField(Field f, Class<? extends ConfigurationHolder> clazz) {
		boolean fieldAccesible = f.isAccessible();
		f.setAccessible(true);

		if (!f.isAnnotationPresent(ConfigField.class))
			return false;

		ConfigField cf = f.getAnnotation(ConfigField.class);
		String configPath = cf.path();
		if (configPath == null || configPath.isEmpty())
			configPath = f.getName();

		if (!configurationSection.contains(configPath)) {
			if (cf.important())
				cf.importantAction().doAction(configPath, configurationSection);

			return false;
		}

		Object value = configurationSection.get(configPath);

		if (!cf.castMethod().isEmpty()) {
			try {
				Method castMethod = clazz.getDeclaredMethod(cf.castMethod(), Object.class);
				boolean accessible = castMethod.isAccessible();
				castMethod.setAccessible(true);
				value = castMethod.invoke(this, value);
				castMethod.setAccessible(accessible);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		try {
			f.set(this, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			f.setAccessible(fieldAccesible);
		}
		return true;
	}

}
