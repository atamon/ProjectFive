package view;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.beans.PropertyChangeListener;

/**
 * A class to represent a graphical bottle.
 */
public class GraphicalBottle extends GraphicalAbstract 
                                            implements PropertyChangeListener {

    public GraphicalBottle(Vector3f pos,
                         Node blenderModel) {

        node = blenderModel;
        updatePosition(pos);
    }    
}
