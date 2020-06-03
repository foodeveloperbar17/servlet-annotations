import annotations.MethodAnnotation;
import annotations.ServletAnnotation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ServletAnnotation(uri = "/hallo")
public class HelloServlet extends HttpServlet {


    @MethodAnnotation(method = "GET")
    public void greet(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
