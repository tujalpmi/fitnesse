package com.triggle.utils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author jose.alvarez
 */
public class JsonCompare {

    private String expected = null;
    private String actual = null;

    public JsonCompare() {
    }

    public String compare() {

        String expected = this.getExpected();
        String actual = this.getActual();
        
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Gson gson = new Gson();

        Map<String, Object> categoryicons = gson.fromJson(expected, mapType);

        JsonParser parser = new JsonParser();
        Gson g = new GsonBuilder().setPrettyPrinting().create();

        String formatExpected = g.toJson(parser.parse(expected));
        String formatActual = g.toJson(parser.parse(actual));

        List<String> splitExpected = splitJson(formatExpected);
        List<String> splitActual = splitJson(formatActual);

        List<String> expectedForRemove = new ArrayList<>(splitExpected);
        List<String> actualForRemove = new ArrayList<>(splitActual);

        splitExpected.removeAll(actualForRemove);
        splitActual.removeAll(expectedForRemove);

        Map<String, String> expectedMap = getMapFromListEntries(splitExpected);
        Map<String, String> actualMap = getMapFromListEntries(splitActual);

        String diff = Maps.difference(expectedMap, actualMap).toString();

        if (diff.startsWith("not equal: ")) {
            diff = diff.replace("not equal: ", "JSONs are not equal\n");

            if (diff.contains("only on left")) {
                diff = diff.replace("only on left=", "\nonly in expected JSON:\n");
            }
            if (diff.contains("only on right")) {
                diff = diff.replace("only on right=", "\nonly in actual JSON:\n");
            }
            if (diff.contains(" value differences")) {
                diff = diff.replace(": value differences=", "\nvalue differences:\n");
            }
        } else {
            System.out.println("JSONs are equal");
        }

        return diff;
    }

    private static List<String> splitJson(String in) {
        return Arrays
                .stream(in.split("\n"))
                .map(line -> line.replace(",", "").trim())
                .collect(Collectors.toList());
    }

    private static Map<String, String> getMapFromListEntries(List<String> entries) {
        Map<String, String> map = new LinkedHashMap<>();
        for (String node : entries) {
            map.put(node.split(": ")[0], node.split(": ")[1]);
        }
        return map;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {

        this.expected = expected;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {

        this.actual = actual;
    }

}
