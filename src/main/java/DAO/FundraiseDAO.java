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


    public void removeFundraise(Fundraise fundraise) {
        String sql = String.format("DELETE FROM fundraises WHERE fundraise_id = '%d'", fundraise.getFundraiseID());
         try {
             connect.addRecord(sql);
         } catch (SQLException e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         }
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
}


