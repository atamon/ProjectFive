/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author atamon
 */
public class JoinScreen implements ScreenController {
    
    private Nifty nifty;
    private Screen screen;
    
    public JoinScreen() {
        
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}
