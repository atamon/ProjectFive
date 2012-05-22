/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author victorlindhe
 */
public abstract class GraphicalAbstract implements PropertyChangeListener {

    protected Node node;
    private Node parent;
    
    public void provideParent(Node parent) {
        this.parent = parent;
    }

    public Node getNode() {
        return node;
    }

    protected void updatePosition(Vector3f pos) {
        node.setLocalTranslation(pos);
    }

    protected void updateRotation(Quaternion rot) {
        node.setLocalRotation(rot);
    }

    public void propertyChange(PropertyChangeEvent pce) {
        if ("Physical Update".equals(pce.getPropertyName())) {
            updatePosition((Vector3f) pce.getOldValue());
            updateRotation((Quaternion) pce.getNewValue());
        }

        if ("Physical Rescaled".equals(pce.getPropertyName())) {
            node.setLocalScale((Vector3f) pce.getNewValue());
        }

        if ("Hide Moveable".equals(pce.getPropertyName())) {
            node.removeFromParent();
        }
        
        if ("Show Moveable".equals(pce.getPropertyName())) {
            parent.attachChild(node);
        }

        if ("Visual Removed".equals(pce.getPropertyName())) {
            node.removeFromParent();
        }
    }
}
