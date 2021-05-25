package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Interface for bodies cut by the rays.
 * The operation findIntersections(Ray ray) returns the Intersections points
 * with the body from the received Ray
 */

public interface Intersectable {
    /**
     * Links the point to the geometry in which it is located
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }


        /**
         * constructor
         * @param geometry geometry containing the point
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }


    }


    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }
    List<GeoPoint> findGeoIntersections (Ray ray);

}
