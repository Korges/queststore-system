package controller.Student;

import DAO.FundraiseDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.AdminController;
import controller.StudentController;
import models.Fundraise;

import java.io.*;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateFundraise implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = WebTemplate.getSiteContent("templates/student/create-fundraise.twig");
        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String,String> inputs = parseFormData(formData);
            try{

                FundraiseDAO fundraiseDAO = new FundraiseDAO();

                Integer ID = Integer.parseInt(inputs.get("id"));
                String title = inputs.get("title");

                Fundraise fundraise = new Fundraise(ID, title);
                fundraiseDAO.add(fundraise);
//               fundraiseDAO.join(fundraise, user);



            }catch (SQLException e){

            }
            response =  WebTemplate.getSiteContent("templates/success.twig");

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
