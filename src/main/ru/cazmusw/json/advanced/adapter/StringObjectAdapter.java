package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class StringObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return value.toString();
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class);
    }
}
