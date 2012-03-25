/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jme3.math.Vector3f;
import model.Vector;

/**
 * A class with tools.
 * @author Victor Lindhé
 */
public class Util {
    public static Vector3f convertToMonkey3D(Vector vector) {
        return new Vector3f((float)vector.getX(), 0, (float)vector.getY());
    }
}
