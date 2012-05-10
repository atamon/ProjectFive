/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.asset.BlenderKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import java.util.List;

/**
 *
 * @author Anton Lindgren
 */
public abstract class BlenderImporter {

    public static void registerBlender(AssetManager assetManager) {
        assetManager.registerLoader(BlenderModelLoader.class, "blend");
    }

    public static Node loadModel(AssetManager assetManager, String path) {

        BlenderKey key = new BlenderKey(path);
        Spatial blendResult = assetManager.loadAsset(key);
        if (!(blendResult instanceof Node)) {
            throw new RuntimeException("ERROR: BlenderImporter.loadModel()"
                    + " got invalid path, no Spatial loaded."
                    + "Object recieved was of type: "
                    + blendResult.getClass());
        }
        Node node = (Node) blendResult;

        // Use this to correct the ambient color not correctly imported from Blender
        correctMaterialColors(node);

        return node;
    }

    /**
     * BlenderModelLoader does not give us a usable AmbientColor to use, so we make our own.
     * @param node The node holding the imported meshes.
     */
    private static void correctMaterialColors(Node node) {
        // Get sub-nodes, we need to get to the materials so we can set ambient.
        List<Spatial> children = node.getChildren();
        Spatial subNode = children.get(0);
        if (!(subNode instanceof Node)) {
            throw new RuntimeException("ERROR: BlenderImporter.loadModel()"
                    + " failed to get sub-node of blender result, got "
                    + subNode.getClass());
        }

        // If node we can get the meshes and then the materials
        List<Spatial> list = ((Node) subNode).getChildren();
        for (Spatial spatial : list) {
            if (spatial instanceof Geometry) {
                Material mat = ((Geometry) spatial).getMaterial();
                
                // Get the diffuse-color from blender and set it to ambient
                Object objColor = mat.getParam("Diffuse").getValue();
                if (objColor instanceof ColorRGBA) {
                    mat.setColor("Ambient", (ColorRGBA) objColor);
                } else {
                    throw new RuntimeException("ERROR: BlenderImporer.correctMaterialColors()" +
                                " failed to set ambient color from diffuse color. Got obj of type: " +
                                objColor.getClass());
                }
            }
        }
    }
}
