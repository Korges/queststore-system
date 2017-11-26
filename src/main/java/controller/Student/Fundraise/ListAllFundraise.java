package controller.Student.Fundraise;

import DAO.ArtifactDAO;
import DAO.FundraiseDAO;
import DAO.MentorDAO;
import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Artifact;
import models.Fundraise;
import models.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListAllFundraise implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {

                response = listAllFundraise();

            }


        } catch (SQLException e) {


        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String listAllFundraise() throws SQLException{

        ArrayList<Fundraise> fundraiseList = getFundraiseList();
        ArrayList<ArrayList<String>> data = createJtwigData(fundraiseList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/view-all-fundraise.twig");
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
            record.add(fundraise.getName());
            record.add(fundraise.getPrice().toString());
            data.add(record);
            record = new ArrayList<>();
        }

    return data;
    }
}

