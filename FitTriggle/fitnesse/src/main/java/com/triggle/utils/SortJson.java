
package com.triggle.utils;

/**
 *
 * @author jose.alvarez

*/


import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Lf2SpacesIndenter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;


 
public class SortJson {

    public SortJson (){}
    
        public static String sort(String in) throws IOException,
                JsonParseException, JsonProcessingException {
            JsonFactory factory = new JsonFactory();
            factory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
            factory.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            JsonParser jp = factory.createParser(in);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode tree = mapper.readTree(jp);
            sort(tree);

            DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
            pp.indentArraysWith(new Lf2SpacesIndenter());
            String result = mapper.writer(pp).writeValueAsString(tree);
            return result;
        }

	public static void sort(JsonNode tree) {

		if (tree.isObject()) {
			sortObject(tree);
		} else if (tree.isArray()) {
			sortArray(tree);
		}

	}

	public static void sortArray(JsonNode tree) {

		for (JsonNode jsonNode : tree) {
			sort(jsonNode);
		}

		List<JsonNode> list = Lists.newArrayList(((ArrayNode) tree).iterator());
		Collections.sort(list, new JsonNodeComparator());
		((ArrayNode) tree).removeAll();
		((ArrayNode) tree).addAll(list);
	}

	public static void sortObject(JsonNode tree) {
		List<String> asList = Lists.newArrayList(tree.fieldNames());
		Collections.sort(asList);
		LinkedHashMap<String, JsonNode> map = new LinkedHashMap<String, JsonNode>();
		for (String f : asList) {

			JsonNode value = tree.get(f);
			sort(value);
			map.put(f, value);
		}
		((ObjectNode) tree).removeAll();
		((ObjectNode) tree).setAll(map);
	}

	public static class JsonNodeComparator implements Comparator<JsonNode> {
		public int compare(JsonNode o1, JsonNode o2) {
			if (o1 == null && o2 == null) {
				return 0;
			}

			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}

			if (o1.isObject() && o2.isObject()) {
				return compObject(o1, o2);
			} else if (o1.isArray() && o2.isArray()) {
				return compArray(o1, o2);
			} else if (o1.isValueNode() && o2.isValueNode()) {
				return compValue(o1, o2);
			} else {
				return 1;
			}
		}

		private int compValue(JsonNode o1, JsonNode o2) {

			if (o1.isNull()) {
				return -1;
			}

			if (o2.isNull()) {
				return 1;
			}

			if (o1.isNumber() && o2.isNumber()) {
				return o1.bigIntegerValue().compareTo(o2.bigIntegerValue());
			}

			return o1.asText().compareTo(o2.asText());
		}

		private int compArray(JsonNode o1, JsonNode o2) {

			int c = ((ArrayNode) o1).size() - ((ArrayNode) o2).size();
			if (c != 0) {
				return c;
			}
			for (int i = 0; i < ((ArrayNode) o1).size(); i++) {
				c = compare(o1.get(i), o2.get(i));
				if (c != 0) {
					return c;
				}
			}

			return 0;
		}

		private int compObject(JsonNode o1, JsonNode o2) {

			String id1 = o1.get("id") == null ? null : o1.get("id").asText();
			String id2 = o2.get("id") == null ? null : o2.get("id").asText();
			if (id1 != null) {
				int c = id1.compareTo(id2);
				if (c != 0) {
					return c;
				}
			}
			int c = ((ObjectNode) o1).size() - ((ObjectNode) o2).size();
			if (c != 0) {
				return c;
			}

			Iterator<String> fieldNames1 = ((ObjectNode) o1).fieldNames();
			Iterator<String> fieldNames2 = ((ObjectNode) o2).fieldNames();
			for (; fieldNames1.hasNext();) {
				String f = fieldNames1.next();

				c = f.compareTo(fieldNames2.next());
				if (c != 0) {
					return c;
				}

				JsonNode n1 = o1.get(f);
				JsonNode n2 = o2.get(f);
				c = compare(n1, n2);
				if (c != 0) {
					return c;
				}
			}
			return 0;
		}
	}
}