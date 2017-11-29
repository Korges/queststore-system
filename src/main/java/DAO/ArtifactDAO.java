package DAO;

import java.sql.ResultSet;
import models.Artifact;
import java.sql.SQLException;
import java.util.ArrayList;
import models.BasicItem;
import models.MagicItem;

public class ArtifactDAO implements InterfaceDAO<Artifact> {

    private ConnectDB connect;

    public ArtifactDAO() throws SQLException{

        connect = DAO.ConnectDB.getInstance();
    }



    public void add(Artifact artifact) throws SQLException{

        Integer isMagic = 0;

        if(artifact.getIsMagic()) {
            isMagic = 1;
        }

        String sql = String.format("INSERT INTO artifacts " +
        "(name, description, price, is_magic)" +
                "VALUES ('%s', '%s', '%d', '%d')", artifact.getName(), artifact.getDescription(), artifact.getPrice(), isMagic);

        connect.addRecord(sql);
    }


    public ArrayList<Artifact> get() throws SQLException{

        ArrayList<Artifact> artifactList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * FROM artifacts");
        while (result.next()) {

            Integer id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");
            Integer price = result.getInt("price");
            Integer is_Magic = result.getInt("is_magic");
            if (is_Magic.equals(1)) {
                MagicItem newItem = new MagicItem(id, name, description, price, true);
                artifactList.add(newItem);
            } else {
                BasicItem newItem = new BasicItem(id, name, description, price, false);
                artifactList.add(newItem);
            }
        }
        return artifactList;
    }

    public Artifact getArtifactById(Integer artifactID) throws SQLException {

        Artifact artifact;
        ConnectDB connectDB = DAO.ConnectDB.getInstance();

        String sql = String.format("SELECT * FROM artifacts WHERE id = '%d'", artifactID);
        ResultSet result = connect.getResult(sql);

        Integer id = result.getInt("id");
        String name = result.getString("name");
        String description = result.getString("description");
        Integer price = result.getInt("price");
        boolean isMagic = result.getBoolean("is_magic");



        if(isMagic) {
            artifact = new MagicItem(id, name, description, price, true);
        }
        else {
            artifact = new BasicItem(id, name, description, price, false);
        }

        return artifact;
        }




    private String getUserID(String sessionIDFull) throws SQLException {

        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        String sql = String.format("SELECT user_id FROM sessions WHERE session_id LIKE '%s'", sessionIDFull);
        ResultSet result = connectDB.getResult(sql);


        String userID = result.getString("user_id");



        return userID;

    }

    public ArrayList<Artifact> getMagicItems() throws SQLException{

        ArrayList<Artifact> artifactList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * FROM artifacts WHERE is_magic = 1");
        while (result.next()) {

            Integer id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");
            Integer price = result.getInt("price");
            MagicItem newItem = new MagicItem(id, name, description, price, true);
            artifactList.add(newItem);
        }
        return artifactList;
    }

    public ArrayList<Artifact> getBasicItems() throws SQLException{

        ArrayList<Artifact> artifactList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * FROM artifacts WHERE is_magic = 0");
        while (result.next()) {

            Integer  id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");
            Integer price = result.getInt("price");
            MagicItem newItem = new MagicItem(id, name, description, price, false);
            artifactList.add(newItem);
        }
        return artifactList;
    }


    public void set(Artifact artifact) throws SQLException{

            Integer isMagic = 0;

            if(artifact.getIsMagic()) {
                isMagic = 1;
            }
            String sql = String.format("UPDATE artifacts SET name='%s',description = '%s',price = '%d', is_magic = '%d' WHERE id = %d",artifact.getName(),artifact.getDescription(),artifact.getPrice(),isMagic, artifact.getID());
            connect.addRecord(sql);
    }


    public void remove(Artifact artifact) throws SQLException{
        String sql = String.format("DELETE from artifacts WHERE id = %s", artifact.getID());
        connect.addRecord(sql);
    }
}
