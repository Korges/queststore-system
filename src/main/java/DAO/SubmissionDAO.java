package DAO;

import models.Submission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubmissionDAO implements InterfaceDAO<Submission> {

    private ConnectDB connect;

    public SubmissionDAO() throws SQLException{
        connect = ConnectDB.getInstance();
    }

    public void add(Submission submission) throws SQLException{

        String querry = String.format("INSERT INTO submissions "+
                "(quest_id, student_id, is_marked, description) " +
                "VALUES ('%d', '%d', '%d', '%s')",
                submission.getQuestId(), submission.getStudentId(),
                dbBooleanInjection(submission.isMarked()), submission.getDescription());
        connect.addRecord(querry);
    }

    private Integer dbBooleanInjection(Boolean isMarked) {
        if(isMarked) {
            return 1;
        }else return 0;
    }

    private Boolean dbBooleanWithdraw(Integer isMarked) {
        return isMarked.equals(1);
    }



    private Submission createSubmission(ResultSet result) throws SQLException {

        Integer id = result.getInt("id");
        Integer questId = result.getInt("quest_id");
        Integer studentId = result.getInt("student_id");
        Boolean isMarked = dbBooleanWithdraw(result.getInt("is_marked"));
        String description = result.getString("description");

        return new Submission(id, studentId, questId, isMarked, description);
    }


    public ArrayList<Submission> get() throws SQLException{

        ArrayList<Submission> submissionList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * from submissions;");
        while (result.next()) {
            Submission submission = createSubmission(result);
            submissionList.add(submission);

        }

        return submissionList;
    }


    public void set(Submission submission) throws SQLException{
        String querry = String.format("UPDATE submissions " +
         "SET quest_id='%d',student_id = '%d',is_marked = '%d', description = '%s'" +
         "WHERE id = %d", submission.getQuestId(),submission.getStudentId(),dbBooleanInjection(submission.isMarked()),submission.getDescription(), submission.getId());
        connect.addRecord(querry);
    }

    public Integer getSubmissionValue(Integer submissionId) throws SQLException{
        String querry = String.format("SELECT value FROM quests INNER JOIN submissions" +
                " ON quests.id = submissions.quest_id WHERE submissions.id = %d", submissionId );
        ResultSet dbResult = connect.getResult(querry);

        return dbResult.getInt("value");
    }

    public Boolean checkAlreadySubmitted(Integer questId, Integer studentId) throws SQLException{

        String query = String.format("SELECT id FROM submissions WHERE quest_id = '%d' AND student_id = '%d';", questId, studentId);
        ResultSet result = connect.getResult(query);
        return result.next();
    }
}
