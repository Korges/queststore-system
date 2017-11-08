package controller.Mentor;

import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CreateStudent implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = WebTemplate.getSiteContent("templates/mentor/create-student.twig");
        }//todo
//        if (method.equals("POST")) {
//            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
//                    "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            String formData = br.readLine();
//            Map<String,String> form = ParseForm.parseFormData(formData);
//            try {
//                AdminController adminController = new AdminController();
//                adminController.createGroup(form.get("group-name"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            response =  WebTemplate.getSiteContent("templates/success.twig");
//        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
