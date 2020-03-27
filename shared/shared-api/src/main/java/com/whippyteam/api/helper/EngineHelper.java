package com.whippyteam.api.helper;

import com.whippyteam.api.ToolsPlugin;

import java.lang.reflect.Constructor;
import java.util.Map;

public class EngineHelper {

    @SuppressWarnings("unchecked")
    public static <T> T initiateObject(ToolsPlugin plugin, Class<?> interfaceClass, String path, Map<Class<?>, Object> paramObjects) {
        String engineType = plugin.getEngineType().name();
        try {
            Class<?> clazz = Class.forName("com.whippyteam." + engineType.toLowerCase() + ".tools." + path);
            if (clazz == null) {
                return null;
            }

            if (interfaceClass.isAssignableFrom(clazz)) {
                Constructor<?> constructor;

                if (paramObjects == null) {
                    constructor = clazz.getConstructor();
                    return (T) constructor.newInstance();
                } else {
                    constructor = clazz.getConstructor(paramObjects.keySet().toArray(new Class<?>[0]));
                    return (T) constructor.newInstance(paramObjects.values());
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
