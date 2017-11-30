package controller.Mentor.Submission;

import DAO.QuestDAO;
import DAO.StudentDAO;
import DAO.SubmissionDAO;
import models.Quest;
import models.Student;
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

            changeSaldo(submissionID);
            submissionDAO.completeSubmission(submissionID);
            status = true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return status;
    }

    public void changeSaldo(Integer submissionID) {

        SubmissionDAO submissionDAO = null;
        StudentDAO studentDAO = null;
        QuestDAO questDAO = null;
        try {
            submissionDAO = new SubmissionDAO();
            questDAO = new QuestDAO();
            studentDAO = new StudentDAO();

            Integer studentID = submissionDAO.getStudentIdBySubmissionId(submissionID);

            Integer questID = submissionDAO.getQuestIdBySubmissionId(submissionID);

            Student student = studentDAO.getStudentById(studentID);

            Quest quest = questDAO.getQuestById(questID);

            Integer questValue = quest.getValue();
            System.out.println(student.getWallet());
            System.out.println(questValue);


            student.wallet.add(questValue);
            studentDAO.editWalletValue(student);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
