package models;

public class Student extends User {

    public Wallet wallet;

    public Student(String firstName, String lastName, String email, String password, String klass) {

        super(firstName, lastName, email, password, klass);
    }

    public Student(Integer id, String firstName, String lastName, String email, String password, String klass) {

        super(id,firstName, lastName, email, password, klass);

    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}