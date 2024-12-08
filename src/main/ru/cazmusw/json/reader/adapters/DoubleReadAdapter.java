package ru.cazmusw.json.reader.adapters;

import ru.cazmusw.json.reader.ISectionReadAdapter;

public class DoubleReadAdapter implements ISectionReadAdapter {
    @Override
    public Object startAdapt(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public boolean canAdapt(String value) {
        return value.matches("-?\\d+.?\\d*");
    }
}
