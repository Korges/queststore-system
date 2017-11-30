package controller.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParseForm {

    public static Map<String,String> parseFormData(String formData) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    public static Map<String, String> parsePost(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader;
        Map<String, String> inputs = null;
        try {
            inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String formData = br.readLine();
            inputs = ParseForm.parseFormData(formData);
        } catch (UnsupportedEncodingException e) {
            return inputs;
        }
        return inputs;
    }
}
