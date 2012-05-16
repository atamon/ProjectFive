/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import math.Vector;
import model.visual.Battlefield;
import model.visual.StatusBox;
import math.MonkeyConverter;
import de.lessvoid.nifty.screen.Screen;
import java.util.*;
import model.GameState;
import model.IGame;
import model.round.RoundState;
import model.visual.StatusBox.Message;

/**
 *
 * @author victorlindhe
 */
public class GUIController implements PropertyChangeListener {

    public static final String NIFTY_XML_PATH = "xml/main2.xml";
    private final Nifty nifty;
    private final IGame game;
    private final Node rootNode;
    private float lastCount;

    public GUIController(NiftyJmeDisplay niftyGUI, IGame game,
            FilterPostProcessor waterPostProcessor,
            SimpleApplication jme3) {
        this.game = game;
        this.rootNode = jme3.getRootNode();

        // Start listen
        StatusBox.getInstance().addPropertyChangeListener(this);
        
        // Init GUI JoinScreen
        nifty = niftyGUI.getNifty();
        nifty.fromXml(NIFTY_XML_PATH, "join");
        nifty.addXml("xml/HUD.xml");
        nifty.addXml("xml/countdown.xml");
    }

    public void updateGui(boolean stateChanged) {
        if (stateChanged) {
            // Handle which GUI shall be shown
            if (game.getState() == GameState.INACTIVE) {
                nifty.gotoScreen("join");
            }
            if (game.getRoundState() == RoundState.PLAYING) {
                // Display HUD
                nifty.gotoScreen("HUD");
            }
            if (game.getRoundState() == RoundState.PAUSED) {
                nifty.gotoScreen("pause");
            }
        }
    }

    public void countdown(float counter) {
        int count = (int)counter;
        
        if (lastCount != count) {
            Screen screen = nifty.getScreen("countdown");
            
            if (nifty.getCurrentScreen() != screen) {
                nifty.gotoScreen("countdown");
            }
            
            Element shownElem = screen.findElementByName("" + (count + 1));
            if (shownElem != null) {
                shownElem.setVisible(false);
            }
            
            Element counterElem = screen.findElementByName("" + count);
            counterElem.setVisible(true);
        }
        if (count <= 0) {
            nifty.gotoScreen("HUD");
        }
        lastCount = count;
    }

    public void playerActive(int playerID, boolean joined) {
        // Hide current join-gui
        String hide = joined ? "leave" : "join";
        Element hideButton = nifty.getScreen("join").findElementByName(hide + playerID);
        hideButton.setVisible(true);
        // Display the correct gui
        String name = joined ? "join" : "leave";
        Element button = nifty.getScreen("join").findElementByName(name + playerID);
        button.setVisible(false);
    }

    public void gameValid(boolean valid) {
        nifty.getScreen("join").findElementByName("space").onStartScreen();
        nifty.getScreen("join").findElementByName("space").setVisible(valid);
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

    public void propertyChange(PropertyChangeEvent pce) {
        if ("StatusBox Message".equals(pce.getPropertyName())){
            // TODO: Add last 2-5 messages to the status box.
            // Since this happens on every message update. maybe just add latest and remove oldest if limit reached
            List<Message> messages = (List<Message>) pce.getNewValue();
            List<Element> output = this.nifty.getScreen("HUD").findElementByName("info").getElements();
            Iterator<Element> iterEle = output.iterator();
            ListIterator<Message> iterStr = messages.listIterator(messages.size());
            while(iterEle.hasNext() && iterStr.hasPrevious()){
                Element eleMsg = iterEle.next();
                Message strMsg = iterStr.previous();
                eleMsg.getRenderer(TextRenderer.class).setText(strMsg.getMessage());
                eleMsg.getRenderer(TextRenderer.class).setColor(new de.lessvoid.nifty.tools.Color(strMsg.getColor().getRGB()));
            }
        }
        if ("StatusBox Cleared".equals(pce.getPropertyName())){
            List<Element> output = this.nifty.getScreen("HUD").findElementByName("info").getElements();
            Iterator<Element> iterEle = output.iterator();
            while(iterEle.hasNext()){
                Element eleMsg = iterEle.next();
                eleMsg.getRenderer(TextRenderer.class).setText("");
            }
        }
        if ("StatusBox Visible".equals(pce.getPropertyName())){
            this.nifty.getScreen("HUD").findElementByName("info").setVisible(true);
        }
        if ("StatusBox Hidden".equals(pce.getPropertyName())){
            this.nifty.getScreen("HUD").findElementByName("info").setVisible(false);
        }
    }
}
