package br.ufrrj.game.engine.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.engine.events.Cancellable;
import br.ufrrj.game.engine.events.Event;
import br.ufrrj.game.engine.events.EventHandler;
import br.ufrrj.game.engine.events.EventListener;
import br.ufrrj.game.engine.events.EventPriority;
import br.ufrrj.game.engine.events.PreEventHandler;
import br.ufrrj.game.engine.events.RegisteredEvent;
import lombok.Getter;

@Getter
public class EventManager {

    private Map<EventPriority, List<RegisteredEvent>> listeners;

    public EventManager() {
        this.listeners = new ConcurrentHashMap<>();
    }

    public <T> boolean isEventListener(T listener) {
        return listener.getClass().isAnnotationPresent(EventListener.class);
    }

    public <T> T registerEvent(T listener) {
        if (!isEventListener(listener)) throw new IllegalArgumentException(
                "The class " + listener.getClass().getName() + " does not have the EventListener annotation");

        var methods = listener.getClass().getMethods();

        for (Method method : methods) {
            EventHandler handler = method.getAnnotation(EventHandler.class);

            if (handler == null) continue;

            if (method.getParameterCount() != 1) throw new IllegalArgumentException("The method " + method.getName()
                    + " in class " + listener.getClass().getName()
                    + " does not have a valid parameter count. Expected: 1 Found: " + method.getParameterCount());

            if (!Event.class.isAssignableFrom(method.getParameterTypes()[0]))
                throw new IllegalArgumentException("The method " + method.getName() + " in class "
                        + listener.getClass().getName() + " does not have a valid parameter. Expected: "
                        + Event.class.getName() + " Found: " + method.getParameterTypes()[0].getName());

            // get pre event method with PreEvent<T extends Event>

            List<PreEvent> preEventMethods = new ArrayList<>();

            Stream.of(methods)
                    .filter(m -> m.getAnnotation(PreEventHandler.class) != null && m.getParameterCount() == 1
                            && method.getParameterTypes()[0].isAssignableFrom(m.getParameterTypes()[0])
                            && m.getReturnType().isAssignableFrom(boolean.class))
                    .forEach(m -> {
                        preEventMethods.add((event) -> {
                            try {
                                m.setAccessible(true);
                                var bool = (boolean) m.invoke(listener, event);

                                return !bool;
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                e.printStackTrace();
                            }

                            return true;
                        });
                    });

            Class<? extends Event> eventClass = method.getParameterTypes()[0].asSubclass(Event.class);
            method.setAccessible(true);

            registerEvent(listener, eventClass, method.getName(), handler, t -> {
                if (preEventMethods.size() > 0) {
                    if (preEventMethods.stream().anyMatch(preEvent -> preEvent.apply(t))) return;
                }

                if (t instanceof Cancellable) {
                    Cancellable cancellable = (Cancellable) t;
                    if (cancellable.isCancelled() && handler.ignoreCancelled()) return;
                }

                try {
                    long startTime = System.currentTimeMillis();
                    method.invoke(listener, t);
                    long duration = System.currentTimeMillis() - startTime;

                    if (duration >= 50) {
                        CommonGame.getInstance().log(Level.WARNING,
                                "Event " + t.getClass().getSimpleName() + " took " + duration + "ms to process");
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }

        return listener;
    }

    private <T> void registerEvent(T listener, Class<? extends Event> eventClass, String methodName,
            EventHandler eventHandler, Consumer<Event> consumer) {
        listeners.computeIfAbsent(eventHandler.priority(), v -> new ArrayList<>())
                .add(new RegisteredEvent(listener.hashCode(), methodName, eventHandler, eventClass, consumer));
        CommonGame.getInstance().log(Level.INFO,
                "Registering event " + eventClass.getSimpleName() + " (" + methodName + " - " + listener.hashCode()
                        + ") of class " + listener.getClass().getSimpleName() + " with priority "
                        + eventHandler.priority());
    }

    public <T> void unregisterEvent(T listener) {
        if (!isEventListener(listener)) throw new IllegalArgumentException(
                "The class " + listener.getClass().getName() + " does not have the EventListener annotation");

        listeners.values().forEach(list -> list.removeIf(event -> event.getHashCode() == listener.hashCode()));
        CommonGame.getInstance().log(Level.INFO, "Unregistering events into class "
                + listener.getClass().getSimpleName() + " (" + listener.hashCode() + ")");
    }

    public void unregisterEvent(RegisteredEvent event) {
        listeners.get(event.getHandler().priority()).remove(event);
    }

    public <T extends Event> T callEvent(T event) {
        for (EventPriority priority : EventPriority.values()) {
            if (!listeners.containsKey(priority)) continue;

            ImmutableList.copyOf(listeners.get(priority)).stream()
                    .filter(registeredEvent -> event.getClass().isAssignableFrom(registeredEvent.getEventClass()))
                    .forEach(registeredEvent -> registeredEvent.getConsumer().accept(event));
        }

        return event;
    }

    interface PreEvent {

        boolean apply(Event event);

    }
}
