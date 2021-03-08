package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry {
    Ray axisRay;
    double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
