package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class CharObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return value.toString().charAt(0);
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(char.class) || clazz.isAssignableFrom(Character.class);
    }
}

