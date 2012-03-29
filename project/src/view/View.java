/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;
import model.IGame;
import util.Util;

/**
 *
 * @author atamon
 * @modified johnhu
 */
public class View {

    private final SimpleApplication jme3;
    private final IGame game;
    private AssetManager assetManager;
    private Node rootNode;
    private Node guiNode;
    private List<GraphicalUnit> graphicalUnits;

    public View(SimpleApplication jme3, IGame game) {
        this.jme3 = jme3;
        this.game = game;
        this.graphicalUnits = new ArrayList();
        this.assetManager = jme3.getAssetManager();
        this.rootNode = jme3.getRootNode();
        this.guiNode = jme3.getGuiNode();
    }

    /**
     * Sets up the graphical world.
     * Very simple, only adds a blue plane, lighting and a gUint.
     */
    public void createScene() {
        Vector3f v = Util.convertToMonkey3D(this.game.getBattlefieldSize()).setY(1f);
        initGround(v);
        initCamera();
        initLighting();
        initUnit();
    }

    private void initGround(Vector3f vector) {
        GraphicalBattlefield geoBattlefield = new GraphicalBattlefield(vector, assetManager);
        
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
     */
    public void initUnit() {
        float size = 1.0f;
        int nmbOfPlayers = this.game.getNbrOfPlayers();
        for(int i = 0; i < nmbOfPlayers; i++) {
            graphicalUnits.add(i, new GraphicalUnit(i,
                                                ColorRGBA.randomColor(),
                                                new Vector3f(size, size, size),
                                                assetManager));
            
            this.game.addUnitListener(i, graphicalUnits.get(i));
            rootNode.attachChild(graphicalUnits.get(i).getGeometry());
        }
    }
    
}
