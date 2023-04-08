package utilities;

import org.reflections.Reflections;
import org.testng.ITestClass;
import java.util.HashSet;
import java.util.Set;

public class Classloader {

    private Classloader(){

    }

    public static Set<Class <? extends ITestClass>> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return new HashSet<>(reflections.getSubTypesOf(ITestClass.class));
    }

}
