package ru.cazmusw.json.reader.adapters;

import ru.cazmusw.json.reader.ISectionReadAdapter;

public class BooleanReadAdapter implements ISectionReadAdapter {
    @Override
    public Object startAdapt(String value) {
        return value.equals("true");
    }

    @Override
    public boolean canAdapt(String value) {
        return value.equals("true") || value.endsWith("false");
    }
}
