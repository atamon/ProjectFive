/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jme3.math.Vector3f;
import model.tools.Vector;

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

    public static Vector3f getCenterPosition(Vector3f sizeVector) {
        return new Vector3f( sizeVector.getX()/2, sizeVector.getY()/2, sizeVector.getZ()/2 );
    }
}
