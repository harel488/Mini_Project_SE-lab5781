package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 *
 * @author daniel and harel
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
