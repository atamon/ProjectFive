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
    
    public StatusBoxTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
        StatusBox.getInstance().clear();
        final String message = "testMessage";
        PropertyChangeListener pcl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if("StatusBox Message".equals(evt.getPropertyName())){
                   List<Message> messages = (List<Message>)evt.getNewValue();
                   Message expected = messages.get(0);
                   assertEquals(expected.getMessage(),message);
                   assertEquals(expected.getColor(),StatusBox.STATUS_MESSAGE_COLOR);
                }
            }
        };
        StatusBox.getInstance().addPropertyChangeListener(pcl);
        StatusBox.getInstance().message(message);
        StatusBox.getInstance().removePropertyChangeListener(pcl);
        
    }

    /**
     * Test of message method, of class StatusBox.
     */
    @Test
    public void testMessage_Color_String() {
        StatusBox.getInstance().clear();
        final String message = "testMessage";
        PropertyChangeListener pcl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if("StatusBox Message".equals(evt.getPropertyName())){
                   List<Message> messages = (List<Message>)evt.getNewValue();
                   Message expected = messages.get(0);
                   assertEquals(expected.getMessage(),message);
                   assertEquals(expected.getColor(),Color.BLACK);
                }
            }
        };
        StatusBox.getInstance().addPropertyChangeListener(pcl);
        StatusBox.getInstance().message(Color.BLACK, message);
        StatusBox.getInstance().removePropertyChangeListener(pcl);
    }

    /**
     * Test of clear method, of class StatusBox.
     */
    @Test
    public void testClear() {
        final String message = "anotherTestMessage";
        PropertyChangeListener pcl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if("StatusBox Cleared".equals(evt.getPropertyName())){
                   List<Message> messages = (List<Message>)evt.getNewValue();
                   assertEquals(messages.size(),0);
                }
            }
        };
        
        StatusBox.getInstance().addPropertyChangeListener(pcl);
        StatusBox.getInstance().message(message);
        StatusBox.getInstance().clear();
        assertEquals(StatusBox.getInstance().getMessages().size(), 0);
        StatusBox.getInstance().removePropertyChangeListener(pcl);
    }

    /**
     * Test of setVisible method, of class StatusBox.
     */
    @Test
    public void testSetVisible() {
        
        PropertyChangeListener pcl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                assertEquals("StatusBox Visible", evt.getPropertyName());
            }
        };
        StatusBox.getInstance().addPropertyChangeListener(pcl);
        StatusBox.getInstance().setVisible(true);
        StatusBox.getInstance().removePropertyChangeListener(pcl);
        
        PropertyChangeListener pcl2 = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                assertEquals("StatusBox Hidden", evt.getPropertyName());
            }
        };
        
        StatusBox.getInstance().addPropertyChangeListener(pcl2);
        StatusBox.getInstance().setVisible(false);
    }

}
