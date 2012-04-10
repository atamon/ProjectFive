/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import com.jme3.math.Vector3f;

/**
 * A class with tools.
 * @author Victor Lindhé
 */
public class Util {
    public static Vector3f convertToMonkey3D(Vector vector) {
        return new Vector3f(vector.getX(), 0, vector.getY());
    }
    
    public static Vector getDiffVector(Vector vector1, Vector vector2) {
        float diffX = vector1.getX() - vector2.getX();
        float diffY = vector1.getY() - vector2.getY();
        return new Vector(diffX, diffY);
    }
}
