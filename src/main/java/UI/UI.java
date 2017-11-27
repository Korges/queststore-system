package UI;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class UI {

    private static Scanner sc = new Scanner(System.in);

    public static String getLogin(){

        String login = getString("Enter login: ");

        return login;
    }

    public static String getPassword(){

        final String password, message = "Enter password";

        if( System.console() == null ) {
            final JPasswordField pf = new JPasswordField();
            password = JOptionPane.showConfirmDialog( null, pf, message,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION ?
                    new String( pf.getPassword() ) : "";
        }
        else
            password = new String( System.console().readPassword( "%s> ", message ) );

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

    public static<T> void printList(ArrayList<T> list) {

        if (list.size() < 1) {
            System.out.println("List is empty.");
        }


        for (T object : list ) {

            System.out.println(object.toString());
        }
    }

    public static List<String> optionsList;

    private static void setOptionsList(List menuOptions) {

        String[] optionsArray = new String[menuOptions.size()];
        int size = optionsArray.length;

        for(int i=0; i<menuOptions.size()-1; i++) {
            optionsArray[i] = Integer.toString(i+1);

        }
        optionsArray[size-1] = "0";
        optionsList = Arrays.asList(optionsArray);
    }

    public static void printMenu(List menuOptions){

        setOptionsList(menuOptions);
        for(int i=0; i<menuOptions.size(); i++) {
            System.out.format("%s - %s\n", optionsList.get(i), menuOptions.get(i));
        }
    }


    public static String getChoice(){

        String choice;
        do{
            choice = getString("Choose option: ");
        }while (!optionsList.contains(choice));

        return choice;
    }

    public static void printLabel(String label) {

        System.out.println("*** " + label + " ***");
    }

    public static String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        return date.toString();

    }

}
