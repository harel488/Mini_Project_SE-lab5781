package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;


/**
 *
 * @author daniel and harel
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
