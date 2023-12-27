package com.example.webappinitializer.util;

public enum EventType {
    MODULE_SELECTED,
    MODULE_DESELECTED,
    PROJECT_CONFIGURATION_UPDATED,
    CREATE_APP_BUTTON_CLICKED,
    SHORT_NAME_CHANGED,
    FULL_NAME_CHANGED,
    DESCRIPTION_CHANGED,
    DIRECTORY_NAME_CHANGED,
    PRETTIER_CONFIG_CHANGED, GET_STARTED_BUTTON_CLICKED, HOME_BUTTON_CLICKED, BACK_BUTTON_CLICKED, NEXT_BUTTON_CLICKED;

    @Override
    public String toString() {
        String name = this.name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}

