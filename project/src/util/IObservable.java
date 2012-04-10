/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.beans.PropertyChangeListener;

/**
 *
 * @author johannes wikner
 */
public interface IObservable {
    
    public void addPropertyChangeListener(PropertyChangeListener ls);

    public void removePropertyChangeListener(PropertyChangeListener ls);

}
