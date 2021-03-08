package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        //TODO check direction of vectors
        Vector U = p1.subtract(p2);
        Vector V = p3.subtract(p2);

        Vector N = U.crossProduct(V);

        N.normalize();

        _normal = N;
    }


    @Override
    public Vector getNormal(Point3D point) {
        return this.getNormal();
    }

    public Vector getNormal() {
        return _normal;
    }

}