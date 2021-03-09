package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;


/**
 *Infinite Tube representation by radius of Tube
 * and the ray in the direction of the Tube
 * @see geometries.Geometry
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Tube implements Geometry {
    Ray axisRay;
    double radius;

    /**
     * constructor recieving cylinder's radius and axis ray
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter
     * @return cylinder axis Ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter
     * @return cylinder radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of Tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
