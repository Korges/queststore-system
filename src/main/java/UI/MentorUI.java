package UI;

import java.util.Arrays;
import java.util.List;

public class MentorUI extends UI {

    public static String mainMenuLabel = "MENTOR - Main Menu";

    public static String studentMenuLabel = "MENTOR - Student Menu";

    public static String artifactMenuLabel = "MENTOR - Artifact Menu";

    public static String questMenuLabel = "MENTOR - Quest Menu";

    public static String fundraiseMenuLabel = "MENTOR - Fundraise Menu";

    public static List<String> mainMenuOptions = Arrays.asList(
        "Student Panel",
        "Artifact Panel",
        "Quest Panel",
        "See student wallet",
        "Fundraise Panel",
        "EXIT");

    public static List<String> studentMenuOptions = Arrays.asList(
        "Create Student",
        "Edit Student",
        "Delete Student",
        "List All Students",
        "EXIT");

    public static List<String> artifactMenuOptions = Arrays.asList(
        "Create Artifact",
        "Edit Artifact",
        "Delete Artifact",
        "List All Artifacts",
        "EXIT");

    public static List<String> questMenuOptions = Arrays.asList(
        "Create Quest",
        "Edit Quest",
        "Mark Quest",
        "EXIT");

    public static List<String> fundraiseMenuOptions = Arrays.asList(
        "Check Existing Fundraises",
        "List Fundraise Members",
        "Execute Existing Fundraise",
        "Delete Existing Fundraise",
        "EXIT");




}
