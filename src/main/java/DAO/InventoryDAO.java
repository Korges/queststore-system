package DAO;

import models.Inventory;
import models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAO {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Inventory inventory) {

        String sql = String.format("INSERT INTO student_inventory " +
                "(student_id, artifact_id, date)" +
                " VALUES ('%d', '%d', '%s')", inventory.getStudentID(), inventory.getArtifactID(), inventory.getDate());

        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList get() {

        ArrayList<Inventory> inventoryList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult(String.format("SELECT * FROM student_inventory"));

            while (result.next()) {
                Integer studentID = result.getInt("student_id");
                Integer artifactID = result.getInt("artifact_id");
                String date = result.getString("date");
                Inventory inventory = new Inventory(studentID, artifactID, date);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return inventoryList;

    }

    public ArrayList<Inventory> getStudentInventory(Student student) {

        ArrayList<Inventory> inventoryList = new ArrayList<>();
        try {

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
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return inventoryList;

    }

}