package com.halfplatepoha.jisho.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by surjo on 21/12/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DialogScope {}
