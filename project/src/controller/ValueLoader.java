/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author johnhu
 */
public abstract class ValueLoader {
    
    private static final String SEPARATOR = ":";
    private static final String COMMENT = "//";

    public static Map<String, Integer> readValues(String path, String heading) {
        String startAt = "[" + heading + "]";
        String stopAt = "[/" + heading + "]";
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
        Map<String, Integer> map = new HashMap<String, Integer>();
        String line = "";
        // Find start of default settings
        try {
            do {
                line = reader.readLine();
            } while (!line.contains(startAt)); //If startAt doesn't exist exception will be thrown automatically
            while (!line.contains(stopAt)) {
                line = reader.readLine();
                String name = findValueName(line);
                int value = findValue(line);
                map.put(name, value);
            }
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
    
    private static String findValueName(String setting) {
        int colonIndex = setting.indexOf(":");
        String name = setting.substring(0, colonIndex);
        // Try to validate the heck out of this string
        // TODO

        return name;
    }
    
    private static int findValue(String setting) {
        int colonIndex = setting.indexOf(":");
        String valueString = setting.substring(colonIndex + 1);
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
