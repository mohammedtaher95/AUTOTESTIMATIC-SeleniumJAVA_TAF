package utilities;

import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class Classloader {

    private Classloader() {

    }

    public static Set<Class<?>> findAllClasses(String packageName) {
        Reflections reflections = new Reflections(packageName,
                Scanners.SubTypes.filterResultsBy(s -> true));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }
}
