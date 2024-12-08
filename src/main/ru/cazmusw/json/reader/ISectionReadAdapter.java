package ru.cazmusw.json.reader;

public interface ISectionReadAdapter {

    Object startAdapt(String value);

    boolean canAdapt(String value);
}
