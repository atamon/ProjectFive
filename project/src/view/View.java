package view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.water.WaterFilter;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import model.GameState;
import model.IGame;
import model.player.Player;
import model.round.RoundState;
import math.Vector;
import model.visual.CannonBall;
import model.visual.Item;
import util.Util;
import model.visual.Unit;

/**
 *
 * @author Anton Lindgren @modified johnhu
 */
public class View implements PropertyChangeListener {

    public static final String BLEND_PATH = "Blends/P5Ship_export.blend";
    private final Map<Element, Unit> hpBars = new HashMap<Element, Unit>();
    private final Node blenderUnit;
    private final IGame game;
    private final SimpleApplication jme3;
    private AssetManager assetManager;
    private Node rootNode;
    private GameState lastGameState;
    private RoundState lastRoundState;

    private GUIController guiControl;
    
    public View(SimpleApplication jme3, IGame game,
            int windowWidth, int windowHeight, NiftyJmeDisplay niftyGUI) {

        this.jme3 = jme3;
        this.game = game;
        this.assetManager = jme3.getAssetManager();
        this.rootNode = jme3.getRootNode();

        // Create scene
        this.createScene();
        
        // Register a BlenderLoader with our assetManager so it supports .blend
        BlenderImporter.registerBlender(assetManager);

        blenderUnit = BlenderImporter.loadModel(assetManager, BLEND_PATH);
        
        // Create water effects
        FilterPostProcessor waterPostProcessor = new FilterPostProcessor(assetManager);
        WaterFilter water = new WaterFilter(rootNode, new Vector3f(0, -1, 1));
        water.setWaterHeight(4f);
        waterPostProcessor.addFilter(water);
        
                
        jme3.getViewPort().addProcessor(waterPostProcessor);        
        guiControl = new GUIController(niftyGUI, game, waterPostProcessor, jme3);
        
        // We need to fetch first gamestates right away
        lastGameState = game.getState();
        lastRoundState = game.getRoundState();
    }

    /**
     * Sets up the graphical world. Very simple, only adds a blue plane,
     * lighting and a gUint.
     */
    public void createScene() {
        initGround(this.game.getBattlefieldSize(), this.game.getBattlefieldPosition());
        initCamera();
        initLighting();
    }

    private void initGround(Vector size, Vector pos) {
        GraphicalBattlefield geoBattlefield = new GraphicalBattlefield(Util.convertToMonkey3D(size),
                Util.convertToMonkey3D(pos), assetManager);
        
        GraphicalBattlefield layerPlane = new GraphicalBattlefield(
                new Vector3f(size.getX()+100, 0.1f, size.getZ()), 
                new Vector3f(pos.getX()-50, 15f, pos.getZ()), assetManager);
      /*  Material newMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        newMat.setTexture("ColorMap", assetManager.loadTexture("lines.png"));
        newMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        layerPlane.getGeometry().setMaterial(newMat);
        layerPlane.getGeometry().setQueueBucket(Bucket.Transparent);
        rootNode.attachChild(layerPlane.getGeometry());*/
        rootNode.attachChild(geoBattlefield.getGeometry());
    }
    
    private void initLighting() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0, -1, 1));
        sun.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(sun);
        
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(ambient);
    }

    private void initCamera() {
        Camera cam = jme3.getCamera();
        cam.setLocation(new Vector3f(this.game.getBattlefieldCenter().getX(), 110, 0));
        cam.lookAt(Util.convertToMonkey3D(this.game.getBattlefieldCenter()).setZ(42), Vector3f.UNIT_Y);
    }

    public void update(float tpf) {
        boolean stateHasChanged = !(lastGameState == game.getState() && lastRoundState == game.getRoundState());
        // No need to set viewstate if game has not changed
        guiControl.updateGui(stateHasChanged);

        lastGameState = game.getState();
        lastRoundState = game.getRoundState();
        
        if (lastRoundState == RoundState.PLAYING) {
            for (Element bar : hpBars.keySet()) {
                Vector3f screenPos = jme3.getCamera().getScreenCoordinates(Util.convertToMonkey3D(hpBars.get(bar).getPosition()));
                Element placeHolder = bar.getParent();

                // X needs an offset and Y needs to be inverted across the screen and an offset
                int xPos = (int) screenPos.getX() - 20;
                int yPos = placeHolder.getParent().getHeight() - (int) screenPos.getY() + 25;

                placeHolder.setConstraintX(new SizeValue("" + xPos + "px"));
                placeHolder.setConstraintY(new SizeValue("" + yPos + "px"));
                placeHolder.getParent().layoutElements();

                SizeValue hp = new SizeValue(hpBars.get(bar).getHitPoints() + "%");
                bar.setConstraintWidth(hp);
            }
        }
    }

    /**
     * Places the unit in the graphical world.
     *
     * @param playerID
     * @param pos
     * @param dir
     * @param color
     */
    public void createGraphicalUnit(int playerID, Vector pos, Vector dir,
            Vector size, ColorRGBA color) {
        Vector3f gPos = Util.convertToMonkey3D(pos);
        Vector3f gDir = Util.convertToMonkey3D(dir);
        Vector3f gSize = Util.convertToMonkey3D(size);

        GraphicalUnit gUnit = new GraphicalUnit(playerID, color,
                gPos,
                gDir,
                gSize,
                assetManager,
                blenderUnit.clone(true));
        // Set it to start listening to its unit
        this.game.addUnitListener(playerID, gUnit);

        // Attach it
        rootNode.attachChild(gUnit.getNode());
    }

    public void propertyChange(PropertyChangeEvent pce) {
        if ("Player Created".equals(pce.getPropertyName())) {
            if (pce.getNewValue().getClass() == Player.class
                    && pce.getOldValue() == null) {
                // Salvage what gUnit needs to set itself up from player
                Player player = (Player) pce.getNewValue();
                Unit unit = player.getUnit();

                Vector pos = unit.getPosition();
                Vector dir = unit.getDirection();
                Vector size = unit.getSize();
                // Also send playerID so it knows which unit to listen to.
                int playerID = player.getId();

                this.createGraphicalUnit(playerID, pos, dir, size, ColorRGBA.randomColor());

                // If a player is created we need to start listening to it so we can know when it shoots
                player.addPropertyChangeListener(this);
                Element bar = guiControl.getHealthBarElement(playerID);
                bar.getParent().setVisible(true);
                hpBars.put(bar, game.getPlayer(playerID).getUnit());

            } else {
                throw new RuntimeException(
                        "Unit Created-event sent without correct parameters");
            }
        }

        if ("CannonBall Created".equals(pce.getPropertyName())) {
            CannonBall cannonBall = (CannonBall) pce.getNewValue();
            GraphicalCannonBall graphicalBall = new GraphicalCannonBall(ColorRGBA.Orange,
                    Util.convertToMonkey3D(cannonBall.getPosition()),
                    Util.convertToMonkey3D(cannonBall.getSize()), this.assetManager,
                    null);
            rootNode.attachChild(graphicalBall.getNode());
            cannonBall.addPropertyChangeListener(graphicalBall);
        }

        if ("Item Created".equals(pce.getPropertyName())) {
            Item item = (Item) pce.getNewValue();
            GraphicalItem graphicalItem = new GraphicalItem(ColorRGBA.randomColor(),
                    Util.convertToMonkey3D(item.getPosition()),
                    Util.convertToMonkey3D(item.getSize()),
                    this.assetManager, null);
            rootNode.attachChild(graphicalItem.getNode());
            System.out.println("VIEW: IM CREATING AN ITEM");
            item.addPropertyChangeListener(graphicalItem);
        }
    }
}
