import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ServletController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        AnnotationsMap.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Class servletClass = AnnotationsMap.getInstance().getServletClass(req.getRequestURI());
        invokeDoGet(servletClass, req, resp);
    }

    private void invokeDoGet(Class servletClass, HttpServletRequest req, HttpServletResponse resp){
        try {
            Object servletObj = servletClass.newInstance();
            servletClass.getMethod("init").invoke(servletObj);
            servletClass.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class).invoke(servletObj, req, resp);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
