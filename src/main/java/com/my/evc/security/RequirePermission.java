package com.my.evc.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify what permission is required to call the corresponding
 * RESTful service. An permission list can be specified, if the caller is
 * granted any of the permission, the call successes; otherwise fails.
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

	/**
	 * The permissions which required to call the decorated RESTful service
	 * method successfully.
	 */
	int[] permissions() default {};
}
