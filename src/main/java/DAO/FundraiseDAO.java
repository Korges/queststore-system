package DAO;

import models.Fundraise;
import models.Student;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FundraiseDAO {

    connectDB connect;
    public FundraiseDAO() throws SQLException {
        connect = DAO.connectDB.getInstance();
    }


    public void add(Fundraise fundraise) throws SQLException {

        String sql = String.format("INSERT INTO fundraises" +
                "(artifact_id, title)" +
                "VALUES ('%d', '%s')", fundraise.getArtifactID(), fundraise.getTitle());
        connect.addRecord(sql);
    }

    public void join(Fundraise fundraise, Student student) throws SQLException{

        String sql = String.format("INSERT INTO fundraises_students" +
                "(fundraise_id, student_id)" +
                "VALUES ('%d', '%d')", fundraise.getFundraiseID(), student.getID());
        connect.addRecord(sql);
    }
    public void remove(Fundraise fundraise) {

         String sql = String.format("DELETE FROM fundraises_students WHERE (fundraise_id = '%d' and student_id = '%d')", fundraise.getFundraiseID(), fundraise.getStudentID());
         try {
             connect.addRecord(sql);
         } catch (SQLException e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         }
    }

    public void removeFundriseByFundraiseID(Fundraise fundraise) {
        String sql = String.format("DELETE FROM fundraises WHERE fundraise_id = '%d'", fundraise.getFundraiseID());
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void removeFundraise(Fundraise fundraise) {
        String sql = String.format("DELETE FROM fundraises WHERE fundraise_id = '%d'", fundraise.getFundraiseID());
         try {
             connect.addRecord(sql);
         } catch (SQLException e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         }
    }

    public ArrayList<Fundraise> get() {

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

    public ArrayList<Fundraise> getFundraisesStudents() {
        ArrayList<Fundraise> fundraiseStudentList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises_students JOIN fundraises ON fundraises_students.fundraise_id = fundraises.fundraise_id JOIN artifacts ON fundraises.artifact_id = artifacts.id"));

            while (result.next()) {
                Integer studentID = result.getInt("student_id");
                Integer fundraiseID = result.getInt("fundraise_id");
                String title = result.getString("title");
                String name = result.getString("name");
                Integer price = result.getInt("price");
                Fundraise fundraise = new Fundraise(studentID, fundraiseID, title, name, price);
                fundraiseStudentList.add(fundraise);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return fundraiseStudentList;
    }

}


