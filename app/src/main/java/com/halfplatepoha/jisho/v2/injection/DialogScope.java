package com.halfplatepoha.jisho.v2.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by surjo on 21/12/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DialogScope {}
