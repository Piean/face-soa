package com.mogu.demo.utils;

import java.util.Collection;

public class CollectionUtil {
    public static boolean isEmpty(final Collection collection) {
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection collection) {
        return !isEmpty(collection);
    }
}
