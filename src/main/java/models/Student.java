package models;

public class Student extends User {

    public Wallet wallet;

    public Student(String firstName, String lastName, String email, String password, String klass) {

        super(firstName, lastName, email, password, klass);
        wallet = new Wallet();
    }
}