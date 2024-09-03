package org.example.core.utilities;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.List;

public class ObjectHelper {
    private static final Cloner cloner = new Cloner();

    public static <T> T clone(T object) {
        return cloner.deepClone(object);
    }

    /**
     * cloneList.
     * @param objects List of objects
     * @return Cloned list.
     */
    @SuppressWarnings("checkstyle:all")
    public static <T> List<T> cloneList(List<T> objects) {
        List<T> list = new ArrayList<>();
        for (T o : objects) {
            list.add(cloner.deepClone(o));
        }
        return list;
    }
}
