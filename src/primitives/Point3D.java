package primitives;

import java.util.Objects;

/**
 * Point3D class to represent a point in three-dimensional space
 *
 * @author
 */
public class Point3D {
    private final Coordinate _x;
    private final Coordinate _y;
    private final Coordinate _Z;

    /**
     * Define The ZERO point so that we can compare and throw exceptions accordingly
     */
    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * Standard Constructor
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = new Coordinate(x.coord);
        _y = new Coordinate(y.coord);
        _Z = new Coordinate(z.coord);
    }

    /**
     * Constructor with double params
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    /**
     * Copy Constructor
     *
     * @param p
     */
    public Point3D(Point3D p) {
        this(p._x.coord, p._y.coord, p._Z.coord);
    }

    /**
     * getters for the Coordinates
     *
     * @return the Cordinate Value in double type
     */
    public double getX() {
        return _x.coord;
    }

    public double getY() {
        return _y.coord;
    }

    public double getZ() {
        return _Z.coord;
    }
    //***************************************

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
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _Z.equals(point3D._Z);
    }

    /**
     * toString Method for printing
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _Z + ")";
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
        double x = this.getX() - p.getX();
        double y = this.getY() - p.getY();
        double z = this.getZ() - p.getZ();
        return new Vector(x, y, z);

    }

    /**
     * Add a Vector to the Point3D
     *
     * @return New Point3D after adding vector direction and size
     */
    public Point3D add(Vector v) {
        return new Point3D(this.getX() + v.getHead().getX(),
                this.getY() + v.getHead().getY(),
                this.getZ() + v.getHead().getZ());
    }

    /**
     * The function calculates the square distance between two points
     *
     * @param p - Is the Other Point3D
     * @return Double Type - The Square Distance
     */
    public double distanceSquared(Point3D p) {
        return (this.getX() - p.getX()) * (this.getX() - p.getX()) +
                (this.getY() - p.getY()) * (this.getY() - p.getY()) +
                (this.getZ() - p.getZ()) * (this.getZ() - p.getZ());
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