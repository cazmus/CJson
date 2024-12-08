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


@Deprecated
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

        if (field.getType().isPrimitive() || field.getType().isAssignableFrom(String.class)) {
            field.set(object, this.getPrimaryTypeValue(field.getType(), jsonObject.getData().get(title)));
        } else if (field.getType().isArray()) {

            List<?> jsonValues = (List<?>) jsonObject.getData().get(title);

            Object resultArray = Array.newInstance(field.getType().getComponentType(), jsonValues.size());

            for (int i = 0; i < jsonValues.size(); i++) {
                Object jsonValue = jsonValues.get(i);

                Object elementValue = this.getPrimaryTypeValue(field.getType().getComponentType(), jsonValue);

                if (elementValue == null) {

                    elementValue = field.getType().getComponentType().newInstance();
                    this.parseObjectType(elementValue, (JsonObject) jsonValue);
                }
                Array.set(resultArray, i, elementValue);
            }
            field.set(object, resultArray);
        } else if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(ArrayList.class)) {
            List<?> jsonValues = (List<?>) jsonObject.getData().get(title);
            ArrayList<Object> resultArray = new ArrayList<>();

            for (Object jsonValue : jsonValues) {


                ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) field.getGenericType();
                Type[] paramArguments = parameterizedType.getActualTypeArguments();
                Class<?> typeClass = Class.forName(paramArguments[0].getTypeName());
                Object elementValue = this.getPrimaryTypeValue(typeClass, jsonValue);

                if (elementValue == null) {
                    elementValue = typeClass.newInstance();
                    this.parseObjectType(elementValue, (JsonObject) jsonValue);
                }

                resultArray.add(elementValue);
            }
            field.set(object, resultArray);

        } else if (field.getType().isAssignableFrom(Map.class) || field.getType().isAssignableFrom(HashMap.class)) {
            List<?> jsonValues = (List<?>) jsonObject.getData().get(title);
            HashMap<Object, Object> resultMap = new HashMap<>();

            for (Object jsonValue : jsonValues) {

                ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) field.getGenericType();
                Type[] paramArguments = parameterizedType.getActualTypeArguments();
                Class<?> typeClass = Class.forName(paramArguments[0].getTypeName());
                Class<?> valueClass = Class.forName(paramArguments[1].getTypeName());

                Object keyValue = this.getPrimaryTypeValue(typeClass, ((JsonObject) jsonValue).getData().get("key"));

                if (keyValue == null) {
                    keyValue = typeClass.newInstance();
                    this.parseObjectType(keyValue, (JsonObject) ((JsonObject) jsonValue).getData().get("key"));
                }

                Object elementValue = this.getPrimaryTypeValue(valueClass, ((JsonObject) jsonValue).getData().get("value"));

                if (elementValue == null) {
                    elementValue = valueClass.newInstance();
                    this.parseObjectType(elementValue, (JsonObject) ((JsonObject) jsonValue).getData().get("value"));
                }

                resultMap.put(keyValue, elementValue);
            }
            field.set(object, resultMap);
        } else {
            Object result = field.getType().newInstance();
            this.parseObjectType(result, (JsonObject) jsonObject.getData().get(title));
            field.set(object, result);
        }
    }

    public Object getPrimaryTypeValue(Class<?> clazz, Object needParseValue) {

        if (clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(double.class) || clazz.isAssignableFrom(Double.class)) {
            return Double.parseDouble(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class)) {
            return Boolean.parseBoolean(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class)) {
            return Long.parseLong(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class)) {
            return Float.parseFloat(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(short.class) || clazz.isAssignableFrom(Short.class)) {
            return Short.parseShort(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(byte.class) || clazz.isAssignableFrom(Byte.class)) {
            return Byte.parseByte(needParseValue.toString().trim());
        } else if (clazz.isAssignableFrom(char.class) || clazz.isAssignableFrom(Character.class)) {
            return needParseValue.toString().charAt(0);
        } else if (clazz.isAssignableFrom(String.class)) {
            return needParseValue.toString();
        }

        return null;
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
