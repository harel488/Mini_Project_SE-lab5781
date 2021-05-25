package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * The geometry interface contains the getNormal function
 * to return the normal vector to geometry
 *
 * @author Daniel Honig
 */
public abstract class Geometry implements Intersectable{

    protected Color _emission = Color.BLACK;
    private Material _material=new Material();

    /**
     * sets scene emission light
     * @param emission emission light
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     *
     * @return
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     *
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     *
     * @return emission light
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     *
     * @param point - Point3D on the geometry plane
     * @return the normal vector of the geometry
     */
    public abstract Vector getNormal(Point3D point);
}