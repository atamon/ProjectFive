/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author johnnes wikner
 */
interface IPositionable {
    public void setPosition(float x, float y);
    public void setPosition(Vector pos);
    
    public Vector getPosition();
    
}
