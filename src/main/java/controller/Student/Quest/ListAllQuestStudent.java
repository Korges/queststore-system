package controller.Student.Quest;

import DAO.QuestDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Quest;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListAllQuestStudent implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {

                response = listAllQuest();

            }


        } catch (SQLException e) {


        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String listAllQuest() throws SQLException{

        ArrayList<Quest> questList = getQuestList();
        ArrayList<ArrayList<String>> data = createJtwigData(questList);


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/view-all-quest.twig");
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

    private ArrayList<Quest> getQuestList() throws SQLException {

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
            record.add(quest.getDescription());
            record.add(quest.getValue().toString());
            record.add(quest.getExperience().toString());
            record.add(quest.getCategory().toString());
            data.add(record);
            record = new ArrayList<>();
        }

    return data;
    }
}

