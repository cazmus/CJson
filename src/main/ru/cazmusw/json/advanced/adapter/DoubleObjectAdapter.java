package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class DoubleObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Double.parseDouble(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(double.class) || clazz.isAssignableFrom(Double.class);
    }
}
