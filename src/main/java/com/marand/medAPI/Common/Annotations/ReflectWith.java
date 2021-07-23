package com.marand.medAPI.Common.Annotations;

import com.marand.medAPI.Common.Services.UpdaterService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReflectWith {
  Class<? extends UpdaterService> value() default UpdaterService.class;
}
