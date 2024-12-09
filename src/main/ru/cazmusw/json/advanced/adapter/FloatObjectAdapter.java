package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class FloatObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Float.parseFloat(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class);
    }
}
