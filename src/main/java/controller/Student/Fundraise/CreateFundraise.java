package controller.Student.Fundraise;

import DAO.ArtifactDAO;
import DAO.FundraiseDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.AdminController;
import controller.StudentController;
import models.Artifact;
import models.Fundraise;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class CreateFundraise implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        try {


            if (method.equals("GET")) {

                response = listAllMagicItems();
            }

            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                        "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);


                Fundraise fundraise = createFundraise(inputs);
                addNewFundraiseToDatabase(fundraise);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
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

    public String listAllMagicItems() throws SQLException{

        ArrayList<Artifact> fundraiseList = getMagicItemList();
        ArrayList<ArrayList<String>> data = createJtwigData(fundraiseList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/create-fundraise.twig");
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

    private ArrayList<Artifact> getMagicItemList() throws SQLException {

        ArtifactDAO artifactDAO = new ArtifactDAO();
        ArrayList<Artifact> artifactList = artifactDAO.getMagicItems();

        return artifactList;
    }

    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Artifact> artifactList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Artifact artifact: artifactList){
            record.add(artifact.getID().toString());
            record.add(artifact.getName());
            data.add(record);
            record = new ArrayList<>();
        }


        return data;
    }




}
