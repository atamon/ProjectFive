package view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.water.WaterFilter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.GameState;
import model.IGame;
import model.player.Player;
import model.round.RoundState;
import math.Vector;
import model.visual.Battlefield;
import model.visual.CannonBall;
import model.visual.Item;
import util.Util;
import model.visual.Unit;

/**
 *
 * @author Anton Lindgren @modified johnhu
 */
public class View implements PropertyChangeListener {

    public static final String NIFTY_XML_PATH = "xml/main.xml";
    public static final float[] MAGICAL_VIEW_ZERO = {0.06f, 0.45f, 0.60f, 0.95f};
    public static final float[] MAGICAL_VIEW_ONE = {0.56f, 0.95f, 0.15f, 0.50f};
    public static final float[] MAGICAL_VIEW_TWO = {0.06f, 0.45f, 0.15f, 0.50f};
    public static final float[] MAGICAL_VIEW_THREE = {0.56f, 0.95f, 0.60f, 0.95f};
    private final List<ViewPort> viewports = new LinkedList<ViewPort>();
    private final Map<Element, Unit> hpBars = new HashMap<Element, Unit>();
    private final Nifty nifty;
    private final Node blenderUnit;
    private final IGame game;
    private final SimpleApplication jme3;
    private AssetManager assetManager;
    private Node rootNode;
    private GameState lastGameState;
    private RoundState lastRoundState;

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

        blenderUnit = BlenderImporter.loadModel(assetManager, BlenderImporter.BOAT_PATH);

        // Create water effects
        FilterPostProcessor waterPostProcessor = new FilterPostProcessor(assetManager);
        WaterFilter water = new WaterFilter(rootNode, new Vector3f(0, -1, 1));
        water.setWaterHeight(4f);
        waterPostProcessor.addFilter(water);
        jme3.getViewPort().addProcessor(waterPostProcessor);

        
        
        // Set up individual cam positions
        Vector bfSize = game.getBattlefieldSize();
        viewports.add(setUpCameraView(MAGICAL_VIEW_ZERO, Util.convertToMonkey3D(Battlefield.getStartingPosition(0, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_ONE, Util.convertToMonkey3D(Battlefield.getStartingPosition(1, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_TWO, Util.convertToMonkey3D(Battlefield.getStartingPosition(2, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_THREE, Util.convertToMonkey3D(Battlefield.getStartingPosition(3, bfSize)), waterPostProcessor));

        // Init GUI JoinScreen
        nifty = niftyGUI.getNifty();
        nifty.fromXml(NIFTY_XML_PATH, "join");
        nifty.addXml("xml/HUD.xml");

        // We need to fetch first gamestates right away
        lastGameState = game.getState();
        lastRoundState = game.getRoundState();
    }

    private ViewPort setUpCameraView(float[] vpPos, Vector3f unitPos, FilterPostProcessor fpp) {
        // .clone() works for us now since we will use same aspect ratio as window.
        Vector3f position = unitPos.add(0, 25f, -10f);
        Camera camera = jme3.getCamera().clone();
        camera.setViewPort(vpPos[0], vpPos[1], vpPos[2], vpPos[3]);
        camera.setLocation(position);
        camera.lookAt(unitPos, Vector3f.UNIT_Y);

        ViewPort viewport = jme3.getRenderManager().createMainView("PiP", camera);
        viewport.setClearFlags(true, true, true);
        viewport.attachScene(rootNode);
        viewport.addProcessor(fpp);
        return viewport;
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
        rootNode.attachChild(geoBattlefield.getGeometry());
        
        ParticleEmitter fog = new ParticleEmitter("Fog", ParticleMesh.Type.Triangle, 10);
        Material fogMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fogMat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
        fog.setMaterial(fogMat);
        fog.setImagesX(3);
        fog.setImagesY(3);
        fog.setLowLife(100000.0f);
        fog.setGravity(0, -10, 0);
        fog.setRotateSpeed(1);
        fog.setStartColor(ColorRGBA.Red);
        fog.getParticleInfluencer().setVelocityVariation(0.60f);
        fog.setLocalTranslation(Vector3f.ZERO);
        rootNode.attachChild(fog);
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
        updateGui(stateHasChanged);

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

    private void updateGui(boolean stateChanged) {
        if (stateChanged) {
            // Handle which GUI shall be shown
            if (game.getState() == GameState.INACTIVE) {
                // Show joinscreen
                for (ViewPort vp : viewports) {
                    vp.setEnabled(true);
                }
                nifty.gotoScreen("join");
            }
            if (game.getState() == GameState.ACTIVE) {
                // Hide joinscreen
                for (ViewPort vp : viewports) {
                    vp.setEnabled(false);
                }
                // Display HUD
                nifty.gotoScreen("HUD");
            }
            if (game.getRoundState() == RoundState.PAUSED) {
                nifty.gotoScreen("pause");
            }
        }
    }

    private Element getHealthBarElement(int playerID) {
        // Since this is done only a few times per game no need to optimize resources
        Element elem = nifty.getScreen("HUD").findElementByName("" + playerID);
        if (elem == null) {
            throw new IllegalArgumentException("ERROR: No such Element in Nifty-XML, cannot get HP-bar :)");
        } else {
            return elem;
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
                Element bar = getHealthBarElement(playerID);
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
