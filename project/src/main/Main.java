package main;

import model.Game;
import controller.Controller;
import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import model.IGame;
import view.View;

/**
 * Main for our application.
 **/
public class Main extends SimpleApplication {

    private final static String version = "0.1-a1";
    private Controller controller;
    private View view;
    private IGame game;

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
        final AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        settings.setTitle("Battle For The Bottle "+version);
        settings.setResolution(1024, 768);
//        settings.setFullscreen(true);
        // Set settings and start
        setShowSettings(false);
        setSettings(settings);
        start();
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
        
        // clear gui from status-info such as fps
        setDisplayStatView(false);
        this.guiNode.detachAllChildren(); 
        
        // Create MVC and make connections
        game = new Game();
        view = new View(this, game, niftyDisplay);
        controller = new Controller(getInputManager(), view, game);
        
        // Don't show all log info.. 
        java.util.logging.Logger.getLogger("").setLevel(Level.SEVERE);
    }

    /**
     * Updates controller, view and game
     *
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        controller.update(tpf);
    }
}
