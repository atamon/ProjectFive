/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.Vector;
import util.Util;

/**
 * A class to hold a Mesh.
 * @author Victor Lindhé
 */
public class GraphicalUnit implements PropertyChangeListener {
    private Geometry box;
    private int playerID;
    
    public GraphicalUnit(int playerID, ColorRGBA color, Vector3f size, 
                                                    AssetManager assetManager) {
        this.playerID = playerID;
        Box box = new Box(new Vector3f(1,1,1), 1,1,2);
        this.box = new Geometry("Box", box);
        Material boxMat = new Material(assetManager, 
                                           "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", color);
        this.box.setMaterial(boxMat);
    }
    
    public Geometry getGeometry() {
        return this.box;
    }
    
    public Vector3f getLocation() {
        return this.box.getLocalTranslation();
    }
    
    public void updateLocation(Vector3f vector) {
        this.box.setLocalTranslation(vector);
    }
    
    public int getPlayerID() {
        return this.playerID;
    }
    
    public boolean isPlayer(int playerID) {
        return this.playerID == playerID;
    }

    public void propertyChange(PropertyChangeEvent pce) {
        Vector3f newVector = Util.convertToMonkey3D((Vector)pce.getNewValue());
        this.box.setLocalTranslation(newVector);
    }
}
