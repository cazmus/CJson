package ru.cazmusw.json.advanced;

import java.util.ArrayList;
import java.util.List;

public enum ObjectAdapters {
    ;

    private static final List<IObjectAdapter> usersAdapters = new ArrayList<>();
    public final IObjectAdapter objectAdapter;

    ObjectAdapters(IObjectAdapter objectAdapter) {
        this.objectAdapter = objectAdapter;
    }

    public static List<IObjectAdapter> getAllAdapters() {

        List<IObjectAdapter> result = new ArrayList<>(usersAdapters);

        for (ObjectAdapters adapter : values()) {
            result.add(adapter.objectAdapter);
        }

        return result;
    }

    public static void registerAdapter(IObjectAdapter objectAdapter) {
        usersAdapters.add(objectAdapter);
    }
}
