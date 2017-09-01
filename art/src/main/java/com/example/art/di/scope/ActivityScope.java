package com.example.art.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
