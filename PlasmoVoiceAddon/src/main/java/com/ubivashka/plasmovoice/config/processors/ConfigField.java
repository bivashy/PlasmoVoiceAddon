package com.ubivashka.plasmovoice.config.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.LOCAL_VARIABLE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField {
	String path() default "";

	boolean important() default false;

	ImportantAction importantAction() default ImportantAction.LOG_DISABLE_PLUGIN;

	String castMethod() default "";
}
