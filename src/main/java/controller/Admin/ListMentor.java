package controller.Admin;

import DAO.MentorDAO;
import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListMentor implements HttpHandler{

    public void handle(HttpExchange httpExchange) throws IOException {
            WebTemplateDao webTemplateDao = new WebTemplateDao();
            String response = "";
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) { ;

            }

            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }


}
