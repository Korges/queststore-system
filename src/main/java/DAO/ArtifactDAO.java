package DAO;

import models.Artifact;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtifactDAO implements InterfaceDAO<Artifact> {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Artifact artifact){

        Integer isMagic = 0;

        if(artifact.getIsMagic()) {
            isMagic = 1;
        }

        String sql = String.format("INSERT INTO artifacts " +
        "(price, description, is_magic)" +
                "VALUES ('%d', '%s', '%s', '%d')", artifact.getName(), artifact.getPrice(), artifact.getDescription(), isMagic);

        try {
            connect.addRecord(sql);
            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println("Something get wrong");;
            System.exit(0);
        }


    }

    public ArrayList get(){
        ArrayList<Artifact> artifactList = new ArrayList<>();
        return artifactList;
    }

    public void set(Artifact artifact) {

//        for(Artifact item : artifactList){
//            System.out.println(item);
//        }
    }


}
