package controller.Student.Store;

import DAO.ArtifactDAO;
import DAO.ConnectDB;
import DAO.InventoryDAO;
import DAO.StudentDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.StudentController;
import models.Artifact;
import models.Inventory;
import models.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListUserInventory implements HttpHandler{

    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionID = StudentController.getSession();
        try {
            Integer userID = getUserID(sessionID);

            if (method.equals("GET")) {

                response = listUserInventory(userID);

            }


        } catch (SQLException e) {


        }



        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String listUserInventory(Integer userID) throws SQLException{

        ArrayList<Inventory> inventoryList = getStudentInventoryList(userID);
        ArrayList<ArrayList<String>> data = createJtwigData(inventoryList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/view-user-inventory.twig");
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

    private Integer getUserID(String sessionIDFull) throws SQLException {

        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        String sql = String.format("SELECT user_id FROM sessions WHERE session_id LIKE '%s'", sessionIDFull);
        ResultSet result = connectDB.getResult(sql);


        Integer userID = result.getInt("user_id");



        return userID;

    }

    private ArrayList<Inventory> getStudentInventoryList(Integer userID) throws SQLException {

        InventoryDAO inventoryDAO = new InventoryDAO();
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentById(userID);
        ArrayList<Inventory> inventoryList = inventoryDAO.getStudentInventory(student);

        return inventoryList;
    }

    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Inventory> basicItemList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Inventory inventory: basicItemList){
            record.add(inventory.getName());
            record.add(inventory.getDescription());
            record.add(inventory.getDate());
            record.add(inventory.getPrice().toString());
            record.add(inventory.getCategory());

            data.add(record);
            record = new ArrayList<>();
        }

        return data;
    }
}

