package fr.istic.sit.domain;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Fonction utile pour faire l'intersection de deux listes
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
}
