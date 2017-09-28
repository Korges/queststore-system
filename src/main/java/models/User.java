package models;

public abstract class User {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    Integer id;
    private String klass;



    User(String firstName, String lastName, String email, String password, String klass) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.klass = klass;

    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public Integer getID() {
        return id;
    }

    public String getEmail() { return email; }

    public String getKlass() {
        return klass;
    }

    public String getPassword() { return password; }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName) { this.lastName = lastName;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    protected boolean checkPassword(String password) {

        if (password != null){ // that allows us to avoid NullExceptionError line in 28

            if (this.password.equals(password)) {
                return true;
            }
            else {
                return false;
            }
         }
        return false;
    }

    public String toString(){

        String view = String.format("ID: %s, Name: %s, Email: %s", this.getID(), this.getFullName(), this.getEmail());
        return view;
    }
}
