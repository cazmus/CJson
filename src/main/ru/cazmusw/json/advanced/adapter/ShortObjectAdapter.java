package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class ShortObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Short.parseShort(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(short.class) || clazz.isAssignableFrom(Short.class);
    }
}
