package com.example.webappinitializer.util;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager {
    private static final HashMap<String, Consumer<Object>> listeners = new HashMap<>();

    public static void subscribe(String event, Consumer<Object> listener) {
        listeners.put(event, listener);
    }

    public static void publish(String event, Object message) {
        if (listeners.containsKey(event)) {
            listeners.get(event).accept(message);
        }
    }
}

