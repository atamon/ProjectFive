package view;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * Class to represent a graphical molotov cocktail.
 */
public class GraphicalMolotov extends GraphicalBottle {
    
    public GraphicalMolotov(Vector3f size, Node node) {
        super(size, node);
        
        // No need to perform checks since we can control the blends that get loaded
        // This order is always correct
        Material mat = ((Geometry)((Node) node.getChild(0)).getChild(0)).getMaterial();

        mat.setColor("Ambient", ((ColorRGBA) mat.getParam("Ambient").getValue()).mult(ColorRGBA.Red.mult(3)));
    }
}
