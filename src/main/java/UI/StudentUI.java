package UI;
import java.util.Arrays;
import java.util.List;

public class StudentUI extends UI {

    public static String mainMenuLabel = "STUDENT - Main Menu";

    public static String artifactMenuLabel = "STUDENT - Artifact Menu";

    public static String fundraiseMenuLabel = "STUDENT - Fundraise Menu";


    public static List<String> menuMainOptions = Arrays.asList(
        "Artifact Panel",
        "Experience",
        "EXIT");

    public static List<String> menuArtifactOptions = Arrays.asList(
            "List All Artifacts",
            "Buy Artifact",
            "Fundraise",
            "Check Balance",
            "Check Purchase History",
            "EXIT");

    public static List<String> menuFundraiseOptions = Arrays.asList(
            "Create Fundraise",
            "Join Existing Fundraise",
            "EXIT");


}
