package ru.cazmusw.json.writer;

import ru.cazmusw.json.utils.JsonToken;
import ru.cazmusw.json.utils.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

public class JsonWriter {


    private final File file;

    public JsonWriter(File file) {
        this.file = file;
    }

    public void saveTextToFile(JsonObject object) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));

            writer.write(this.writeSection(object, 0));

            writer.close();

        } catch (Exception e) {
            System.out.println("Ошибка! Не получилось записать файл!");
        }

    }

    private String getWhiteSpaceString(int count) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            result.append(JsonToken.WHITE_SPACE);
        }

        return result.toString();
    }

    public void writeData(StringBuilder builder, Object object, int amount) {
        if (object instanceof JsonObject) {

            builder.append(this.writeSection((JsonObject) object, amount + 2));

        } else if (object instanceof List) {

            builder.append(writeArray((List<?>) object, amount + 2));

        } else if (object instanceof String) {

            builder.append(JsonToken.STRING_STORAGE);
            builder.append(object);
            builder.append(JsonToken.STRING_STORAGE);

        } else {
            builder.append(object);
        }
    }


    public String writeArray(List<?> list, int amount) {
        StringBuilder result = new StringBuilder();

        result.append(JsonToken.ARRAY_START);
        result.append(JsonToken.NEW_LINE);

        int index = 0;

        for (Object value : list) {

            boolean isNeedSeparator = index != list.size() - 1;

            result.append(this.getWhiteSpaceString(amount + 2));

            this.writeData(result, value, amount);

            if (isNeedSeparator) {
                result.append(JsonToken.SEPARATOR);
            }

            result.append(JsonToken.NEW_LINE);

            index++;
        }

        result.append(this.getWhiteSpaceString(amount));
        result.append(JsonToken.ARRAY_END);

        return result.toString();
    }

    public String writeSection(JsonObject jsonObject, int amount) {
        StringBuilder result = new StringBuilder();

        result.append(JsonToken.SECTION_START);
        result.append(JsonToken.NEW_LINE);

        int index = 0;

        HashMap<String, Object> data = jsonObject.getData();

        for (String str : data.keySet()) {

            Object value = data.get(str);
            boolean isNeedSeparator = index != data.size() - 1;

            result.append(this.getWhiteSpaceString(amount + 2));
            result.append(JsonToken.STRING_STORAGE);
            result.append(str);
            result.append(JsonToken.STRING_STORAGE);
            result.append(JsonToken.VALUE_SEPARATOR);
            result.append(this.getWhiteSpaceString(1));

            this.writeData(result, value, amount);

            if (isNeedSeparator) {
                result.append(JsonToken.SEPARATOR);
            }

            result.append(JsonToken.NEW_LINE);

            index++;
        }

        result.append(this.getWhiteSpaceString(amount));
        result.append(JsonToken.SECTION_END);

        return result.toString();

    }


}
