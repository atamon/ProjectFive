/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import model.visual.StatusBox.Message;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jnes
 */
public class StatusBoxTest {
    
    private boolean testSuccess = false;
    private String eventString ="";
    private String message= "";
    private PropertyChangeListener pcl;
    private Color messageColor;
    @Before
    public void setUp() {
        pcl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if(eventString.equals(evt.getPropertyName())){
                    if("StatusBox Message".equals(eventString)){
                        List<Message> messages = (List<Message>)evt.getNewValue();
                        Message expected = messages.get(0);
                        testSuccess = (expected.getMessage().equals(message) && expected.getColor().equals(messageColor));
                    } else if("StatusBox Cleared".equals(evt.getPropertyName())){
                        testSuccess = StatusBox.getInstance().getMessages().size() == 0;
                    } else if("StatusBox Hidden".equals(evt.getPropertyName())){
                        testSuccess = "StatusBox Hidden".equals(eventString);
                    } else if("StatusBox Visible".equals(evt.getPropertyName())){
                        testSuccess = "StatusBox Visible".equals(eventString);
                    }
                }
            }
        };
        StatusBox.getInstance().addPropertyChangeListener(pcl);
    }
    
    @After
    public void tearDown() {
        StatusBox.getInstance().removePropertyChangeListener(pcl);
    }

    /**
     * Test of getInstance method, of class StatusBox.
     */
    @Test
    public void testGetInstance() {
        StatusBox one = StatusBox.getInstance();
        StatusBox two = StatusBox.getInstance();
        assertSame(one,two);
    }

    /**
     * Test of message method, of class StatusBox.
     */
    @Test
    public void testMessage_String() {
        testSuccess=false;
        StatusBox.getInstance().clear();
        message = "testMessage";
        eventString = "StatusBox Message";
        messageColor = StatusBox.STATUS_MESSAGE_COLOR;
        StatusBox.getInstance().message(message);
        assertTrue(testSuccess);
    }

    /**
     * Test of message method, of class StatusBox.
     */
    @Test
    public void testMessage_Color_String() {
        testSuccess=false;
        StatusBox.getInstance().clear();
        message = "testMessage";
        eventString = "StatusBox Message";
        messageColor = Color.BLACK;
        StatusBox.getInstance().message(messageColor, message);
        assertTrue(testSuccess);
    }

    /**
     * Test of clear method, of class StatusBox.
     */
    @Test
    public void testClear() {
        testSuccess = false;
        message = "anotherTestMessage";
        eventString = "StatusBox Cleared";
        
        StatusBox.getInstance().message(message);
        StatusBox.getInstance().clear();
        assertEquals(StatusBox.getInstance().getMessages().size(), 0);
        assertTrue(testSuccess);
    }

    /**
     * Test of setVisible method, of class StatusBox.
     */
    @Test
    public void testSetVisible() {
        testSuccess = false;
        eventString = "StatusBox Visible";
        StatusBox.getInstance().setVisible(true);
        assertTrue(testSuccess);
        
        testSuccess = false;
        eventString = "StatusBox Hidden";
        StatusBox.getInstance().setVisible(false);
        assertTrue(testSuccess);
    }

}
