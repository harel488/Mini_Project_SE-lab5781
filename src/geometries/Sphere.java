package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 *To represent a sphere in a 3D space by the radius of the sphere and the center of the ball
 * @see geometries.Geometry
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Sphere extends Geometry {
    final Point3D _center;
    final double _radius;

    /**
     * constructor receiving the center of the sphere and its radius
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
       _center = center;
       _radius = radius;
    }

    /**
     * getter
     * @return sphere center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter
     * @return sphere radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * overriding getNormal from Geometry interface
     * @param point - Point3D on the geometry plane
     * @return normal vector of sphere
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector p_0 = point.subtract(_center);
        return p_0.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
    /**
     * Calculate the point of intersection with the Sphere
     * @param ray
     * @return List of the intersected Point3D
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D P0 = ray.getPoint();
        Vector v = ray.getDirection();

        if (P0.equals(_center)) {
            return List.of(new GeoPoint(this, _center.add(v.scale(_radius))));
        }

        Vector U = _center.subtract(P0);

        double tm = Util.alignZero(v.dotProduct(U));

        double d = Util.alignZero(Math.sqrt(U.lengthSquared() -tm*tm));

        if(d>=_radius)
            return null;

        double th = Util.alignZero(Math.sqrt(_radius*_radius - d*d));

        double t1 = Util.alignZero(tm -th);
        double t2 = Util.alignZero( tm + th);

        //RETURN only the t points that bigger then 0
        if(t1 > 0 && t2 > 0){
            Point3D P1 = ray.getPoint(t1);
            Point3D P2 = ray.getPoint(t2);

            return List.of(new GeoPoint(this,P1),new GeoPoint(this,P2));
        }
        if(t1 > 0){
            Point3D P1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this,P1));
        }
        if(t2 > 0){
            Point3D P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,P2));
        }
        return null;

    }


}
