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
    Point3D center;
    double radius;

    /**
     * constructor receiving the center of the sphere and its radius
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * getter
     * @return sphere center point
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * getter
     * @return sphere radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of sphere
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
