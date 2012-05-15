/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Singleton Status Box that can show information about what's happening in the game
 * @author jnes
 */
public final class StatusBox  {

    private static StatusBox instance;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    // Circular. No more than the five last messages needs to exist
    private final Map<Color, String> messages = new LinkedHashMap<Color, String>(){
        protected boolean removeElderestEntry(Map.Entry elderest){
            return this.size() > 5;
        }
    };
    
    public static final Color STATUS_MESSAGE_COLOR = Color.GRAY;
    
    private StatusBox() {}
    
    public static synchronized StatusBox getInstance(){
        if (instance == null) {
            instance = new StatusBox();
        }
        return instance;
    }

    public void message(final String message){
        this.message(STATUS_MESSAGE_COLOR, message);
    }
    
    public void message(final Color messageColor, final String message){
        if (message != null) {
            this.messages.put(messageColor, message);
            this.pcs.firePropertyChange("StatusBox Message", null, this.messages);
        }
    }
    public void clear() {
        this.messages.clear();
        this.pcs.firePropertyChange("StatusBox Cleared", null, this.messages);
    }
    
    public void setVisible(final boolean visible) {
        if (visible) {
            this.pcs.firePropertyChange("StatusBox Visible", null, null);
        } else {
            this.pcs.firePropertyChange("StatusBox Hidden", null, null);
        }
    }

    public void addPropertyChangeListener(final PropertyChangeListener ls) {
        pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(final PropertyChangeListener ls) {
        pcs.removePropertyChangeListener(ls);
    }

    public Map<Color, String> getMessages() {
        return this.messages;
    }

}
