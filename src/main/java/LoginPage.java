import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class LoginPage implements HttpHandler{

    public void handle(HttpExchange httpExchange) throws IOException {
        WebTemplateDao webTemplateDao = new WebTemplateDao();
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = webTemplateDao.getSiteTemplate("static/login-page.html");

        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
