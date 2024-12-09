package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class BooleanObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Boolean.parseBoolean(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class);
    }
}
