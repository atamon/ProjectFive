/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author victorlindhe
 */
public abstract class GraphicalAbstract {
    protected Node node;
    
    protected void setNode(Node node) {
        this.node = node;
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
}
