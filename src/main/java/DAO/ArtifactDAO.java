package DAO;

import java.sql.ResultSet;
import models.Artifact;
import java.sql.SQLException;
import java.util.ArrayList;
import models.BasicItem;
import models.MagicItem;

public class ArtifactDAO implements InterfaceDAO<Artifact> {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Artifact artifact){

        Integer isMagic = 0;

        if(artifact.getIsMagic()) {
            isMagic = 1;
        }

        String sql = String.format("INSERT INTO artifacts " +
        "(name, description, price, is_magic)" +
                "VALUES ('%s', '%s', '%d', '%d')", artifact.getName(), artifact.getDescription(), artifact.getPrice(), isMagic);

        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.out.println("Something get wrong");
            System.exit(0);
        }
    }


    public ArrayList<Artifact> get(){

        ArrayList<Artifact> artifactList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult("SELECT * FROM artifacts");
            while (result.next()) {

                Integer  id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Integer price = result.getInt("price");
                Integer is_Magic = result.getInt("is_magic");
                if(is_Magic.equals(1)){
                    MagicItem newItem = new MagicItem(id, name, description, price, true);
                    artifactList.add(newItem);
                } else {
                    BasicItem newItem = new BasicItem(id, name, description, price, false);
                    artifactList.add(newItem);
                }

            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return artifactList;
    }

    public ArrayList getMagicItems(){

        ArrayList<Artifact> artifactList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult("SELECT * FROM artifacts WHERE is_magic = 1");
            while (result.next()) {

                Integer  id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Integer price = result.getInt("price");
                MagicItem newItem = new MagicItem(id, name, description, price, true);
                artifactList.add(newItem);
                            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return artifactList;
    }


    public void set(Artifact artifact) {

        try {

            Integer isMagic = 0;

            if(artifact.getIsMagic()) {
                isMagic = 1;
            }
            String sql = String.format("UPDATE artifacts SET name='%s',description = '%s',price = '%d', is_magic = '%d' WHERE id = %d",artifact.getName(),artifact.getDescription(),artifact.getPrice(),isMagic, artifact.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public void remove(Artifact artifact) {
        try {
            String sql = String.format("DELETE from artifacts WHERE id = %s", artifact.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
