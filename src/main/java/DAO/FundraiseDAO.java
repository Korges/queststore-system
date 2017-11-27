package DAO;

import models.Fundraise;
import models.Student;
import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FundraiseDAO {

    ConnectDB connect;

    public FundraiseDAO() throws SQLException {
        connect = DAO.ConnectDB.getInstance();
    }


    public void createNewFundraise(Fundraise fundraise) throws SQLException {

        String sql = String.format("INSERT INTO fundraises" +
                "(artifact_id, title)" +
                "VALUES ('%d', '%s')", fundraise.getArtifactID(), fundraise.getTitle());
        connect.addRecord(sql);
    }

    public void joinFundraise(String fundraiseID, String userID) throws SQLException{

        String sql = String.format("INSERT INTO fundraises_students" +
                "(fundraise_id, student_id)" +
                "VALUES ('%s', '%s')", fundraiseID, userID);
        connect.addRecord(sql);
    }
    public void leaveFundraise(String fundraiseID, String userID) {

         String sql = String.format("DELETE FROM fundraises_students WHERE (fundraise_id = '%s' and student_id = '%s')", fundraiseID, userID);
         try {
             connect.addRecord(sql);
         } catch (SQLException e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         }
    }


    public void deleteFundraise(Integer fundraiseID) {
        String sql = String.format("DELETE FROM fundraises WHERE id = '%d'", fundraiseID);
         try {
             connect.addRecord(sql);
         } catch (SQLException e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         }
    }

    public void deleteFundraiseStudents(Integer fundraiseID) {
        String sql = String.format("DELETE FROM fundraises_students WHERE fundraise_id = '%d'", fundraiseID);
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Fundraise getFundraiseByID(Integer id) {

        Fundraise fundraise = new Fundraise();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises JOIN artifacts ON fundraises.artifact_id = artifacts.id WHERE fundraises.id = '%d'", id));

            while (result.next()) {
                Integer fundraiseID = result.getInt("id");
                String title = result.getString("title");
                String name = result.getString("name");
                Integer price = result.getInt("price");

                 fundraise = new Fundraise(fundraiseID, title, name, price);

            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return fundraise;
    }

    public ArrayList<Fundraise> getFundraiseList() {
        ArrayList<Fundraise> fundraiseStudentList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises JOIN artifacts ON fundraises.artifact_id = artifacts.id"));

            while (result.next()) {
                Integer fundraiseID = result.getInt("id");
                String title = result.getString("title");
                String name = result.getString("name");
                Integer price = result.getInt("price");

                Fundraise fundraise = new Fundraise(fundraiseID, title, name, price);
                fundraiseStudentList.add(fundraise);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return fundraiseStudentList;
    }

    public ArrayList<Fundraise> getFundraiseStudentList(Integer fundraiseID) {

        ArrayList<Fundraise> fundraiseStudentList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises_students WHERE fundraise_id = '%d'", fundraiseID));

            while (result.next()) {
                Integer studentID = result.getInt("student_id");

                Fundraise fundraise = new Fundraise(studentID);
                fundraiseStudentList.add(fundraise);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return fundraiseStudentList;
    }






    public ArrayList<Fundraise> getJoinedFundraiseList(String userID) {

        ArrayList<Fundraise> fundraiseStudentList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM fundraises_students JOIN fundraises ON fundraises_students.fundraise_id = fundraises.id JOIN artifacts ON fundraises.artifact_id = artifacts.id WHERE student_id = '%s'", userID));

            while (result.next()) {
                Integer fundraiseID = result.getInt("fundraise_id");
                String title = result.getString("title");
                String name = result.getString("name");
                Integer price = result.getInt("price");


                Fundraise fundraise = new Fundraise(fundraiseID, title, name, price);
                fundraiseStudentList.add(fundraise);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return fundraiseStudentList;
    }
}


