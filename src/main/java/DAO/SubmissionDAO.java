package DAO;

import models.Submission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubmissionDAO implements InterfaceDAO<Submission> {

    private connectDB connect;

    public SubmissionDAO() throws SQLException{
        connect = connectDB.getInstance();
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
        if(isMarked.equals(1)) {
            return true;
        }else return false;
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
        Integer result = null;
        ResultSet dbResult = connect.getResult(querry);
        result = dbResult.getInt("value");
        return result;
    }
}
