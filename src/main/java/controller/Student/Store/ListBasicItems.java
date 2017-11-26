package controller.Student.Store;

import DAO.ArtifactDAO;
import DAO.FundraiseDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Artifact;
import models.Fundraise;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import UI.UI;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListBasicItems implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {

                response = listAllBasicItems();

            }


        } catch (SQLException e) {


        }



        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String listAllBasicItems() throws SQLException{

        ArrayList<Artifact> basicItemList = getBasicItemList();
        ArrayList<ArrayList<String>> data = createJtwigData(basicItemList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/view-basic-item.twig");
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

        ArtifactDAO fundraiseDAO = new ArtifactDAO();
        ArrayList<Artifact> basicItemList = fundraiseDAO.getBasicItems();

        return basicItemList;
    }

    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Artifact> basicItemList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Artifact artifact: basicItemList){
            record.add(artifact.getID().toString());
            record.add(artifact.getName());
            record.add(artifact.getDescription());
            record.add(artifact.getPrice().toString());
            data.add(record);
            record = new ArrayList<>();
        }

    return data;
    }
}

