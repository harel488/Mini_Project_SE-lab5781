package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 *To represent a sphere in a 3D space by the radius of the sphere and the center of the ball
 * @see geometries.Geometry
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    /**
     * constructor receiving the center of the sphere and its radius
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
       _center = center;
       _radius = radius;
    }

    /**
     * getter
     * @return sphere center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter
     * @return sphere radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of sphere
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector p_0 = point.subtract(_center);
        return p_0.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}
