package com.whippyteam.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandInfo {

    String[] name();

    String description() default "";

    int min() default 0;

    int max() default 127;

    String usage() default "";

    boolean userOnly() default false;

    String[] permission() default "";

    String completer() default "";

}
