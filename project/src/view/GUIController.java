/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import java.util.LinkedList;
import java.util.List;
import math.Vector;
import model.GameState;
import model.IGame;
import model.round.RoundState;
import model.visual.Battlefield;
import util.Util;

/**
 *
 * @author victorlindhe
 */
public class GUIController {
    
    public static final String NIFTY_XML_PATH = "xml/main.xml";
    public static final float[] MAGICAL_VIEW_ZERO = {0.06f, 0.45f, 0.60f, 0.95f};
    public static final float[] MAGICAL_VIEW_ONE = {0.56f, 0.95f, 0.15f, 0.50f};
    public static final float[] MAGICAL_VIEW_TWO = {0.06f, 0.45f, 0.15f, 0.50f};
    public static final float[] MAGICAL_VIEW_THREE = {0.56f, 0.95f, 0.60f, 0.95f};
    private final List<ViewPort> viewports = new LinkedList<ViewPort>();
    private final Nifty nifty;
    private final IGame game;
    private final SimpleApplication jme3;
    private final Node rootNode;
    
    public GUIController(NiftyJmeDisplay niftyGUI, IGame game, 
                                FilterPostProcessor waterPostProcessor,
                                SimpleApplication jme3) {
        this.jme3 = jme3;
        this.game = game;
        this.rootNode = jme3.getRootNode();
        
        // Init GUI JoinScreen
        nifty = niftyGUI.getNifty();
        nifty.fromXml(NIFTY_XML_PATH, "join");
        nifty.addXml("xml/HUD.xml");
        
        // Set up individual cam positions
        Vector bfSize = game.getBattlefieldSize();
        viewports.add(setUpCameraView(MAGICAL_VIEW_ZERO, Util.convertToMonkey3D(Battlefield.getStartingPosition(0, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_ONE, Util.convertToMonkey3D(Battlefield.getStartingPosition(1, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_TWO, Util.convertToMonkey3D(Battlefield.getStartingPosition(2, bfSize)), waterPostProcessor));
        viewports.add(setUpCameraView(MAGICAL_VIEW_THREE, Util.convertToMonkey3D(Battlefield.getStartingPosition(3, bfSize)), waterPostProcessor));
    }
    
    public void updateGui(boolean stateChanged) {
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
    
    public Element getHealthBarElement(int playerID) {
        // Since this is done only a few times per game no need to optimize resources
        Element elem = nifty.getScreen("HUD").findElementByName("" + playerID);
        if (elem == null) {
            throw new IllegalArgumentException("ERROR: No such Element in Nifty-XML, cannot get HP-bar :)");
        } else {
            return elem;
        }
    }
}
