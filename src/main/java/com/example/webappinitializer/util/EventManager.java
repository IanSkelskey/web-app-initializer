package com.example.webappinitializer.util;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * EventManager
 * <p>
 *     This class is used to manage events and listeners.
 *     It is used to publish events and subscribe to events.
 * </p>
 */
public class EventManager {
    /**
     * A HashMap of event names and listeners.
     */
    private static final HashMap<String, Consumer<Object>> listeners = new HashMap<>();

    /**
     * Subscribe to an event.
     * @param event The event name.
     * @param listener The listener.
     */
    public static void subscribe(String event, Consumer<Object> listener) {
        listeners.put(event, listener);
    }

    /**
     * Publish an event.
     * @param event The event name.
     * @param message The message to send to the listener.
     */
    public static void publish(String event, Object message) {
        if (listeners.containsKey(event)) {
            listeners.get(event).accept(message);
        }
    }
}

