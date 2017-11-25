package com.annotation.values;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is processed by {@link } class.
 * <p>
 * Use this annotation if you want to skip a childTest based plaform.
 *
 * @author saikrisv
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public @interface SkipIf {
    String platform();
}
