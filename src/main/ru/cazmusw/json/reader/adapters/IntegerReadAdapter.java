package ru.cazmusw.json.reader.adapters;

import ru.cazmusw.json.reader.ISectionReadAdapter;

public class IntegerReadAdapter implements ISectionReadAdapter {
    @Override
    public Object startAdapt(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public boolean canAdapt(String value) {
        return value.matches("-?\\d+");
    }
}
