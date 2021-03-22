package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * The geometry interface contains the getNormal function
 * to return the normal vector to geometry
 *
 * @author Daniel Honig
 */
public interface Geometry extends Intersectable{
    /**
     *
     * @param point - Point3D on the geometry plane
     * @return the normal vector of the geometry
     */
    Vector getNormal(Point3D point);
}