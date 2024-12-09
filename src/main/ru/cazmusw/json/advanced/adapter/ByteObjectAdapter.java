package ru.cazmusw.json.advanced.adapter;

import ru.cazmusw.json.advanced.IObjectAdapter;

public class ByteObjectAdapter implements IObjectAdapter {
    @Override
    public Object startAdapt(Object value) {
        return Byte.parseByte(value.toString().trim());
    }

    @Override
    public boolean canAdapt(Class<?> clazz) {
        return clazz.isAssignableFrom(byte.class) || clazz.isAssignableFrom(Byte.class);
    }
}
