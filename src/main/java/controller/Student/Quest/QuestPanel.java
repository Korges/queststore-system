package controller.Student.Quest;


import DAO.QuestDAO;
import DAO.SubmissionDAO;
import models.Quest;
import models.Student;
import models.Submission;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QuestPanel {


    public List<Quest> getQuestList()  {

        List<Quest> questList = null;
        try {
            QuestDAO fundraiseDAO = new QuestDAO();
            questList = fundraiseDAO.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questList;
    }


    public boolean createSubmission(Map<String, String> parsedForm, Student student) {

        boolean status = false;
        Integer questID = Integer.valueOf(parsedForm.get("id"));
        String description = parsedForm.get("description");
        Submission submission = new Submission(student.getID(), questID, description);
        SubmissionDAO submissionDAO = null;

        try {
            submissionDAO = new SubmissionDAO();
            submissionDAO.add(submission);
            status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }


    public List<Submission> getSubmissionList(Student student) {

        List<Submission> submissionList = null;
        try {
            SubmissionDAO submissionDAO = new SubmissionDAO();
            submissionList = submissionDAO.getStudentSubmissions(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return submissionList;

    }



}

