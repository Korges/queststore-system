package DAO;

import models.Inventory;
import models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAO {

    private connectDB connect;

    public InventoryDAO() throws SQLException{
        connect = DAO.connectDB.getInstance();
    }

    public void add(Inventory inventory) throws SQLException{

        String sql = String.format("INSERT INTO student_inventory " +
                "(student_id, artifact_id, date)" +
                " VALUES ('%d', '%d', '%s')", inventory.getStudentID(), inventory.getArtifactID(), inventory.getDate());
        connect.addRecord(sql);
    }

    public ArrayList get() throws SQLException{

        ArrayList<Inventory> inventoryList = new ArrayList<>();
            ResultSet result = connect.getResult("SELECT * FROM student_inventory");

        while (result.next()) {
            Integer studentID = result.getInt("student_id");
            Integer artifactID = result.getInt("artifact_id");
            String date = result.getString("date");
            Inventory inventory = new Inventory(studentID, artifactID, date);
            inventoryList.add(inventory);
        }

        return inventoryList;

    }

    public ArrayList getSingleStudent(Student student) throws SQLException{

        ArrayList<Inventory> inventoryList = new ArrayList<>();
            ResultSet result = connect.getResult(String.format("SELECT * FROM student_inventory JOIN artifacts on artifact_id = id WHERE student_id = %d", student.getID()));

        while (result.next()) {
            String name = result.getString("name");
            String description = result.getString("description");
            String date = result.getString("date");
            Integer price = result.getInt("price");
            boolean isMagic = result.getBoolean("is_magic");

            Inventory inventory = new Inventory(name, description, date, price, isMagic);
            inventoryList.add(inventory);
        }

        return inventoryList;

    }

}