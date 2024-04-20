package br.ufrrj.game.engine.events;

import java.util.function.Consumer;

import lombok.Getter;

@Getter
public class RegisteredEvent {

    private int hashCode;

    private String name;
    private EventHandler handler;
    private Class<? extends Event> eventClass;
    private Consumer<Event> consumer;

    public RegisteredEvent(int hashCode, String methodName, EventHandler handler, Class<? extends Event> eventClass,
            Consumer<Event> consumer) {
        this.hashCode = hashCode;
        this.name = methodName;
        this.handler = handler;
        this.eventClass = eventClass;
        this.consumer = consumer;
    }

}
