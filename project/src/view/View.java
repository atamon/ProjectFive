/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import util.BlenderImporter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import model.IGame;
import model.Player;
import model.tools.Vector;
import util.Util;
import model.visual.Unit;

/**
 *
 * @author atamon
 * @modified johnhu
 */
public class View implements PropertyChangeListener {
    
    public static final String BLEND_PATH = "Blends/P5Ship_export.blend";
    
    private final Node blenderUnit;
    
    private final IGame game;
    private final SimpleApplication jme3;
    
    private AssetManager assetManager;
    private Node rootNode;
    private Node guiNode;

    public View(SimpleApplication jme3, IGame game) {
        this.jme3 = jme3;
        this.game = game;
        this.assetManager = jme3.getAssetManager();
        this.rootNode = jme3.getRootNode();
        this.guiNode = jme3.getGuiNode();

        // Register a BlenderLoader with out assetManager so it supports .blend
        BlenderImporter.registerBlender(assetManager);
        blenderUnit = BlenderImporter.loadModel(assetManager, BLEND_PATH);
    }

    /**
     * Sets up the graphical world.
     * Very simple, only adds a blue plane, lighting and a gUint.
     */
    public void createScene() {
        
        initGround(this.game.getBattlefieldSize(), this.game.getBattlefieldPosition());
        initCamera();
        initLighting();
    }

    private void initGround(float size, Vector pos) {
        GraphicalBattlefield geoBattlefield = new GraphicalBattlefield(size, Util.convertToMonkey3D(pos), assetManager);

        rootNode.attachChild(geoBattlefield.getGeometry());


//        RigidBodyControl groundPhysics = new RigidBodyControl(0.0f);
//        groundGeometry.addControl(groundPhysics);
//        bulletAppState.getPhysicsSpace().add(groundPhysics);
    }

    private void initLighting() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, 1));
        sun.setColor(ColorRGBA.Yellow);
        rootNode.addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }

    private void initCamera() {
        Camera cam = jme3.getCamera();
        cam.setLocation(new Vector3f(-40, 15, -40));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    /**
     * Places the unit in the graphical world.
     * @param playerID
     * @param pos
     * @param dir
     * @param color 
     */
    public void createGraphicalUnit(int playerID, Vector pos, Vector dir, float size, ColorRGBA color) {
        // To be replaced with argument from model
        Vector3f gPos = Util.convertToMonkey3D(pos);
        Vector3f gDir = Util.convertToMonkey3D(dir);
        
        // Create the graphicalUnit-object
        GraphicalUnit gUnit = new GraphicalUnit(color,
                                                gPos,
                                                gDir,
                                                size,
                                                assetManager,
                                                blenderUnit.clone(true));
        // Set it to start listening to its unit
        this.game.addUnitListener(playerID, gUnit);
        
        // Attach it
        rootNode.attachChild(gUnit.getNode());
    }

    public void propertyChange(PropertyChangeEvent pce) {
        // If a unit was created we make sure we got a unit with it
        if ("Player Created".equals(pce.getPropertyName())) {
            if (pce.getNewValue().getClass() == Player.class
                    && pce.getOldValue() == null) {
                
                // Salvage what gUnit needs to set itself up from player
                Player player = (Player) pce.getNewValue();
                Unit unit = player.getUnit();
                
                Vector pos = unit.getPosition();
                Vector dir = unit.getDirection();
                float size = unit.getSize();
                // Also send playerID so it knows which unit to listen to.
                int playerID = player.getId();
                
                this.createGraphicalUnit(playerID, pos, dir, size, ColorRGBA.randomColor());
            
            } else {
                throw new RuntimeException(
                        "Unit Created-event sent without correct parameters");
            }

        }
    }
}
