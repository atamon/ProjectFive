package main;

import model.Game;
import controller.Controller;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
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
        app.start();
    }

    /**
     * Creates view, game and controller and sets up the application through them.
     */
    @Override
    public void simpleInitApp() {
        Game game = new Game();
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

    @Override
    public void simpleRender(RenderManager rm) {
        view.update();
    }
}