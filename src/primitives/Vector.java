package primitives;

import static java.lang.System.out;
import static primitives.Point3D.ZERO;

/**
 * Vector Class- To represent a vector in three-dimensional space
 *
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Vector {
    Point3D _head;

    //Define the Zero Vector
    // For throwing exceptions if necessary
    public final static Vector ZeroVector = new Vector(Point3D.ZERO);

    /**
     * Constructor- getting only Point3D for _head
     *
     * @param head
     */
    public Vector(Point3D head) {
        _head = head;
    }

    /**
     * Constructor- getting three doubles,
     * checking ig equals to Zero
     *
     * @param x x axis
     * @param y y axis
     * @param z z axis
     * @throws IllegalArgumentException- if the Constructor build the Zero Vector-{0,0,0}.
     */
    public Vector(double x, double y, double z) {
        Point3D point = new Point3D(x, y, z);
        if (point.equals(ZERO))
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        _head = point;
    }

    /**
     * Constructor- getting three Coordinates
     *
     * @param x x axis
     * @param y y axis
     * @param z z axis
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * Get for _head field
     *
     * @return this._head
     */
    public Point3D getHead() {
        return _head;
    }


    /**
     * Compares two vectors
     *
     * @param o vector to compare with
     * @return true if equals, false if no equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "head= " + _head.toString();
    }

    /**
     * adding Vectors
     *
     * @param v
     * @return new Vector v1+v2
     */
    public Vector add(Vector v) {
        return new Vector(_head.add(v));
    }

    /**
     * subtract Vectors
     *
     * @param v
     * @return new Vector v1-v2
     */
    public Vector subtract(Vector v) {
        return new Vector(_head.subtract(v._head)._head);
    }

    /**
     * Vector multiplier in scalar
     *
     * @param _scale
     * @return new Vector after multiplies in scalar
     */
    public Vector scale(double _scale) {
        return new Vector(_scale * _head._x.coord, _scale * _head._y.coord, _scale * _head._z.coord);
    }

    /**
     * Scalar multiplication between two vectors
     * by the formula:
     * (x1,y1,z1)*(x2,y2,z2) = x1*x2 + y1*y2 + z1*z2
     *
     * @param v
     * @return Double Type of the ScalarMult
     */
    public double dotProduct(Vector v) {
        return
                _head._x.coord * v._head._x.coord +
                        _head._y.coord * v._head._y.coord +
                        _head._z.coord * v._head._z.coord;
    }

    /**
     * Vector multiplication that returns the perpendicular vector to two in multiplied vectors
     * the formula:
     * ( v1 )     ( u1 )      (  v2*u3 - v3*u2 )
     * ( v2 )  X  ( u2 )  =   (  v3*u1 - v1*u3 )     [all three are three dimensional vector]
     * ( v3 )     ( u3 )      (  v1*u2 - v2-u1 )
     *
     * @param v
     * @return The New Vector After Multiplication
     */
    public Vector crossProduct(Vector v) {
        double _x = this._head._y.coord * v._head._z.coord - this._head._z.coord * v._head._y.coord;
        double _y = this._head._z.coord * v._head._x.coord - this._head._x.coord * v._head._z.coord;
        double _z = this._head._x.coord * v._head._y.coord - this._head._y.coord * v._head._x.coord;
        Point3D p = new Point3D(_x, _y, _z);
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("The Vectors are Parallel ");
        Vector newVec = new Vector(p);
        return newVec;

    }

    /**
     * Calculate the Vector Squared Length
     *
     * @return Double Type- The Squared Length of the Vector
     */
    public double lengthSquared() {
        return _head._x.coord * _head._x.coord + _head._y.coord * _head._y.coord + _head._z.coord * _head._z.coord;
    }

    /**
     * Calculate the Vector Length , Using The previous Function
     *
     * @return Double Type- The Length of the Vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }


    /**
     * Normalized The Vector
     *
     * @return A new normalized Vector
     */
    public Vector normalized() {
        return new Vector(
                new Point3D(_head._x.coord / length(), _head._y.coord / length(), _head._z.coord / length()));
    }

    /**
     * Normalized The Vector
     *
     * @return the same vector after Normalized
     */
    public Vector normalize() {
        this._head = new Point3D(_head._x.coord / length(), _head._y.coord / length(), _head._z.coord / length());
        return this;
    }
}