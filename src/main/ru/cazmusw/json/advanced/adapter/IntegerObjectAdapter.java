package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class IntegerObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Integer.parseInt(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class);
    }
}
