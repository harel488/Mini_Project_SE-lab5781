package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    double _height;

    public Cylinder(double height ,Ray axisRay, double radius) {
        super(axisRay, radius);
        _height = height;
    }
}
