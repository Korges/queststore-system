package UI;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class UI {

    private static Scanner sc = new Scanner(System.in);

    public static String getLogin(){

        String login = getString("Enter login: ");

        return login;
    }

    public static String getPassword(){

        String password = getString("Enter password: ");

        return password;
    }

    public static String getEmail(){

        String email = getString("Enter email: ");

        return email;
    }


    public static void showMessage(String message){

        System.out.println(message);
    }

    public static String getString(String message){

       sc = new Scanner(System.in);
       showMessage(message);
       return sc.nextLine();
    }

    public static int getInteger(String message){

        do{
            sc = new Scanner(System.in);
            showMessage(message);
        } while(!sc.hasNextInt());
        return sc.nextInt();
    }

    public static boolean getBoolean(String message){

        List<String> messages = Arrays.asList("Y", "YES");
        sc = new Scanner(System.in);
        showMessage(message);
        if(messages.contains(sc.nextLine().toUpperCase())){
            return true;
        } else {
            return false;
        }
    }

}
