package models;

public class Admin extends User {
    public Admin(Integer id, String firstName, String lastName, String email, String password, String klass) {
        super(id, firstName, lastName, email, password, klass);
    }

    public Admin(String firstName, String lastName, String email, String password, String klass) {

        super(firstName, lastName, email, password, klass);
    }
}
