package DAO;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WebTemplateDao {

    public String getSiteTemplate(String templatePath){
        StringBuilder result = new StringBuilder("");

        ClassLoader cl = getClass().getClassLoader();
        File file = new File(cl.getResource(templatePath).getFile());

        try(Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}