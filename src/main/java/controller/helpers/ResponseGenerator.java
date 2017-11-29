package controller.helpers;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ResponseGenerator {
    public static String generateSubmitResponse(String controller, Integer option) {
        String responseTemplatePath = "templates/";
        if (option == 1) {
            responseTemplatePath += "success.twig";
        } else if (option == 2) {
             responseTemplatePath += "error.twig";
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate(responseTemplatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("controller", controller);
        String response = template.render(model);

        return response;
    }

    public static String generateModelResponse(Object object, String alias, String URL) {
        try {
            JtwigTemplate template = JtwigTemplate.classpathTemplate(URL);
            JtwigModel model = JtwigModel.newModel();
            model.with(alias, object);
            return template.render(model);
        }catch (Exception e){
            System.out.println(e);
        }
        return "a";
    }

    public static String generateModelResponse(Object object, String alias, Object object2, String alias2, String URL) {
        try {
            JtwigTemplate template = JtwigTemplate.classpathTemplate(URL);
            JtwigModel model = JtwigModel.newModel();
            model.with(alias, object);
            model.with(alias2, object2);
            return template.render(model);
        }catch (Exception e){
            System.out.println(e);
        }
        return "a";
    }

    public static String generateModelResponse(String URL){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(URL);
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

}
