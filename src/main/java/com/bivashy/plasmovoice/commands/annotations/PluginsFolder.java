package com.bivashy.plasmovoice.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If this annotation present on File method parameter, File will be fetched from /plugins/PLUGINNAME folder.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PluginsFolder {
    boolean onlyPluginsFolder() default true;
}
