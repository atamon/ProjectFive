/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads stored settings from text-file and converts it into a HashMap.
 *
 * @author Anton Lindgren
 */
public abstract class SettingsLoader {

    private static final String SEPARATOR = ":";
    private static final String COMMENT = "//";

    public static Map<String, Integer> readSettings(String path) {
        // Construct readers
        BufferedReader reader = null;
        try {
            FileReader inFile = new FileReader(path);
            reader = new BufferedReader(inFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("ERROR: No such file: " + path);
        } catch (IOException e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException eInner) {
                    System.out.println("WARNING: Failed to close reader in SettingsLoader");
                    System.exit(1);
                }
            }
            throw new IllegalArgumentException("ERROR: SettingsLoader.readSettings() was unable to read file: "
                    + path);
        }

        // Read stream into map.
        return readToMap(reader);
    }
    
    private static Map<String, Integer> readToMap(BufferedReader reader) {
                Map<String, Integer> map = new HashMap<String, Integer>();
        String line;
        // Find start of default settings
        try {
            do {
                line = reader.readLine();
                // Found setting
                if (line != null && line.contains(SEPARATOR) && !line.contains(COMMENT)) {
                    String name = findSettingName(line);
                    int value = findSettingValue(line);
                    map.put(name, value);
                }
            } while (line != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("WARNING: Failed to close reader after reading settings!");
            }
        }
        return map;
    }
    
    private static String findSettingName(String setting) {
        int colonIndex = setting.indexOf(":");
        String name = setting.substring(0, colonIndex);
        // Try to validate the heck out of this string
        // TODO
        
        return name;
    }
    
    private static int findSettingValue(String setting) {
        int colonIndex = setting.indexOf(":");
        String valueString = setting.substring(colonIndex+1);
        int value;
        try {
            value = Integer.parseInt(valueString);
            return value;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not load setting " + setting
                    + "due to invalid valueformat");
            System.exit(1);
        }
        throw new RuntimeException("Failed to return value of setting " + setting
                + ", reached end of findSettingValue()");
    }
}
