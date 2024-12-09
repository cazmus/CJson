package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class LongObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Long.parseLong(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class);
    }
}