package com.annotation.values;

import java.lang.annotation.*;

/**
 * This annotation is processed by {@link } class.
 *
 * Use this annotation if you want to skip a test based plaform.
 *
 * @author saikrisv
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipIf{
     String platform();
}