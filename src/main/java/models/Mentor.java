package models;

public class Mentor extends User {



    public Mentor(Integer id, String firstName, String lastName, String email, String password, String klass) {

        super(firstName, lastName, email, password, klass);
        this.id = id;

    }

    public Mentor(String firstName, String lastName, String email, String password, String klass) {

        super(firstName, lastName, email, password, klass);

    }


 }
