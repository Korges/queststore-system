package controller.Admin;

import DAO.WebTemplate;
import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.AdminController;

import java.io.*;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateMentor implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = WebTemplate.getSiteContent("templates/admin/create-mentor.twig");
        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String,String> inputs = parseFormData(formData);
            try{
                AdminController adminController = new AdminController();
                String firstName = inputs.get("first-name");
                String lastName = inputs.get("last-name");
                String email = inputs.get("email");
                String password = inputs.get("password");
                adminController.createMentor(firstName,lastName,password,email);

            }catch (SQLException e){

            }catch (NoSuchAlgorithmException e){

            }



        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String,String> parseFormData(String formData) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
