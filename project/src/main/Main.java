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
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        app.setSettings(settings);
        app.start();
    }

    /**
     * Creates view, game and controller and sets up the application through them.
     */
    @Override
    public void simpleInitApp() {
        IGame game = new Game(new Battlefield());
        this.view = new View(this, game);
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
