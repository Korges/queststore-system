import controller.MainController;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException{
        MainController controller = new MainController();
        controller.loginToSystem();


    }
}