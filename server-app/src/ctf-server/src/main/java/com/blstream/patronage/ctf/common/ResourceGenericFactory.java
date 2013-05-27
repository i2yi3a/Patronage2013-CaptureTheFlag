package com.blstream.patronage.ctf.common;

/**
 * User: mkr
 * Date: 4/22/13
 */
public class ResourceGenericFactory {
    public static <T> T createInstance(Class<T> _class) throws RuntimeException {
        try {
            return _class.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Object instance of class: %s cannot by created.", _class));
        }
    }
}
