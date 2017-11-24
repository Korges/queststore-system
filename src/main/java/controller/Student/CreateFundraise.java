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

import static controller.helpers.ParseForm.parseFormData;

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

                Fundraise fundraise = createFundraise(inputs);
                addNewFundraiseToDatabase(fundraise);

            }catch (SQLException e){

            }
            response =  WebTemplate.getSiteContent("templates/success.twig");

        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Fundraise createFundraise(Map<String, String> inputs) {

        Integer ID = Integer.parseInt(inputs.get("id"));
        String title = inputs.get("title");

        Fundraise fundraise = new Fundraise(ID, title);

        return fundraise;
    }

    public void addNewFundraiseToDatabase(Fundraise fundraise) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.createNewFundraise(fundraise);
    }




}
