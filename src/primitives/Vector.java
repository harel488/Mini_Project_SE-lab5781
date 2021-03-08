package primitives;




/**
 * Vector Class- To represent a vector in three-dimensional space
 *
 * @author
 */
public class Vector {
    private Point3D _head;

    //Define the Zero Vector
    // For throwing exceptions if necessary
    public final static Vector ZeroVector = new Vector(Point3D.ZERO);

    /**
     * Constructor- getting only Point3D for _head
     *
     * @param head
     */
    public Vector(Point3D head) {
        _head=new Point3D(head);
    }

    /**
     * Constructor- getting three doubles,
     * checking ig equals to Zero
     *
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        Point3D p = new Point3D(x, y, z);
        _head = p;
        if (this.equals(Vector.ZeroVector))
            throw new IllegalArgumentException("this is the Zero Vector");
    }

    /**
     * Constructor- getting three Coordinates
     *
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord,y.coord, z.coord);
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
     * @param o
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
        return "head= " + _head;
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
        return new Vector(_scale * _head.getX(), _scale * _head.getY(), _scale * _head.getZ());
    }

    /**
     * Scalar multiplication between two vectors
     *
     * @param v
     * @return Double Type of the ScalarMult
     */
    public double dotProduct(Vector v) {
        double result = _head.getX() * v._head.getX() +
                _head.getY() * v._head.getY() +
                _head.getZ() * v._head.getZ();
        return result;
    }

    /**
     * Vector multiplication that returns the perpendicular vector to two in multiplied vectors
     *
     * @param v
     * @return The New Vector After Multiplication
     */
    public Vector crossProduct(Vector v) {
        double _x = this._head.getY() * v._head.getZ() - this._head.getZ() * v._head.getY();
        double _y = this._head.getZ() * v._head.getX() - this._head.getX() * v._head.getZ();
        double _z = this._head.getX() * v._head.getY() - this._head.getY() * v._head.getX();
        Point3D p =new Point3D(_x,_y,_z);
        if(p.equals(Point3D.ZERO))
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
        return _head.getX() * _head.getX() + _head.getY() * _head.getY() + _head.getZ() * _head.getZ();
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
     * @return A new Vector After the normalized
     */
    public Vector normalized() {
        return new Vector(_head.getX() / length(), _head.getY() / length(), _head.getZ() / length());
    }

    /**
     * Normalized The Vector
     *
     * @return the same vector after Normalized
     */
    public Vector normalize() {
        this._head = new Point3D(_head.getX() / length(), _head.getY() / length(), _head.getZ() / length());
        return this;
    }

}