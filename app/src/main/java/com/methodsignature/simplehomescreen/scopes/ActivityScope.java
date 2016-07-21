package com.methodsignature.simplehomescreen.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by randallmitchell on 7/20/16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {}
