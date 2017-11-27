package controller.Mentor.Quest;

import DAO.QuestDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Fundraise;
import models.Quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class CreateQuest implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        try {


            if (method.equals("GET")) {

                response =  WebTemplate.getSiteContent("templates/mentor/create-quest.twig");

            }

            if (method.equals("POST")) {

                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                        "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> inputs = parseFormData(formData);

                Quest quest = createQuest(inputs);
                addNewQuestToDatabase(quest);





            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Quest createQuest(Map<String, String> inputs) {

        String name =inputs.get("name");
        String description = inputs.get("description");
        Integer value = Integer.valueOf(inputs.get("value"));
        Integer experience = Integer.valueOf(inputs.get("experience"));
        String category = inputs.get("category");

        Quest quest = new Quest(name, description, value, experience, category);

        return quest;
    }

    private void addNewQuestToDatabase(Quest quest) throws SQLException {

        QuestDAO questDAO = new QuestDAO();
        questDAO.add(quest);
    }
}
