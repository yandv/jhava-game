package br.ufrrj.game.engine.events;

import lombok.Getter;

@Getter
public abstract class Event {

    private String name;
    private boolean async;

    public Event(String name, boolean async) {
        this.name = name;
        this.async = false;
    }

    public Event(boolean async) {
        this.name = this.getClass().getSimpleName();
        this.async = async;
    }

    public Event() {
        this.name = this.getClass().getSimpleName();
        this.async = false;
    }

}
