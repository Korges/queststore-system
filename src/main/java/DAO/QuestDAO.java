package DAO;

import models.Quest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestDAO implements InterfaceDAO<Quest> {

    private connectDB connect = DAO.connectDB.getInstance();

    private ArrayList<Quest> questList = new ArrayList<>();

    public void add(Quest quest) {

        String querry = String.format("INSERT INTO quests "+
                "(name, description, value, experience, quest_category ) " +
                "VALUES ('%s', '%s', '%d', '%d', '%s')", quest.getName(), quest.getDescription(), quest.getValue(), quest.getExperience(), quest.getCategory());
        try {
            connect.addRecord(querry);
            System.out.println(querry);
        } catch (SQLException e) {
            System.out.println("Something went wrong, propably database is occupied by another process, shutting down...");
            System.exit(0);
        }

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

    public ArrayList get(){
        ArrayList<Quest> questList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult("SELECT * from quests;");
            while (result.next()) {
                Quest quest = createQuest(result);
                questList.add(quest);

            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return questList;
    }


    public void set(Quest quest) {
        try {
            String querry = String.format("UPDATE quests + " +
             "SET name='%s',description = '%s',value = '%d', experience = '%d', category = '%s' " +
             "WHERE id = %d", quest.getName(),quest.getDescription(),quest.getValue(),quest.getExperience(),quest.getCategory(), quest.getId());
            connect.addRecord(querry);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

}
