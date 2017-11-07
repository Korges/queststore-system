package DAO;

import java.util.Map;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class WebTemplate {

    public static String getSiteContent() {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/admin-page.html");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }
}