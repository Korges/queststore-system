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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controller.helpers.ParseForm.parseFormData;

public class QuestPanel {

    public boolean addNewQuestToDatabase(Map<String, String> inputs) {

        boolean status = false;

        try {
            Quest quest = createQuest(inputs);
            QuestDAO questDAO = new QuestDAO();
            questDAO.add(quest);
            status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(status);
        return status;
    }

    private Quest createQuest(Map<String, String> inputs) throws SQLException {

        String name = inputs.get("name");
        String description = inputs.get("description");
        Integer value = Integer.valueOf(inputs.get("value"));
        Integer experience = Integer.valueOf(inputs.get("experience"));
        String category = inputs.get("category");

        Quest quest = new Quest(name, description, value, experience, category);

        return quest;
    }

        private ArrayList<Quest> getQuestList() throws SQLException {

            QuestDAO questDAO = new QuestDAO();
            ArrayList<Quest> questList = questDAO.get();

            return questList;
        }

        public boolean deleteQuest(Map<String, String> parsedForm) {

            boolean status = false;
            Integer questID = Integer.valueOf(parsedForm.get("id"));
            try {
                QuestDAO questDAO = new QuestDAO();
                questDAO.deleteQuest(questID);
                status = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return status;
        }


        public List<Quest> getFullQuestList()  {

            List<Quest> questList = null;
            try {
                QuestDAO questDAO = new QuestDAO();
                questList = questDAO.get();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return questList;
        }

    public List<Quest> getQuests() {
        List<Quest> quests = null;
        try {
            QuestDAO questDAO = new QuestDAO();
            quests = questDAO.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }

}
