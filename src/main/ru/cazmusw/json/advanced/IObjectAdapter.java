package ru.cazmusw.json.advanced;

public interface IObjectAdapter {

    Object startAdapt(Object value);

    boolean canAdapt(Class<?> clazz);
}
