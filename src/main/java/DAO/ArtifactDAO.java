package DAO;

import models.Artifact;

import java.util.ArrayList;

public class ArtifactDAO implements InterfaceDAO<Artifact> {

    private ArrayList<Artifact> artifactList = new ArrayList<>();

    public void add(Artifact artifact){
        artifactList.add(artifact);
    }

    public ArrayList get(){
        return artifactList;
    }

    public void set(Artifact artifact) {

        for(Artifact item : artifactList){
            System.out.println(item);
        }
    }


}
