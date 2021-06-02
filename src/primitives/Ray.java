package primitives;
import static java.lang.System.out;

import geometries.Geometry;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
import static java.lang.System.out;
import static primitives.Point3D.ZERO;

import java.util.List;
import java.util.Objects;

/**
 * Class Ray - A Ray is a vector that is also represented by a space position,
 * built from two parameters, one vector and the other Point3D
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Ray {
    //Object fields
   final Vector _direction;
   final Point3D _point;

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

    private static final double DELTA = 0.1;

    /**
     * Building a Ray with moving a point
     * @param point
     * @param dir
     * @param n
     */
    public Ray(Point3D point, Vector dir, Vector n) {
        double sign =dir.dotProduct(n);
        if(Util.isZero(sign)){
            throw new IllegalArgumentException("direction vector is zero vector");
        }
        else
        {
            _point=point.add(n.scale(DELTA*Math.signum(sign)));
            _direction=dir;
        }

    }


//-------------------------------------------------------------

    //***********Getters**************************
    public Vector getDirection() {
        return _direction;
    }

    public Point3D getPoint() {
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

    /**
     * calculate point on ray.
     * @param t
     * @return P0 + t * v {_point + t * _direction}
     */
    public Point3D getPoint(double t)
    {
        Point3D P0=getPoint();
        Vector v=getDirection();
        return P0.add(v.scale(t));
    }

    /**
     * find the closest Point to Ray from a list of Point3D
     * @param pointsList intersections point List
     * @return the closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList){
        Point3D result =null;
        double closestDistance = Double.MAX_VALUE;

        if(pointsList== null){
            return null;
        }

        for (Point3D p: pointsList) {
            double temp = p.distance(_point);
            if(temp < closestDistance){
                closestDistance =temp;
                result =p;
            }
        }

        return  result;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        GeoPoint result =null;
        double closestDistance = Double.MAX_VALUE;

        if(geoPoints== null){
            return null;
        }

        for (GeoPoint geo: geoPoints) {
            double temp = geo.point.distance(_point);
            if(temp < closestDistance){
                closestDistance =temp;
                result =geo;
            }
        }

        return  result;
    }
}
