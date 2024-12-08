package ru.cazmusw.json.reader;

import ru.cazmusw.json.reader.adapters.*;

public enum SectionReadAdapters {

    STRING(new StringReadAdapter()),
    NULL(new NullReadAdapter()),
    BOOLEAN(new BooleanReadAdapter()),
    INTEGER(new IntegerReadAdapter()),
    DOUBLE(new DoubleReadAdapter());

    public final ISectionReadAdapter sectionAdapter;

    SectionReadAdapters(ISectionReadAdapter sectionAdapter) {
        this.sectionAdapter = sectionAdapter;
    }

}
