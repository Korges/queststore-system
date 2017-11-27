package controller.Student.Fundraise;

import DAO.ConnectDB;
import DAO.FundraiseDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.StudentController;
import models.Fundraise;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class JoinFundraise implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {


                response = listAllFundraise();
            }



            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);


                String sessionID = StudentController.getSession();
                String fundraiseID = inputs.get("id");
                String userID = getUserID(sessionID);

                joinFundraise(fundraiseID, userID);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }




    private String getUserID(String sessionIDFull) throws SQLException {

        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        String sql = String.format("SELECT user_id FROM sessions WHERE session_id LIKE '%s'", sessionIDFull);
        ResultSet result = connectDB.getResult(sql);


        String userID = result.getString("user_id");



        return userID;

    }



    public void joinFundraise(String fundraiseID, String userID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.joinFundraise(fundraiseID, userID);

    }

    public String listAllFundraise() throws SQLException{

        ArrayList<Fundraise> fundraiseList = getFundraiseList();
        ArrayList<ArrayList<String>> data = createJtwigData(fundraiseList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/join-fundraise.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = "";
        model.with("data", data);
        try {
            response = template.render(model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private ArrayList<Fundraise> getFundraiseList() throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.getFundraiseList();

        return fundraiseList;
    }

    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Fundraise> fundraiseList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Fundraise fundraise: fundraiseList){
            record.add(fundraise.getFundraiseID().toString());
            record.add(fundraise.getTitle());
            data.add(record);
            record = new ArrayList<>();
        }


        return data;
    }



}

