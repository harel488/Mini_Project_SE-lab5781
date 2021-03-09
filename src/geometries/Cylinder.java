package geometries;

import primitives.Ray;


/**
 * Cylinder class represents a cylinder at final height,
 * extends Tube class.
 *
 * @see geometries.Tube
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Cylinder extends Tube {

    double _height;

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

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() +
                "_height=" + _height +
                '}';
    }
}
