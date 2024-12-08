package ru.cazmusw.json.advanced;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSection {

    String comment() default "";

    String title();

}
