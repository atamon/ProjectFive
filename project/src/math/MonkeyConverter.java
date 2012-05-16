/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.awt.Color;
import math.Vector;

/**
 * A class with tools.
 * @author Victor Lindhé
 * @modified johnhu
 */
public abstract class MonkeyConverter {
    public static Vector3f convertToMonkey3D(Vector vector) {
        return new Vector3f(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public static Vector convertFromMonkey3D(Vector3f vector) {
        return new Vector(vector.x, vector.y, vector.z);
    }

    public static ColorRGBA convertToMonkeyColor(Color color) {
        return new ColorRGBA(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }
}
