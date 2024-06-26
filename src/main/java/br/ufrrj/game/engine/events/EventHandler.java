package br.ufrrj.game.engine.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

    public EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;

}
