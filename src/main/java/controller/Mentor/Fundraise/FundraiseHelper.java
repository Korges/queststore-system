package controller.Mentor.Fundraise;

import DAO.ConnectDB;
import DAO.FundraiseDAO;
import DAO.InventoryDAO;
import DAO.StudentDAO;
import UI.UI;
import models.Fundraise;
import models.Inventory;
import models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FundraiseHelper {


    public boolean finalizeFundraise(Map<String, String> parsedForm) {

        boolean status = false;
        Integer fundraiseID = Integer.valueOf(parsedForm.get("id"));

        try {
            if (checkSaldo(fundraiseID)) {
                addFundraiseToInventory(fundraiseID);
                changeSaldo(fundraiseID);
                deleteFinalizedFundraise(fundraiseID);
                status = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void changeSaldo(Integer fundraiseID) throws SQLException {

        StudentDAO studentDAO = new StudentDAO();
        Integer pricePerStudent = countPricePerStudent(fundraiseID);
        ArrayList<Fundraise> fundraiseStudentList = getFundraiseStudentList(fundraiseID);
        for (Fundraise fundraise : fundraiseStudentList) {
            Student student = studentDAO.getStudentById(fundraise.getStudentID());
            student.wallet.substract(pricePerStudent);
            studentDAO.editWalletValue(student);
        }
    }


    private boolean checkSaldo(Integer fundraiseID) throws SQLException {

        StudentDAO studentDAO = new StudentDAO();
        Integer pricePerStudent = countPricePerStudent(fundraiseID);
        ArrayList<Fundraise> fundraiseStudentList = getFundraiseStudentList(fundraiseID);
        for(Fundraise fundraise: fundraiseStudentList) {

            Integer studentID = fundraise.getStudentID();
            if(studentDAO.getStudentById(studentID).getWallet().getBalance() < pricePerStudent) {
                return false;
            }
        }
        return true;
    }


    private Integer countPricePerStudent(Integer fundraiseID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraiseStudentList(fundraiseID);
        Fundraise fundraise = fundraiseDAO.getFundraiseByID(fundraiseID);

        Integer pricePerOneStudent = fundraise.getPrice()/fundraiseStudentList.size();

        return pricePerOneStudent;
    }


    private void addFundraiseToInventory(Integer fundraiseID) throws SQLException {

        InventoryDAO inventoryDAO = new InventoryDAO();

        String date = UI.getCurrentDate();
        Integer price = countPricePerStudent(fundraiseID);
        Integer artifactID = getArtifactID(fundraiseID);

        ArrayList<Fundraise> fundraiseStudentList = getFundraiseStudentList(fundraiseID);
        for(Fundraise fundraise: fundraiseStudentList) {
            Integer studentID = fundraise.getStudentID();
            Inventory inventory = new Inventory(studentID, artifactID, date, price);
            inventoryDAO.add(inventory);
        }
    }


    private ArrayList<Fundraise> getFundraiseStudentList(Integer fundraiseID) throws SQLException {
        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraiseStudentList(fundraiseID);

        return fundraiseStudentList;
    }


    private void deleteFinalizedFundraise(Integer fundraiseID) throws SQLException {

        FundraiseDAO fundraiseDAO = new FundraiseDAO();
        fundraiseDAO.deleteFundraise(fundraiseID);
        fundraiseDAO.deleteFundraiseStudents(fundraiseID);

    }


    private Integer getArtifactID(Integer fundraiseID) throws SQLException {

        ConnectDB connectDB = ConnectDB.getInstance();
        String sql = String.format("SELECT artifact_id FROM fundraises WHERE id LIKE '%s'", fundraiseID);
        ResultSet result = connectDB.getResult(sql);


        Integer artifactID = result.getInt("artifact_id");

        return artifactID;
    }
}
