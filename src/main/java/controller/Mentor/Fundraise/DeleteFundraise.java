package controller.Mentor.Fundraise;

import DAO.FundraiseDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Fundraise;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class DeleteFundraise implements HttpHandler {


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

                deleteFundraise(fundraiseID);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void deleteFundraise(Integer fundraiseID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.deleteFundraise(fundraiseID);
        fundraiseDAO.deleteFundraiseStudents(fundraiseID);
    }

    private String loadFundraiseToSelectForm() throws SQLException {

        ArrayList<Fundraise> fundraiseList = getFullFundraiseList();
        ArrayList<ArrayList<String>> data = createJtwigData(fundraiseList);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/delete-fundraise.twig");
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