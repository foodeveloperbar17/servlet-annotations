import annotations.ServletAnnotation;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationsMap {

    private static AnnotationsMap annotationsMap;

    public static AnnotationsMap getInstance(){
        if(annotationsMap == null){
            synchronized (AnnotationsMap.class){
                if(annotationsMap == null){
                    annotationsMap = new AnnotationsMap();
                }
            }
        }
        return annotationsMap;
    }

    private Map<String, Class> hashMap = new HashMap<String, Class>();

    public AnnotationsMap(){
        Reflections reflections = new Reflections("");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ServletAnnotation.class);
        for(Class cls : classes){
            ServletAnnotation annotation = (ServletAnnotation) cls.getAnnotation(ServletAnnotation.class);
            hashMap.put(annotation.uri(), cls);
        }
    }

    public Class getServletClass(String uri){
        System.out.println(uri);
        String uriWithoutWar = uri.substring(uri.indexOf('/', 1));
        System.out.println(uriWithoutWar);
        return hashMap.get(uriWithoutWar);
    }

}
