/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;

/**
 *
 * @author jnes
 */
public interface IObservable {
    
    public void addPropertyChangeListener(PropertyChangeListener ls);

    public void removePropertyChangeListener(PropertyChangeListener ls);

}
