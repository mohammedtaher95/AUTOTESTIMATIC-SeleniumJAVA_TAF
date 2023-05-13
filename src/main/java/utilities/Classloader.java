package utilities;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import java.util.*;

public class Classloader {

    private Classloader(){

    }

    public static Set<Class<?>> findAllClasses(String packageName) {
        Reflections reflections = new Reflections(packageName,Scanners.SubTypes.filterResultsBy(s -> true));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

}
