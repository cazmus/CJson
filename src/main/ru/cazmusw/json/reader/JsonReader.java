package ru.cazmusw.json.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class JsonReader {

    private final File file;

    public JsonReader(File file) {
        this.file = file;
    }

    public String getTextFromFile() {
        String json = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json = json.concat(line);
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("Ошибка! Не получилось прочитать файл!");
        }

        return json;
    }

}
