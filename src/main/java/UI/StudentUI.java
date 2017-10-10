package UI;
import java.util.Arrays;
import java.util.List;

public class StudentUI extends UI {

    public static String mainMenuLabel = "STUDENT - Main Menu";

    public static String artifactMenuLabel = "STUDENT - Artifact Menu";

    public static String fundraiseMenuLabel = "STUDENT - Fundraise Menu";

    public static String walletMenuLabel = "STUDENT - Wallet Menu";

    public static String submissionMenuLabel = "STUDENT - Submission Menu";


    public static List<String> mainMenuOptions = Arrays.asList(
    "Artifact Panel",
    "Wallet Panel",
    "Submission Panel",
    "EXIT");

    public static List<String> walletMenuOptions = Arrays.asList(
    "Show wallet status",
    "EXIT");

    public static List<String> artifactMenuOptions = Arrays.asList(
            "List All Artifacts",
            "Buy Artifact",
            "Fundraise",
            "Check Balance",
            "Check Purchase History",
            "EXIT");

    public static List<String> fundraiseMenuOptions = Arrays.asList(
            "Create Fundraise",
            "Join Existing Fundraise",
            "Leave Fundraise",
            "Show joined Fundraises",
            "Show all Fundraises",
            "EXIT");

    public static List<String> submitMenuOptions = Arrays.asList(
            "Show all Quests",
            "Show your Submissions",
            "Submit Quest",
            "EXIT");



}
