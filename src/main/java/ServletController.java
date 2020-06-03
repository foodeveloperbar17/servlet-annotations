import annotations.MethodAnnotation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServletController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        AnnotationsMap.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Class servletClass = AnnotationsMap.getInstance().getServletClass(req.getRequestURI());
        invokeDoGet(servletClass, req, resp, "GET");
    }

    private void invokeDoGet(Class servletClass, HttpServletRequest req, HttpServletResponse resp, String methodType){
        try {
            Method methodToInvoke = getMethodToInvoke(servletClass, methodType);
            Object servletObj = servletClass.newInstance();
            methodToInvoke.invoke(servletObj, req, resp);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Method getMethodToInvoke(Class servletClass, String methodType){
        Method[] methods = servletClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (int j = 0; j < annotations.length; j++) {
                Annotation annotation = annotations[j];
                if(annotation.annotationType().equals(MethodAnnotation.class)){
                    return method;
                }
            }
        }
        throw new RuntimeException("couldn't found method");
//        return null;
    }
}
