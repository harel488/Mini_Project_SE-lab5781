package primitives;

import java.util.Objects;

/**
 * Class Ray - A Ray is a vector that is also represented by a space position,
 * built from two parameters, one vector and the other Point3D
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Ray {
    //Object fields
    Vector _direction;
    Point3D _point;

    /**
     * Constructor- getting tow Prams The Vector And the Point3D
     * @param direction
     * @param point
     */
    public Ray(Vector direction,Point3D point){
        direction.normalize();
        _direction=direction;
        _point=point;
    }


//-------------------------------------------------------------

    //***********Getters**************************
    public Vector get_direction() {
        return _direction;
    }

    public Point3D get_point() {
        return _point;
    }
//----------------------------------------------------------------

    /**
     * Equal Func- Equals Between Tow Ray Objects
     * @param o - The other Ray
     * @return Boolean Answer
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _direction.equals( ray._direction) &&
                _point.equals(ray._point);
    }

    /**
     * Classic toString Func for printing
     * @return String Type
     */
    @Override
    public String toString() {
        return "_direction=" + _direction +
                ", _point=" + _point ;

    }
}
