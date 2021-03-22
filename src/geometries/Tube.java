package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import java.util.List;


/**
 *Infinite Tube representation by radius of Tube
 * and the ray in the direction of the Tube
 * @see geometries.Geometry
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Tube implements Geometry {
    final Ray _axisRay;
    final double _radius;

    /**
     * constructor recieving cylinder's radius and axis ray
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * getter
     * @return cylinder axis Ray
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter
     * @return cylinder radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of Tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = _axisRay.getPoint();
        Vector p0_p = point.subtract(p0);
        Vector v  = _axisRay.getDirection();
        double t = v.dotProduct(p0_p);
        Point3D o = p0.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
