package main;

import Model.Game;
import controller.Controller;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import javax.swing.text.View;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

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
        View view = new View(this, game);
        Controller controller = new Controller(this, view, game);
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
