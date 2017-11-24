package controller.Student;

import DAO.ConnectDB;
import DAO.FundraiseDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.StudentController;
import models.Fundraise;

import java.io.*;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class JoinFundraise implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                response = WebTemplate.getSiteContent("templates/student/join-fundraise.twig");
            }

            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);

                try {

                    String sessionID = StudentController.getSession();
                    String fundraiseID = inputs.get("id");
                    String userID = getUserID(sessionID);
                    joinFundraise(fundraiseID, userID);

                } catch (SQLException e) {

                }
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

    }


    private String getUserID(String sessionIDFull) throws SQLException {
        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        String sql = String.format("SELECT user_id FROM sessions WHERE session_id LIKE '%s'", sessionIDFull);
        ResultSet userID = connectDB.getResult(sql);

        return userID.toString();

    }



    private void joinFundraise(String fundraiseID, String userID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.joinFundraise(fundraiseID, userID);



    }



}
