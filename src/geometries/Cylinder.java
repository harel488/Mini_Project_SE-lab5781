package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * Cylinder class represents a cylinder at final height,
 * extends Tube class.
 *
 * @see geometries.Tube
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Cylinder extends Tube {

   final double _height;

    /**
     * constructor receiving cylinder dimensions
     *
     * @param height
     * @param axisRay
     * @param radius
     */
    public Cylinder(double height ,Ray axisRay, double radius) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * getter
     * @return cylinder height
     */
    public double get_height() {
        return _height;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry cylinder
     * @return normal vector of Tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        //if the vector is contained in one of the cylinder bases than the vector from one point in the base to it
        //would be orthogonal to the axis ray vector
        Point3D base1 = _axisRay.getPoint();
        Point3D base2 = base1.add(_axisRay.getDirection().scale(_height));
        Vector v1 = point.subtract(base1);
        Vector v2 = point.subtract(base2);
        if (v1.dotProduct(_axisRay.getDirection()) == 0 || v2.dotProduct(_axisRay.getDirection()) == 0) {
            return _axisRay.getDirection();
        }
        else
            return super.getNormal(point);
    }
    @Override
    public String toString() {
        return "Cylinder{" + super.toString() +
                "_height=" + _height +
                '}';
    }
}
