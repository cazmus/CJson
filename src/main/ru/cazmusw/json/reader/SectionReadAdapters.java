package ru.cazmusw.json.reader;

import ru.cazmusw.json.reader.adapters.*;

import java.util.ArrayList;
import java.util.List;

public enum SectionReadAdapters {

    STRING(new StringReadAdapter()),
    NULL(new NullReadAdapter()),
    BOOLEAN(new BooleanReadAdapter()),
    INTEGER(new IntegerReadAdapter()),
    DOUBLE(new DoubleReadAdapter());

    private static final List<ISectionReadAdapter> usersAdapters = new ArrayList<>();
    public final ISectionReadAdapter sectionAdapter;

    SectionReadAdapters(ISectionReadAdapter sectionAdapter) {
        this.sectionAdapter = sectionAdapter;
    }

    public static List<ISectionReadAdapter> getAllAdapters() {

        List<ISectionReadAdapter> result = new ArrayList<>(usersAdapters);

        for (SectionReadAdapters adapter : values()) {
            result.add(adapter.sectionAdapter);
        }

        return result;
    }

    public static void registerAdapter(ISectionReadAdapter sectionAdapter) {
        usersAdapters.add(sectionAdapter);
    }

}
