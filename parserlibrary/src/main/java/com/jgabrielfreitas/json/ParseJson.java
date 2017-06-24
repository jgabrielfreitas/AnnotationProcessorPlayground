package com.jgabrielfreitas.json;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by JGabrielFreitas on 24/06/17.
 */

@Target(TYPE) // on class level
@Retention(SOURCE) // not needed at runtime
public @interface ParseJson {
}
