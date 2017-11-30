package controller.Student.Store;

import DAO.ArtifactDAO;
import DAO.InventoryDAO;
import DAO.StudentDAO;
import models.Artifact;
import models.Inventory;
import models.Student;
import UI.UI;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StorePanel {


    public Integer getArtifactPrice(Integer artifactID){
        Integer artifactPrice = null;
        ArtifactDAO artifactDAO = null;
        try {
            artifactDAO = new ArtifactDAO();
            artifactPrice = artifactDAO.getArtifactById(artifactID).getPrice();
        } catch (SQLException e) {

        }
        return artifactPrice;
    }


    public boolean buyArtifact(Map<String, String> parsedForm, Student student) {

        boolean status = false;
        Integer artifactID = Integer.valueOf(parsedForm.get("id"));
        Integer artifactPrice = getArtifactPrice(artifactID);
        String date = UI.getCurrentDate();
        InventoryDAO inventoryDAO = null;

        try {

            Inventory inventory = new Inventory(student.getID(), artifactID, date, artifactPrice);
            inventoryDAO = new InventoryDAO();
            inventoryDAO.add(inventory);
            changeSaldo(student, artifactPrice);
            status = true;

        } catch (SQLException e) {
            return status;
        }
        return status;
    }

    public void changeSaldo(Student student, Integer artifactPrice) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        student.wallet.substract(artifactPrice);
        studentDAO.editWalletValue(student);

    }


    public List<Artifact> getBasicItemList() {

        List<Artifact> basicItemList = null;
        try {
            ArtifactDAO artfactDAO = new ArtifactDAO();
            basicItemList = artfactDAO.getBasicItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basicItemList;
    }


    public List<Inventory> getStudentInventoryList(Student student) {

        List<Inventory> inventoryList = null;
        try {
            InventoryDAO inventoryDAO= new InventoryDAO();
            inventoryList = inventoryDAO.getStudentInventory(student);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }


}

