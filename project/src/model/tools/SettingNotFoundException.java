/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tools;

import java.util.NoSuchElementException;

/**
 *
 * @author CaptainAwesome
 */
public class SettingNotFoundException extends NoSuchElementException {
    
    public SettingNotFoundException(String message) {
        super(message);
    }
}
