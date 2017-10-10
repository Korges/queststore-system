package DAO;

import models.Fundraise;
import models.Student;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FundraiseDAO {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Fundraise fundraise) {

        String sql = String.format("INSERT INTO fundraises" +
                "(artifact_id, title)" +
                "VALUES ('%d', '%s')", fundraise.getArtifactID(), fundraise.getTitle());
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void join(Fundraise fundraise, Student student) {

        String sql = String.format("INSERT INTO fundraises_students" +
                "(fundraise_id, student_id)" +
                "VALUES ('%d', '%d')", fundraise.getFundraiseID(), student.getID());
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList get() {

        ArrayList<Fundraise> fundraiseList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises JOIN artifacts on artifact_id = id"));

            while (result.next()) {
                Integer fundraiseID = result.getInt("fundraise_id");
                Integer artifactID = result.getInt("artifact_id");
                String title = result.getString("title");
                String name = result.getString("name");
                Fundraise fundraise = new Fundraise(fundraiseID, artifactID, title, name);
                fundraiseList.add(fundraise);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return fundraiseList;

    }
}


