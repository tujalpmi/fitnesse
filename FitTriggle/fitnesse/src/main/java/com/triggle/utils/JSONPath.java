
package com.triggle.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import java.io.IOException;
//import net.sf.json.JSONArray;
//import net.minidev.json.JSONArray;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;




/**
 *
 * @author jose.alvarez
 */
public class JSONPath {

    public JSONPath() {
    }

    public static String executeJsonPath(String expression, String json) {

        String path = null;
        String id = null;
        JSONObject objectMatch = null;
        try {

            if (expression.contains("==")) {

                JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(json);
                JSONArray arr = jsonRS.getJSONArray("data");
                JSONObject jsonError = arr.getJSONObject(0);
                id = expression.substring(16, 22);
                int idok = Integer.parseInt(id);
                expression = "$." + expression.substring(25);
                
                for (int n = 0; n < arr.size(); n++) {

                    JSONObject object = arr.getJSONObject(n);
                    //System.out.println(object.getInt("id"));

                    if (idok == object.getInt("id")) {
                        objectMatch = arr.getJSONObject(n);
                    }

                }

                String queriedJson = JsonPath.<String>read(objectMatch, expression);
                path = queriedJson;


            } else if (expression.contains("id")) {

                int queriedJson = JsonPath.<Integer>read(json, expression);
                path = Integer.toString(queriedJson);

            } else if (expression.contains("successful")) {

                boolean queriedJson = JsonPath.<Boolean>read(json, expression);
                path = Boolean.toString(queriedJson);

            } else {

                String queriedJson = JsonPath.<String>read(json, expression);
                path = queriedJson;
            }

        } catch (PathNotFoundException e) {

            return "no result";

        } /**catch (IOException e) {

            return "no result";

        }**/

        return path;
    }


}
