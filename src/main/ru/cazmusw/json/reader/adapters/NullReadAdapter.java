package ru.cazmusw.json.reader.adapters;

import ru.cazmusw.json.reader.ISectionReadAdapter;

public class NullReadAdapter implements ISectionReadAdapter {
    @Override
    public Object startAdapt(String value) {
        return null;
    }

    @Override
    public boolean canAdapt(String value) {
        return value.equals("null");
    }
}
