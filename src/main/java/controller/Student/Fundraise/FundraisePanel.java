package controller.Student.Fundraise;

import DAO.ArtifactDAO;
import DAO.FundraiseDAO;
import models.Artifact;
import models.Fundraise;
import models.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FundraisePanel {


    public List<Artifact> getMagicItemList(){

        List<Artifact> magicItemList = null;
        try {
            ArtifactDAO artifactDAO = new ArtifactDAO();
            magicItemList = artifactDAO.getMagicItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return magicItemList;
    }


    public boolean joinFundraise(Map<String, String> parsedForm, Student student) {

        boolean status = false;
        Integer fundraiseID = Integer.valueOf(parsedForm.get("id"));
        FundraiseDAO fundraiseDAO = null;

        try {

            fundraiseDAO = new FundraiseDAO();
            fundraiseDAO.joinFundraise(fundraiseID, student.getID());
            status = true;

        } catch (SQLException e) {
            return status;
        }
        return status;
    }


    public boolean leaveFundraise(Map<String, String> parsedForm, Student student) {

        boolean status = false;
        Integer fundraiseID = Integer.valueOf(parsedForm.get("id"));
        FundraiseDAO fundraiseDAO = null;

        try {

            fundraiseDAO = new FundraiseDAO();
            fundraiseDAO.leaveFundraise(fundraiseID, student.getID());
            status = true;

        } catch (SQLException e) {
            return status;
        }
        return status;
    }


    public List<Fundraise> getFundraiseList()  {

        List<Fundraise> fundraiseList = null;
        try {
            FundraiseDAO fundraiseDAO = new FundraiseDAO();
            fundraiseList = fundraiseDAO.getFundraiseList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fundraiseList;
    }


    public List<Fundraise> getJoinedFundraiseList(Integer userID) {
        List<Fundraise> fundraiseList = null;
        try {
            FundraiseDAO fundraiseDAO = new FundraiseDAO();
            fundraiseList = fundraiseDAO.getJoinedFundraiseList(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fundraiseList;
    }


    public boolean createFundraise(Map<String, String> parsedForm) {
        boolean status = false;
        Integer artifactID = Integer.valueOf(parsedForm.get("id"));
        String name = parsedForm.get("title");
        FundraiseDAO fundraiseDAO = null;

        try {
            Fundraise fundraise = new Fundraise(artifactID, name);
            fundraiseDAO = new FundraiseDAO();
            fundraiseDAO.createNewFundraise(fundraise);
            status = true;

        } catch (SQLException e) {
            return status;
        }
        return status;
    }
}
