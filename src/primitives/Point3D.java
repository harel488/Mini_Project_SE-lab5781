package primitives;

import java.util.Objects;

/**
 * Point3D class to represent a point in three-dimensional space
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    /**
     * Define The ZERO point so that we can compare and throw exceptions accordingly
     */
    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * Constructor with 3 coordinate params
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate   y, Coordinate z) {
        this(x.coord, y.coord, z.coord);

    }

    /**
     * Constructor with 3 double params
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }



    /**
     * Equals method checking if tow Point3D are equals
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * toString Method for printing
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";
    }

    //*************Actions**************

    /**
     * Subtract method- Subtract the first point from the second and
     * return the vector from the first point to the second
     *
     * @param p
     * @return new Vector with _head= p-this._head
     */
    public Vector subtract(Point3D p) {
        double x = _x.coord - p._x.coord;
        double y = _y.coord - p._y.coord;
        double z = _z.coord - p._z.coord;
        return new Vector(x, y, z);

    }

    /**
     * Add a Vector to the Point3D
     *
     * @return New Point3D after adding vector direction and size
     */
    public Point3D add(Vector v) {
        return new Point3D(
                _x.coord +v._head._x.coord,
                _y.coord +v._head._y.coord,
                _z.coord +v._head._z.coord);
    }

    /**
     * The function calculates the square distance between two points
     * by the formula:
     * (x1,y1,z1) <-distance-> (x2,y2,z2) = (x1-x2)^2 + (y1-y2)^2+ (z1-z2)^2
     * @param p - Is the Other Point3D
     * @return Double Type - The Square Distance
     */
    public double distanceSquared(Point3D p) {
        return  (_x.coord-p._x.coord) * (_x.coord-p._x.coord) +
                (_y.coord-p._y.coord) * (_y.coord-p._y.coord) +
                (_z.coord-p._z.coord) * (_z.coord-p._z.coord);
    }

    /**
     * The function calculates the distance between two points,
     * Helped The Previous Function
     *
     * @param p - Is The Other Point3D
     * @return Double Type - The Distance
     */
    public double distance(Point3D p) {
        return Math.sqrt(this.distanceSquared(p));
    }
}