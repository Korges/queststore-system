package controller;

import java.io.*;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import DAO.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.ParseForm;
import controller.helpers.Sessions;
import models.Mentor;
import models.Group;

public class AdminHandler  implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionId = getSessionIdFromCookie(httpExchange);

        if (method.equals("GET") && Sessions.checkSession(sessionId,"Admin")) {
            response = getResponse(path);
        }
        else if (method.equals("POST")){
            handlePost(path,httpExchange);
            response = getResponse(path);
        }
        else{
            Sessions.redirect(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    public String getResponse(String path){
        String response = "";
        if(path.equals("/admin")){
            response = WebTemplate.getSiteContent("templates/admin/admin-menu.twig");
        }
        if(path.equals("/admin/create-group")){
            response = WebTemplate.getSiteContent("templates/admin/create-group.twig");
        }
        if (path.equals("/admin/create-mentor")){
            response = WebTemplate.getSiteContent("templates/admin/create-mentor.twig");
        }
        if (path.equals("/admin/edit-mentor")){
            response = WebTemplate.getSiteContent("templates/admin/edit-mentor.twig");
        }
        return response;
    }

    public void handlePost(String path,HttpExchange httpExchange) throws IOException {
        if(path.equals("/admin/create-mentor")){
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(httpExchange.getRequestBody(),
                        "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String,String> inputs = ParseForm.parseFormData(formData);
                System.out.printf(inputs.get("login"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSessionIdFromCookie(HttpExchange httpExchange) throws IOException {
        String sessionIDFull = "";
        try {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            String[] sessionID = cookieStr.split("sessionId=");
            sessionIDFull= sessionID[1].replace("\"", "");
        }catch (NullPointerException e){
            Sessions.redirect(httpExchange);
        }
        return sessionIDFull;
    }




}
