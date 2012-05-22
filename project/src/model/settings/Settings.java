/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.settings;

import java.util.Map;

/**
 *  Singleton class that imports settings.
 **/
public class Settings {
    
    private Map<String, Integer> settings;
    private static Settings instance;
    
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
    
    public void loadSettings(Map<String, Integer> loadedSettings) {
        settings = loadedSettings;
    }
    
    public int getSetting(String param) {
        Integer value = settings.get(param);
        if (value == null) {
            throw new SettingNotFoundException("ERROR:" + param
                    + "is not a valid setting. Love <3");
        }
        return value;
    }
}
