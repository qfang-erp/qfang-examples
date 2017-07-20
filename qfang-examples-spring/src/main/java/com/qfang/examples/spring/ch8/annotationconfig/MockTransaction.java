package com.qfang.examples.spring.ch8.annotationconfig;

import java.lang.annotation.*;

/**
 * Created by walle on 2017/4/30.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MockTransaction {

    String name() default "";

}
