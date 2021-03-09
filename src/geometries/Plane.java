package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane Class -To represent a two-dimensional surface in a three-dimensional space
 * @see geometries.Geometry
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    /**
     * constructor receiving a point on the plane  and a vector orthogonal to it
     *
     * @param p0
     * @param normal
     */
    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    /**
     *constructor receiving three point contained in it
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector U = p1.subtract(p2);
        Vector V = p3.subtract(p2);
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of the plane
     */
    @Override
    public Vector getNormal(Point3D point) {
        return this.getNormal();
    }

    /**
     * getter
     * @return orthogonal vector to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }
}