package ru.cazmusw.json.reader.adapters;

import ru.cazmusw.json.reader.ISectionReadAdapter;

public class StringReadAdapter implements ISectionReadAdapter {
    @Override
    public Object startAdapt(String value) {
        return value.substring(1, value.length() - 1);
    }

    @Override
    public boolean canAdapt(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }
}
