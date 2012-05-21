/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import observable.IObservable;

/**
 * Singleton Status Box that can show information about what's happening in the game
 * @author jnes
 */
public final class StatusBox implements IObservable  {
    public static final Color STATUS_MESSAGE_COLOR = Color.GRAY;
    public class Message {
        private String message;
        private Color color;
        
        private Message(String message){
            this(STATUS_MESSAGE_COLOR, message);
        }
        
        private Message(Color messageColor, String message){
            this.color = messageColor;
            this.message = message;
            
        }
        
        public String getMessage(){
            return message;
        }
        public Color getColor(){
            return color;
        }
    }
    private static StatusBox instance;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    private List<Message> messages = new ArrayList<Message>();
    
    private StatusBox() {}
    
    public static synchronized StatusBox getInstance(){
        if (instance == null) {
            instance = new StatusBox();
        }
        return instance;
    }

    public void message(final String message){
        message(STATUS_MESSAGE_COLOR, message);
    }
    
    public void message(final Color messageColor, final String message){
        if (message != null) {
            messages.add(new Message(messageColor, message));
            pcs.firePropertyChange("StatusBox Message", null, this.messages);
        }
    }
    public void clear() {
        messages.clear();
        pcs.firePropertyChange("StatusBox Cleared", null, this.messages);
    }
    
    public void setVisible(final boolean visible) {
        if (visible) {
            pcs.firePropertyChange("StatusBox Visible", null, null);
        } else {
            pcs.firePropertyChange("StatusBox Hidden", null, null);
        }
    }

    public void addPropertyChangeListener(final PropertyChangeListener ls) {
        pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(final PropertyChangeListener ls) {
        pcs.removePropertyChangeListener(ls);
    }

    public List<Message> getMessages() {
        return messages;
    }

}
