package UI;

import java.util.Arrays;
import java.util.List;

public class MentorUI extends UI {

    public static List<String> menuMainOptions = Arrays.asList(
        "Student Panel",
        "Artifact Panel",
        "Quest Panel",
        "EXIT");

    public static List<String> menuStudentOptions = Arrays.asList(
        "Create Student",
        "Edit Student",
        "Delete Student",
        "List All Students",
        "EXIT");

    public static List<String> menuArtifactOptions = Arrays.asList(
        "Create Artifact",
        "Edit Artifact",
        "Delete Artifact",
        "List All Artifacts",
        "EXIT");

    public static List<String> menuQuestOptions = Arrays.asList(
        "EXIT");



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

}
