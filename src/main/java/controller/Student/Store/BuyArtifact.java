package controller.Student.Store;

import DAO.ArtifactDAO;
import DAO.ConnectDB;
import DAO.FundraiseDAO;
import DAO.InventoryDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.StudentController;
import models.Artifact;
import models.Fundraise;
import models.Inventory;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import UI.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


import static controller.helpers.ParseForm.parseFormData;

public class BuyArtifact implements HttpHandler {







    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {


                response = listAllBasicItems();
            }





            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);


                String sessionID = StudentController.getSession();

                Integer artifactID = Integer.valueOf(inputs.get("id"));
                Integer userID = Integer.valueOf(getUserID(sessionID));



                buyArtifact(userID, artifactID);


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

        ConnectDB connectDB = ConnectDB.getInstance();
        String sql = String.format("SELECT user_id FROM sessions WHERE session_id LIKE '%s'", sessionIDFull);
        ResultSet result = connectDB.getResult(sql);


        String userID = result.getString("user_id");

        return userID;

    }

    private Integer getArtifactPrice(Integer artifactID) throws SQLException {

        ArtifactDAO artifactDAO = new ArtifactDAO();
        Integer artifactPrice = artifactDAO.getSingleArtifact(artifactID).getPrice();

        return artifactPrice;
    }








    public void buyArtifact(Integer studentID, Integer artifactID) throws SQLException {

        String date = UI.getCurrentDate();
        Integer artifactPrice = getArtifactPrice(artifactID);

        InventoryDAO inventoryDAO = new InventoryDAO();

        Inventory inventory = new Inventory(studentID, artifactID, date, artifactPrice);
        inventoryDAO.add(inventory);

    }






    public String listAllBasicItems() throws SQLException{

        ArrayList<Artifact> basicItemList = getBasicItemList();
        ArrayList<ArrayList<String>> data = createJtwigData(basicItemList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/buy-artifact.twig");
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

    private ArrayList<Artifact> getBasicItemList() throws SQLException {

        ArtifactDAO artifactDAO = new ArtifactDAO();
        ArrayList<Artifact> basicItemList = artifactDAO.getBasicItems();

        return basicItemList;
    }



    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Artifact> basicItemList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Artifact artifact: basicItemList){
            record.add(artifact.getID().toString());
            record.add(artifact.getName());
              data.add(record);
            record = new ArrayList<>();
        }

        return data;
    }

}

