package ru.cazmusw.json.reader;

import ru.cazmusw.json.utils.JsonConstants;
import ru.cazmusw.json.utils.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonParser {

    private int indexOf(String input, char character) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == character && (i == 0 || input.charAt(i - 1) != JsonConstants.IGNORE_RULES)) {
                return i;
            }
        }
        return -1;
    }

    private int findEndCharIndex(String json, char startChar, char endChar) {

        int jsonSectionValue = 0;
        int startIndex = this.indexOf(json, startChar);

        for (int i = startIndex + 1; i < json.length(); i++) {
            if (json.charAt(i) == JsonConstants.STRING_STORAGE && (i == 0 || json.charAt(i - 1) != JsonConstants.IGNORE_RULES)) {
                i = this.indexOf(json.substring(i + 1), JsonConstants.STRING_STORAGE) + i + 1;
            } else if (json.charAt(i) == startChar) {
                jsonSectionValue++;
            } else if (json.charAt(i) == endChar) {
                if (jsonSectionValue != 0) {
                    jsonSectionValue--;
                } else {
                    return i;
                }
            }
        }

        return -1;
    }

    private int findEndSectionIndex(String json) {

        for (int i = 0; i < json.length(); i++) {
            char totalCharacter = json.charAt(i);
            if (totalCharacter == JsonConstants.SECTION_START) {
                i = findEndCharIndex(json, JsonConstants.SECTION_START, JsonConstants.SECTION_END);
            } else if (totalCharacter == JsonConstants.ARRAY_START) {
                i = findEndCharIndex(json, JsonConstants.ARRAY_START, JsonConstants.ARRAY_END);
            } else if (totalCharacter == JsonConstants.STRING_STORAGE && (i == 0 || json.charAt(i - 1) != JsonConstants.IGNORE_RULES)) {
                i = this.indexOf(json.substring(i + 1), JsonConstants.STRING_STORAGE) + i + 1;
            } else if (totalCharacter == JsonConstants.SEPARATOR) {
                return i;
            }
        }

        return json.length() - 1;

    }

    private List<String> parseJsonHierarhyList(String json) {
        List<String> result = new ArrayList<>();

        json = json.trim().substring(1, json.length() - 1);

        while (!json.isEmpty()) {
            json = json.trim();
            int sectionEnd = this.findEndSectionIndex(json);

            String parsedSectionValue = json.substring(0, sectionEnd + 1);

            if (parsedSectionValue.endsWith(String.valueOf(JsonConstants.SEPARATOR))) {
                parsedSectionValue = parsedSectionValue.substring(0, parsedSectionValue.length() - 1);
            }

            result.add(parsedSectionValue.trim());

            json = json.substring(sectionEnd + 1);

        }

        return result;
    }

    public void parseSection(String json, JsonObject jsonObject) {

        List<String> data = this.parseJsonHierarhyList(json);

        for (String section : data) {

            int startSectionNameIndex = this.indexOf(section, JsonConstants.STRING_STORAGE);
            int endSectionNameIndex = this.indexOf(section.substring(startSectionNameIndex + 1), JsonConstants.STRING_STORAGE) + startSectionNameIndex + 1;

            String sectionName = section.substring(startSectionNameIndex + 1, endSectionNameIndex);
            String sectionValue = section.substring(this.indexOf(section, JsonConstants.KEY_VALUE_SEPARATOR) + 1).trim();

            if (sectionValue.startsWith(String.valueOf(JsonConstants.SECTION_START))) {
                JsonObject obj = new JsonObject();
                this.parseSection(sectionValue, obj);
                jsonObject.getData().put(sectionName, obj);
            } else if (sectionValue.startsWith(String.valueOf(JsonConstants.ARRAY_START))) {
                List<Object> arrayList = new ArrayList<>();
                this.parseArray(sectionValue, arrayList);
                jsonObject.getData().put(sectionName, arrayList);
            } else {
                ISectionReadAdapter adapter = Arrays.stream(SectionReadAdapters.values())
                        .map(sectionReadAdapters -> sectionReadAdapters.sectionAdapter)
                        .filter(iSectionAdapter -> iSectionAdapter.canAdapt(sectionValue))
                        .findFirst().orElse(null);


                if (adapter == null) {
                    System.out.println("Ошибка! Не удалось спарсить секцию: " + sectionValue);
                    continue;
                }

                jsonObject.getData().put(sectionName, adapter.startAdapt(sectionValue));
            }
        }
    }

    private void parseArray(String json, List<Object> list) {

        List<String> data = this.parseJsonHierarhyList(json);

        for (String sectionValue : data) {

            if (sectionValue.startsWith(String.valueOf(JsonConstants.SECTION_START))) {
                JsonObject obj = new JsonObject();
                this.parseSection(sectionValue, obj);
                list.add(obj);
            } else if (sectionValue.startsWith(String.valueOf(JsonConstants.ARRAY_START))) {
                List<Object> arrayList = new ArrayList<>();
                this.parseArray(sectionValue, arrayList);
                list.add(arrayList);
            } else {
                ISectionReadAdapter adapter = Arrays.stream(SectionReadAdapters.values())
                        .map(sectionReadAdapters -> sectionReadAdapters.sectionAdapter)
                        .filter(iSectionAdapter -> iSectionAdapter.canAdapt(sectionValue))
                        .findFirst().orElse(null);

                if (adapter == null) {
                    System.out.println("Ошибка! Не удалось спарсить секцию: " + sectionValue);
                    continue;
                }

                list.add(adapter.startAdapt(sectionValue));
            }

        }
    }

}
