package com.example.demo.adapter.in.cache;

import com.example.demo.config.PoliticianCacheAop;
import com.example.demo.domain.entities.Politicians;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

/**
 * Annotation to distinguish methods that return {@link Politicians} wrapped in an
 * {@link Optional}. This annotation is used as the pointcut for caches that operate using
 * AOP e.g., {@link PoliticianCacheAop}.
 * <p/>
 * Such methods operate just like the database, if the {@link ProceedingJoinPoint} return value
 * is empty, the method will return an empty {@link Optional} as well and will not throw any
 * exceptions.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalPolCache {
}
