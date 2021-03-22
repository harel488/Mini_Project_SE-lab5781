package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 * Interface for bodies cut by the rays.
 * The operation findIntersections(Ray ray) returns the Intersections points
 * with the body from the received Ray
 */

public interface Intersectable {

    List<Point3D> findIntersections(Ray ray);
}
