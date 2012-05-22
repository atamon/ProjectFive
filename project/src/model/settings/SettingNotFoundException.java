package model.settings;

import java.util.NoSuchElementException;

/**
 *  Exception to be thrown if settings file not found.
 **/
public class SettingNotFoundException extends NoSuchElementException {
    
    public SettingNotFoundException(String message) {
        super(message);
    }
}
