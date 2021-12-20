package top.pofengsystem.core.util;

public class ClassUtil {
    public static boolean containParentClass(Class<?> source, Class<?> parentClass) {
        while (source!=null) {
            final Class<?> superclass = source.getSuperclass();
            if (superclass==parentClass) {
                return true;
            } else {
                source = superclass;
            }
        }
        return false;
    }
}
