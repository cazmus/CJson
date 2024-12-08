package ru.cazmusw.json;


import ru.cazmusw.json.advanced.ObjectReader;
import ru.cazmusw.json.reader.JsonParser;
import ru.cazmusw.json.reader.JsonReader;
import ru.cazmusw.json.utils.JsonObject;
import ru.cazmusw.json.writer.JsonWriter;

import java.io.File;

public class JsonFile {

    private final File file;

    public JsonFile(File file) {
        this.file = file;
    }

    private void checkFile() {
        if (this.file.exists() && this.file.isFile()) return;

        try {
            boolean isCreated = this.file.createNewFile();

            if(!isCreated) {
                System.err.println("Неизвестная ошибка! Не получилось создать файл " + this.file.getAbsolutePath());
            }

        } catch (Exception e) {
            System.out.println("Ошибка! Не получилось создать файл!");
        }
    }


    @Deprecated
    public void readObject(Object object) {

        JsonObject jsonObject = this.readAsJsonObject();

        if (jsonObject == null) {
            System.out.println("Ошибка! Джсон файл пуст!");
            return;
        }

        ObjectReader objectReader = new ObjectReader();

        try {
            objectReader.parseObjectType(object, jsonObject);
        } catch (Exception e) {
            System.out.println("Ошибка! Не удалось прочитать Json-файл!");
            e.printStackTrace();
        }


    }

    public void saveFile(JsonObject jsonObject) {
        JsonWriter writer = new JsonWriter(this.file);

        if (jsonObject == null) {
            System.out.println("Ошибка! Объект не существует!");
            return;
        }

        writer.saveTextToFile(jsonObject);

    }

    public JsonObject readAsJsonObject() {

        this.checkFile();
        JsonReader jsonReader = new JsonReader(this.file);

        String json = jsonReader.getTextFromFile();

        if (json.isEmpty()) return null;

        if (!json.startsWith("{") || !json.endsWith("}")) {
            System.out.println("Ошибка! Файл не является json-файлом!");
            return null;
        }

        JsonParser parser = new JsonParser();

        JsonObject jsonObject = new JsonObject();

        parser.parseSection(json, jsonObject);

        return jsonObject;
    }

}
