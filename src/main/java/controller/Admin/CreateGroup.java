package controller.Admin;

import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.AdminController;

import java.io.IOException;
import java.io.OutputStream;

public class CreateGroup implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response =  WebTemplate.getSiteContent("templates/admin/create-group.twig");

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
