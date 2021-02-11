package com.nesti.stock_manager.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Settings manager: static methods to get and set key-value pairs from an SQLite database file.
 *
 */
/**
 * @author Emmanuelle Gay, Erik Shea
 *
 */
public class ApplicationSettings {
	private static Map<String, String> settings;

	/**
	 * retrieve value in settings
	 * 
	 * @param key
	 * @return value corresponding to key. if doesn't exist, return default value
	 *         (or null if it in turn doesn't exist)
	 */
	public static String get(String key) {
		if (settings == null) {
			readFile();
		}

		return settings.get(key);
	}

	/**
	 * Set a value in settings with a given key
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		settings.put(key, value);
		writeFile();
	}

	private static void readFile()  {
		settings = new HashMap<>();

		try {
			var jsonParser = new JSONParser();
			var reader = new FileReader("nesti_settings.json");
			var obj = jsonParser.parse(reader);
			var jsonSettings = (JSONObject) obj;

			for (var key : jsonSettings.keySet()) {
				settings.put((String) key, (String) jsonSettings.get(key));
			}
		} catch (IOException | ParseException  e) {
			writeFile();
		}

	}

	@SuppressWarnings("unchecked")
	private static void writeFile() {
		JSONObject json = new JSONObject();
		json.putAll(settings);

		// WriteJsonFile
		try (FileWriter file = new FileWriter("nesti_settings.json")) {
			file.write(json.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
