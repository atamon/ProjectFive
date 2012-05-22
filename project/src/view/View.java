package view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;
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
import math.MonkeyConverter;
import model.visual.Bottle;
import model.visual.Molotov;
import model.visual.Unit;

/**
 * Main View class. Contains all graphics.
 */
public class View implements PropertyChangeListener {

    private final Map<Element, Unit> hpBars = new HashMap<Element, Unit>();
    private final Node blenderUnit;
    private final Node blenderBottle;
    private final IGame game;
    private final SimpleApplication jme3;
    private AssetManager assetManager;
    private Node rootNode;
    private GameState lastGameState;
    private RoundState lastRoundState;
    private GUIController guiControl;

    public View(SimpleApplication jme3, IGame game, NiftyJmeDisplay niftyGUI) {

        this.jme3 = jme3;
        this.game = game;
        assetManager = jme3.getAssetManager();
        rootNode = jme3.getRootNode();

        // Create scene
        createScene();

        // Register a BlenderLoader with our assetManager so it supports .blend
        BlenderImporter.registerBlender(assetManager);

        blenderUnit = BlenderImporter.loadModel(assetManager, BlenderImporter.BOAT_PATH);
        blenderBottle = BlenderImporter.loadModel(assetManager, BlenderImporter.BOTTLE_PATH);

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
        initGround(game.getBattlefieldSize(), game.getBattlefieldPosition());
        initCamera();
        initLighting();

        rootNode.attachChild(SkyFactory.createSky(
                assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
    }

    private void initGround(Vector size, Vector pos) {
        GraphicalBattlefield geoBattlefield = new GraphicalBattlefield(MonkeyConverter.convertToMonkey3D(size),
                MonkeyConverter.convertToMonkey3D(pos), assetManager);

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
        cam.setLocation(new Vector3f(game.getBattlefieldCenter().getX(), 110, 0));
        cam.lookAt(MonkeyConverter.convertToMonkey3D(game.getBattlefieldCenter()).setZ(50), Vector3f.UNIT_Y);
    }

    public void update(float tpf) {
        boolean stateHasChanged = !(lastGameState == game.getState() && lastRoundState == game.getRoundState());
        // No need to set viewstate if game has not changed
        guiControl.updateGui(stateHasChanged);

        lastGameState = game.getState();
        lastRoundState = game.getRoundState();

        if (lastRoundState == RoundState.PLAYING) {
            for (Element bar : hpBars.keySet()) {
                Vector3f screenPos = jme3.getCamera().getScreenCoordinates(MonkeyConverter.convertToMonkey3D(hpBars.get(bar).getPosition()));
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
    public void createUnit(int playerID, Vector pos, Vector dir,
            Vector size, ColorRGBA color) {
        Vector3f gPos = MonkeyConverter.convertToMonkey3D(pos);
        Vector3f gDir = MonkeyConverter.convertToMonkey3D(dir);
        Vector3f gSize = MonkeyConverter.convertToMonkey3D(size);

        GraphicalUnit gUnit = new GraphicalUnit(playerID, color,
                gPos,
                gDir,
                gSize,
                assetManager,
                blenderUnit.clone(true));
        // Set it to start listening to its unit
        game.addUnitListener(playerID, gUnit);

        // Attach it
        rootNode.attachChild(gUnit.getNode());
        gUnit.provideParent(rootNode);
    }

    private void createPlayer(Player player) {
        Unit unit = player.getUnit();

        Vector pos = unit.getPosition();
        Vector dir = unit.getDirection();
        Vector size = unit.getSize();
        // Also send playerID so it knows which unit to listen to.
        int playerID = player.getId();

        final ColorRGBA color = MonkeyConverter.convertToMonkeyColor(player.getColor());
        createUnit(playerID, pos, dir, size, color);

        // If a player is created we need to start listening to it so we can know when it shoots
        player.addPropertyChangeListener(this);

        Element bar = guiControl.getHealthBarElement(playerID);
        bar.getParent().setVisible(true);
        hpBars.put(bar, game.getPlayer(playerID).getUnit());

        // Show leave button
        guiControl.playerActive(playerID, true);

        if (game.hasValidAmountOfPlayers()) {
            guiControl.gameValid(true);
        }
    }

    private void removePlayer(int id) {
        guiControl.playerActive(id, false);
        if (!game.hasValidAmountOfPlayers()) {
            guiControl.gameValid(false);
        }
    }

    private void createCannonBall(CannonBall cannonBall) {
        GraphicalCannonBall graphicalBall = new GraphicalCannonBall(ColorRGBA.Orange,
                MonkeyConverter.convertToMonkey3D(cannonBall.getPosition()),
                MonkeyConverter.convertToMonkey3D(cannonBall.getSize()), assetManager,
                null);
        rootNode.attachChild(graphicalBall.getNode());
        graphicalBall.provideParent(rootNode);
        cannonBall.addPropertyChangeListener(graphicalBall);
    }

    private void createItem(Bottle bottle) {
        GraphicalBottle graphicalItem;
        if (bottle instanceof Molotov) {
            graphicalItem = new GraphicalMolotov(MonkeyConverter.convertToMonkey3D(bottle.getPosition()), blenderBottle.clone(true));
        } else {
            graphicalItem = new GraphicalBottle(MonkeyConverter.convertToMonkey3D(bottle.getPosition()),
                    blenderBottle.clone(true));
        }
        rootNode.attachChild(graphicalItem.getNode());
        graphicalItem.provideParent(rootNode);
        bottle.addPropertyChangeListener(graphicalItem);
    }

    public void propertyChange(PropertyChangeEvent pce) {
        if ("Player Created".equals(pce.getPropertyName())) {
            if (pce.getNewValue().getClass() == Player.class
                    && pce.getOldValue() == null) {

                Player player = (Player) pce.getNewValue();
                createPlayer(player);
            } else {
                throw new RuntimeException(
                        "Unit Created-event sent without correct parameters");
            }
        }

        if ("Player Removed".equals(pce.getPropertyName())) {
            removePlayer(Integer.parseInt("" + pce.getNewValue()));

        }

        if ("CannonBall Created".equals(pce.getPropertyName())) {
            CannonBall cannonBall = (CannonBall) pce.getNewValue();
            createCannonBall(cannonBall);
        }

        if ("Bottle Created".equals(pce.getPropertyName())) {
            Bottle bottle = (Bottle) pce.getNewValue();
            createItem(bottle);
        }

        if ("Round Countdown".equals(pce.getPropertyName())) {
            float counter = Float.parseFloat("" + pce.getNewValue());
            guiControl.countdown(counter);

        }
    }
}
