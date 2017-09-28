package UI;

import java.util.Arrays;
import java.util.List;

public class MentorUI extends UI {

    public static List<String> menuOptions = Arrays.asList(
        "List all students",
        "Create Student",
        "Create Quest",
        "Edit Quest",
        "Create Artifact",
        "Edit Artifact",
        "Mark Submission",
        "Mark Artifact",
        "Invigilate Student",
        "EXIT");

    public static String[] optionsArray = new String[menuOptions.size()];
    public static List<String> optionsList;

    private static void setOptionsList() {
        int size = optionsArray.length;

        for(int i=0; i<menuOptions.size()-1; i++) {
            optionsArray[i] = Integer.toString(i+1);

        }
        optionsArray[size-1] = "0";
        optionsList = Arrays.asList(optionsArray);
    }

    public static void printMenu(){
        setOptionsList();
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

}
