package DAO;

import models.Inventory;
import models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAO {

    private ConnectDB connect;

    public InventoryDAO() throws SQLException{
        connect = DAO.ConnectDB.getInstance();
    }

    public void add(Inventory inventory) throws SQLException{

        String sql = String.format("INSERT INTO logs " +
                "(student_id, artifact_id, date, price)" +
                " VALUES ('%d', '%d', '%s', '%d')", inventory.getStudentID(), inventory.getArtifactID(), inventory.getDate(), inventory.getPrice());
        connect.addRecord(sql);
    }

    public ArrayList get() throws SQLException{

        ArrayList<Inventory> inventoryList = new ArrayList<>();
            ResultSet result = connect.getResult("SELECT * FROM logs");

        while (result.next()) {
            Integer studentID = result.getInt("student_id");
            Integer artifactID = result.getInt("artifact_id");
            String date = result.getString("date");
            Integer price = result.getInt("price");
            Inventory inventory = new Inventory(studentID, artifactID, date, price);
            inventoryList.add(inventory);
        }

        return inventoryList;

    }

//    public ArrayList<Inventory> getStudentInventory(Student student) throws SQLException {
//
//        ArrayList<Inventory> inventoryList = new ArrayList<>();
//            ResultSet result = connect.getResult(String.format("SELECT * FROM student_inventory JOIN artifacts on artifact_id = id WHERE student_id = %d", student.getID()));
//
//        while (result.next()) {
//            String name = result.getString("name");
//            String description = result.getString("description");
//            String date = result.getString("date");
//            Integer price = result.getInt("price");
//            boolean isMagic = result.getBoolean("is_magic");
//
//            Inventory inventory = new Inventory(name, description, date, price, isMagic);
//            inventoryList.add(inventory);
//        }
//
//        return inventoryList;
//
//    }

}