/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jme3.math.Vector3f;
import math.Vector;

/**
 * A class with tools.
 * @author Victor Lindhé
 * @modified johnhu
 */
public abstract class Util {
    public static Vector3f convertToMonkey3D(Vector vector) {
        return new Vector3f(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public static Vector convertFromMonkey3D(Vector3f vector) {
        return new Vector(vector.x, vector.y, vector.z);
    }

    public static Vector3f getCenterPosition(Vector3f sizeVector) {
        return new Vector3f( sizeVector.getX()/2, sizeVector.getY()/2, sizeVector.getZ()/2 );
    }
}
