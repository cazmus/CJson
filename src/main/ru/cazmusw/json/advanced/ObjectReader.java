package ru.cazmusw.json.advanced;

import ru.cazmusw.json.utils.JsonObject;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectReader {

    public void parseObjectType(Object object, JsonObject jsonObject) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<Class<?>> classesHierarchy = this.getClassesHierarchy(object.getClass());
        List<Field> fields = this.getFieldHierarchy(classesHierarchy);

        for (Field field : fields) {
            this.trySetValue(object, field, jsonObject);
        }
    }

    public void trySetValue(Object object, Field field, JsonObject jsonObject) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String title = field.getAnnotation(JsonSection.class).title();

        IObjectAdapter adapter = ObjectAdapters.getAllAdapters()
                .stream()
                .filter(objectAdapter -> objectAdapter.canAdapt(field.getType()))
                .findFirst()
                .orElse(null);

        if (adapter != null) {
            field.set(object, adapter.startAdapt(jsonObject.getData().get(title)));
        } else if (field.getType().isArray()) {
            field.set(object, this.parseArray((List<?>) jsonObject.getData().get(title), field.getType().getComponentType()));
        } else if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(ArrayList.class)) {
            field.set(object, this.parseArrayList((List<?>) jsonObject.getData().get(title), field.getGenericType()));
        } else if (field.getType().isAssignableFrom(Map.class) || field.getType().isAssignableFrom(HashMap.class)) {

        } else {
            Object result = field.getType().newInstance();
            this.parseObjectType(result, (JsonObject) jsonObject.getData().get(title));
            field.set(object, result);
        }
    }

    public Object parseArray(List<?> jsonValues, Class<?> type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Object resultArray = Array.newInstance(type, jsonValues.size());

        for (int i = 0; i < jsonValues.size(); i++) {
            Object jsonValue = jsonValues.get(i);

            if (type.isArray()) {
                Array.set(resultArray, i, this.parseArray((List<?>) jsonValue, type.getComponentType()));
            } else {

                IObjectAdapter adapter = ObjectAdapters.getAllAdapters()
                        .stream()
                        .filter(objectAdapter -> objectAdapter.canAdapt(type))
                        .findFirst()
                        .orElse(null);

                if(adapter != null) {
                    Array.set(resultArray, i, adapter.startAdapt(jsonValue));
                } else {
                    Object elementValue = type.newInstance();
                    this.parseObjectType(elementValue, (JsonObject) jsonValue);
                    Array.set(resultArray, i, elementValue);
                }
            }

        }

        return resultArray;
    }

    public ArrayList<Object> parseArrayList(List<?> jsonValues, Type type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        ArrayList<Object> resultArray = new ArrayList<>();

        for (Object jsonValue : jsonValues) {
            if (type instanceof ParameterizedTypeImpl) {
                ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
                Type[] paramArguments = parameterizedType.getActualTypeArguments();

                Type argType = paramArguments[0];

                if (argType instanceof ParameterizedTypeImpl) {
                    resultArray.add(this.parseArrayList((List<?>) jsonValue, argType));
                } else {
                    Class<?> typeClass = Class.forName(paramArguments[0].getTypeName());
                    IObjectAdapter adapter = ObjectAdapters.getAllAdapters()
                            .stream()
                            .filter(objectAdapter -> objectAdapter.canAdapt(typeClass))
                            .findFirst()
                            .orElse(null);

                    if(adapter != null) {
                        resultArray.add(adapter.startAdapt(jsonValue));
                    } else {
                        Object elementValue = typeClass.newInstance();
                        this.parseObjectType(elementValue, (JsonObject) jsonValue);
                        resultArray.add(elementValue);
                    }
                }
            } else {
                System.out.println("Ошибка! Неопознанный тип объекта! " + type.getTypeName());
            }

        }

        return resultArray;
    }


    public List<Class<?>> getClassesHierarchy(Class<?> mainClazz) {

        List<Class<?>> result = new ArrayList<>();
        Class<?> clazz = mainClazz;

        while (clazz != null && !clazz.equals(Object.class)) {
            result.add(clazz);
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    public List<Field> getFieldHierarchy(List<Class<?>> mainClazz) {

        List<Field> result = new ArrayList<>();

        for (Class<?> clazz : mainClazz) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(JsonSection.class) != null) {
                    field.setAccessible(true);
                    result.add(field);
                }
            }
        }

        return result;
    }


}
