package DAO;

import java.util.ArrayList;
import java.util.Map;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class WebTemplate {

    public static String getSiteContent(String URL) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(URL);
        JtwigModel model = JtwigModel.newModel();


        return template.render(model);
    }
}
