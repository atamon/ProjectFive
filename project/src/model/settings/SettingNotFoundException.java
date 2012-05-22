/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
