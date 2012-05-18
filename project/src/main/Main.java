package main;

import model.visual.Battlefield;
import model.Game;
import controller.Controller;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import model.settings.SettingsLoader;
import java.util.logging.Level;
import model.IGame;
import model.settings.Settings;
import org.lwjgl.opengl.Display;
import view.View;

/**
 * Main for our PirateBattle application.
 *
 * @author Anton Lindgren
 */
public class Main extends SimpleApplication {

    private Controller controller;
    private View view;
    private IGame game;
    private BitmapText debugInfo;
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Creates a new Application
     *
     * @param width The applicationwindow's width
     * @param height The applicationwindow's height
     */
    public Main() {
        super();

        // Create our own settings so we can customize our app
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);

        // Set settings and start
        this.setShowSettings(false);
        this.setSettings(settings);
        this.start();
    }

    /**
     * Creates view, game and controller and sets up the application through
     * them.
     */
    @Override
    public void simpleInitApp() {
        // Create and init NiftyGUI
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, 
                                                            inputManager, 
                                                            audioRenderer, 
                                                            guiViewPort);
        guiViewPort.addProcessor(niftyDisplay);
                
        // Create MVC and make connections
        game = new Game();
        view = new View(this, game, niftyDisplay);
        controller = new Controller(getInputManager(), view, game);

        setDisplayStatView(false);
        // Set up debug game-state
        java.util.logging.Logger.getLogger("").setLevel(Level.SEVERE);
        debugInfo = new BitmapText(guiFont, false);
        debugInfo.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        debugInfo.setColor(ColorRGBA.Blue);                             // font color
        debugInfo.setLocalTranslation(300, debugInfo.getLineHeight(), 0); // position
        guiNode.attachChild(debugInfo);
    }

    /**
     * Updates controller, view and game
     *
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        controller.update(tpf);

        //Print game-states for debug
        debugInfo.setText(game.getState() + "\t||\t" + game.getRoundState());             // the text
    }
}
