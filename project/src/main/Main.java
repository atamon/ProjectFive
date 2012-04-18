package main;

import model.visual.Battlefield;
import model.Game;
import controller.Controller;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import model.IGame;
import view.View;

/**
 * Main for our PirateBattle application.
 * @author Anton Lindgren
 */
public class Main extends SimpleApplication {
    
    private Controller controller;
    private View view;
    
    private int width;
    private int height;
    
    public static void main(String[] args) {
        Main app = new Main();
    }
    
    /**
     * Creates a new Application
     * @param width The applicationwindow's width
     * @param height The applicationwindow's height
     */
    public Main() {
        super();
        
        // Create our own settings so we can customize our app
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        
        // Save the dimension so we can supply view with it
        this.width = settings.getWidth();
        this.height = settings.getHeight();
        
        // Set settings and start
        this.setSettings(settings);
        this.start();
        
    }

    /**
     * Creates view, game and controller and sets up the application through them.
     */
    @Override
    public void simpleInitApp() {
        IGame game = new Game(new Battlefield());
        this.view = new View(this, game, width, height);
        this.controller = new Controller(this, view, game);
    }

    /**
     * Updates controller, view and game
     * @param tpf 
     */
    @Override
    public void simpleUpdate(float tpf) {
        controller.update(tpf);
    }
}
