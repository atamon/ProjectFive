/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Game;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.jarnbjo.vorbis.Util;
import java.util.List;

/**
 *
 * @author atamon
 */
public class View {

    private final SimpleApplication jme3;
    private final Game game;
    private AssetManager assetManager;
    private Node rootNode;
    private Node guiNode;
    private List<GraphicalUnit> graphicalUnits;

    public View(SimpleApplication jme3, Game game) {
        this.jme3 = jme3;
        this.game = game;
        this.assetManager = jme3.getAssetManager();
        this.rootNode = jme3.getRootNode();
        this.guiNode = jme3.getGuiNode();
    }

    /**
     * Sets up the graphical world.
     * Very simple, only adds a blue plane, lighting and a gUint.
     */
    public void createScene() {
        initGround(new Vector3f(20f, 1f, 20f));
        initCamera();
        initLighting();
        initUnit();
    }

    private void initGround(Vector3f vector) {
        Box ground = new Box(Vector3f.ZERO,
                vector.getX(), vector.getY(), vector.getZ());
        Geometry groundGeometry = new Geometry("Box", ground);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        groundGeometry.setMaterial(mat);

        rootNode.attachChild(groundGeometry);
        groundGeometry.setLocalTranslation(0, -5, 0);

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
        GraphicalUnit gUnit = new GraphicalUnit(1,
                                                ColorRGBA.randomColor(),
                                                new Vector3f(size, size, size),
                                                assetManager);
        this.game.addUnitListener(1, gUnit);
        rootNode.attachChild(gUnit.getGeometry());
    }
}
