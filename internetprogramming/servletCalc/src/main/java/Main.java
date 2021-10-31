import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servletCalc", urlPatterns = "/*")
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        String[] parsed_uri = uri.split("/");
        String command = parsed_uri[0];
        Integer firstNumber = Integer.valueOf(parsed_uri[1]);
        Integer secondNumber = Integer.valueOf(parsed_uri[2]);
        resp.getWriter().println();
    }
}