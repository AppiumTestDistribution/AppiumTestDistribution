package com.annotation.values;

import com.appium.utils.MobilePlatform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is processed by {@link } class.
 * <p>
 * Use this annotation if you want to skip a test based plaform.
 *
 * @author saikrisv
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public @interface SkipIf {
    MobilePlatform platform();
}
