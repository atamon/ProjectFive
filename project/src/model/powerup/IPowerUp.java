/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *
 * @author jnes
 */
public interface IPowerUp {
    public void update(float tpf); // i.e. update lifetime
    public String getMessage();
    public String getName();
}
