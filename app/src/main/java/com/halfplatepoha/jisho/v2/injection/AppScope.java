package com.halfplatepoha.jisho.v2.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by surjo on 05/01/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {}
