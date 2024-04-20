package br.ufrrj.game.engine.events;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
