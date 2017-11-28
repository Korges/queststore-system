package DAO;

import models.Quest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestDAO implements InterfaceDAO<Quest> {

    private ConnectDB connect;

    public QuestDAO() throws SQLException{
        connect = DAO.ConnectDB.getInstance();
    }

    public void add(Quest quest) throws SQLException{

        String querry = String.format("INSERT INTO quests "+
                "(name, description, value, experience, quest_category ) " +
                "VALUES ('%s', '%s', '%d', '%d', '%s')", quest.getName(), quest.getDescription(), quest.getValue(), quest.getExperience(), quest.getCategory());
        connect.addRecord(querry);
    }


    private Quest createQuest(ResultSet result) throws SQLException {

        Integer id = result.getInt("id");
        String name = result.getString("name");
        String description = result.getString("description");
        Integer value = result.getInt("value");
        Integer experience = result.getInt("experience");
        String category = result.getString("quest_category");

        return new Quest(id, name, description, value, experience, category);
    }


    public ArrayList<Quest> get() throws SQLException{

        ArrayList<Quest> questList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * from quests;");
        while (result.next()) {
            Quest quest = createQuest(result);
            questList.add(quest);

        }
        return questList;
    }


    public void set(Quest quest) throws SQLException{
        String querry = String.format("UPDATE quests " +
         "SET name='%s',description = '%s',value = '%d', experience = '%d', category = '%s' " +
         "WHERE id = %d", quest.getName(),quest.getDescription(),quest.getValue(),quest.getExperience(),quest.getCategory(), quest.getId());
        connect.addRecord(querry);
    }

    public Boolean checkQuestExist(Integer questID) throws SQLException {

        String query = String.format("SELECT id FROM quests WHERE id = '%d';", questID);
        ResultSet result = connect.getResult(query);
        return result.next();
    }

    public Quest getQuestById(int questID) throws SQLException {
        {
            Quest quest;
            ConnectDB connectDB = DAO.ConnectDB.getInstance();

            String sql = String.format("SELECT * FROM quests WHERE id = '%d'", questID);
            ResultSet result = connect.getResult(sql);

            Integer id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");
            Integer value = result.getInt("value");
            Integer experience = result.getInt("experience");
            String questCategory = result.getString("quest_category");

            quest = new Quest(id, name, description, value, experience, questCategory);

            return quest;
        }

    }
}
