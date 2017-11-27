package controller.Mentor.Quest;

import DAO.FundraiseDAO;
import DAO.QuestDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Fundraise;
import models.Quest;
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

public class DeleteQuest implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {


                response = loadQuestToSelectForm();
            }


            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);

                Integer questID = Integer.valueOf(inputs.get("id"));
                deleteQuest(questID);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void deleteQuest(Integer questID) throws SQLException {

        QuestDAO questDAO = new QuestDAO();
        questDAO.deleteQuest(questID);
    }

    private String loadQuestToSelectForm() throws SQLException {

        ArrayList<Quest> questList = getFullQuestList();
        ArrayList<ArrayList<String>> data = createJtwigData(questList);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/delete-quest.twig");
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

    private ArrayList<Quest> getFullQuestList() throws SQLException {

        QuestDAO questDAO = new QuestDAO();

        ArrayList<Quest> questList = questDAO.get();

        return questList;
    }

    private ArrayList<ArrayList<String>> createJtwigData(ArrayList<Quest> questList) {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> record = new ArrayList<>();

        for(Quest quest: questList){
            record.add(quest.getId().toString());
            record.add(quest.getName());
            data.add(record);
            record = new ArrayList<>();
        }

        return data;
    }
}