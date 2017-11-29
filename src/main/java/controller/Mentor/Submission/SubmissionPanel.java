package controller.Mentor.Submission;

import DAO.SubmissionDAO;
import models.Submission;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SubmissionPanel {


    public List<Submission> getUnfinishedSubmissionList() {

        List<Submission> submissionList = null;
        try {
            SubmissionDAO submissionDAO = new SubmissionDAO();
            submissionList = submissionDAO.getUnfinishedSubmission();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissionList;
    }


    public boolean completeSubmission(Map<String, String> parsedForm) {

        boolean status = false;
        Integer submissionID = Integer.valueOf(parsedForm.get("id"));
        SubmissionDAO submissionDAO = null;

        try {
            submissionDAO = new SubmissionDAO();
            submissionDAO.completeSubmission(submissionID);
            status = true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return status;
    }
}
