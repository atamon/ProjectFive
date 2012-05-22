package observable;

import java.beans.PropertyChangeListener;

/**
 * Interface for observable classes.
 */
public interface IObservable {
    
    public void addPropertyChangeListener(PropertyChangeListener ls);

    public void removePropertyChangeListener(PropertyChangeListener ls);

}
