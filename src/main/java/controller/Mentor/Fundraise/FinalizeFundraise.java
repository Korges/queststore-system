package controller.Mentor.Fundraise;

import DAO.ConnectDB;
import DAO.FundraiseDAO;
import DAO.InventoryDAO;
import DAO.StudentDAO;
import UI.UI;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Fundraise;
import models.Inventory;
import models.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class FinalizeFundraise implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {


                response = loadFundraiseToSelectForm();
            }


            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);

                Integer fundraiseID = Integer.valueOf(inputs.get("id"));

                finalizeFundraise(fundraiseID);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private ArrayList<Fundraise> getFundraiseStudentList(Integer fundraiseID) throws SQLException {
        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraiseStudentList(fundraiseID);

        return fundraiseStudentList;
    }

    private void finalizeFundraise(Integer fundraiseID) throws SQLException {

        if(checkSaldo(fundraiseID)) {
            addFundraiseToInventory(fundraiseID);
            deleteFinalizedFundraise(fundraiseID);
            System.out.println("udało sie");
        }
    }

    private boolean checkSaldo(Integer fundraiseID) throws SQLException {

        StudentDAO studentDAO = new StudentDAO();

        Integer pricePerStudent = countPricePerStudent(fundraiseID);

        ArrayList<Fundraise> fundraiseStudentList = getFundraiseStudentList(fundraiseID);

        for(Fundraise fundraise: fundraiseStudentList) {
            Integer studentID = fundraise.getStudentID();
            if(studentDAO.getStudentById(studentID).getWallet().getBalance() < pricePerStudent) {
                return false;
            }
        }
        return true;


    }

    private void addFundraiseToInventory(Integer fundraiseID) throws SQLException {

        InventoryDAO inventoryDAO = new InventoryDAO();

        String date = UI.getCurrentDate();
        Integer price = countPricePerStudent(fundraiseID);
        Integer artifactID = getArtifactID(fundraiseID);

        ArrayList<Fundraise> fundraiseStudentList = getFundraiseStudentList(fundraiseID);

        for(Fundraise fundraise: fundraiseStudentList) {
            Integer studentID = fundraise.getStudentID();
            Inventory inventory = new Inventory(studentID, artifactID, date, price);
            inventoryDAO.add(inventory);


        }
    }

    private void deleteFinalizedFundraise(Integer fundraiseID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.deleteFundraise(fundraiseID);
        fundraiseDAO.deleteFundraiseStudents(fundraiseID);

    }

    private Integer getArtifactID(Integer fundraiseID) throws SQLException {

        ConnectDB connectDB = ConnectDB.getInstance();
        String sql = String.format("SELECT artifact_id FROM fundraises WHERE id LIKE '%s'", fundraiseID);
        ResultSet result = connectDB.getResult(sql);


        Integer artifactID = result.getInt("artifact_id");

        return artifactID;
    }

    private Integer countPricePerStudent(Integer fundraiseID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraiseStudentList(fundraiseID);
        Fundraise fundraise = fundraiseDAO.getFundraiseByID(fundraiseID);

        Integer pricePerOneStudent = fundraise.getPrice()/fundraiseStudentList.size();

        return pricePerOneStudent;
    }

    private String loadFundraiseToSelectForm() throws SQLException {

        ArrayList<Fundraise> fundraiseList = getFullFundraiseList();
        ArrayList<ArrayList<String>> data = createJtwigData(fundraiseList);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/finalize-fundraise.twig");
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

    private ArrayList<Fundraise> getFullFundraiseList() throws SQLException {

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