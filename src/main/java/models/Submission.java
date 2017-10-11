package models;

public class Submission {

    private int id;
    private int studentId;
    private int questId;
    private boolean isMarked;
    private String description;

    public Submission(int id, int studentId, int questId, boolean isMarked, String description) {
        this.id = id;
        this.studentId = studentId;
        this.questId = questId;
        this.isMarked = isMarked;
        this.description = description;
    }

    public Submission(int studentId, int questId, String description) {
        this.studentId = studentId;
        this.questId = questId;
        this.isMarked = false;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", questId=" + questId +
                ", isMarked=" + isMarked +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
