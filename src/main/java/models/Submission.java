package models;

public class Submission {

    private boolean isMarked;
    private Student student;
    private Quest quest;

    public Submission(Student student, Quest quest) {

        this.isMarked = false;
        this.student = student;
        this.quest = quest;
    }


    public boolean setMarked() {

        if (!isMarked) {

            System.out.println("ENTER GRADE, (CHANGE IT TO OUTSIDE CLASS");
            System.out.println("(INITIALIZE GIB MONEY TO STUDENT PLS)");
            isMarked = true;

            return true;

        } else {
            return false;
        }
    }
}
