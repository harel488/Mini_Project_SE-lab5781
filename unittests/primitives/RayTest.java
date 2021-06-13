package primitives;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static geometries.Intersectable.GeoPoint;

class RayTest {
    /**
     * Test method for
     * {@link Ray#findClosestPoint(List)}
     */
    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: point in the middle of the list is closet to the the beginning of the Ray.
        Ray ray = new Ray(new Vector(1, 10, -100), new Point3D(0, 0, 10));
        List<Point3D> list = new LinkedList<Point3D>();
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0, 2, -10));
        list.add(new Point3D(0.5, 0, -100));

        assertEquals(list.get(2), ray.findClosestPoint(list));


        // ============ boundary value Tests ==============
        //TC01: the pointList is null.
        list = null;
        assertNull(ray.findClosestPoint(list), "try again");

        //TC02: The first point is closest to the beginning of the Ray.

        list = new LinkedList<Point3D>();
        list.add(new Point3D(0, 2, -10));
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0.5, 0, -100));

        assertEquals(list.get(0), ray.findClosestPoint(list));

        //TC03: The last point is closest to the beginning of the Ray.
        list = new LinkedList<Point3D>();
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0.5, 0, -100));
        list.add(new Point3D(0, 2, -10));

        assertEquals(list.get(3), ray.findClosestPoint(list));
    }
    /**
     * Test method for
     * {@link Ray#findClosestGeoPoint(List)}
     */
    @Test
    void findClosestGeoPoint() {
        Geometry triangle =new Triangle(Point3D.ZERO,new Point3D(0,0,1),new Point3D(0,1,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: point in the middle of the list is closet to the the beginning of the Ray.
        Ray ray = new Ray(new Vector(1, 10, -100), new Point3D(0, 0, 10));
        List<Intersectable.GeoPoint> list = new LinkedList<>();
        list.add(new Intersectable.GeoPoint(triangle, new Point3D(1, 1, -100)));
        list.add(new Intersectable.GeoPoint(triangle, new Point3D(-1, 1, -99)));
        list.add(new Intersectable.GeoPoint(triangle, new Point3D(0, 2, -10)));
        list.add(new Intersectable.GeoPoint( triangle,new Point3D(0.5, 0, -100)));

        assertEquals(list.get(2), ray.findClosestGeoPoint(list));


        // ============ boundary value Tests ==============
        //TC01: the pointList is null.
        list = null;
        assertNull(ray.findClosestGeoPoint(list), "try again");

        //TC02: The first point is closest to the beginning of the Ray.

        list = new LinkedList<GeoPoint>();
        list.add(new Intersectable.GeoPoint(triangle ,new Point3D(0, 2, -10)));
        list.add(new GeoPoint( triangle,new Point3D(1, 1, -100)));
        list.add(new GeoPoint(triangle,new Point3D(-1, 1, -99)));
        list.add(new GeoPoint(triangle,new Point3D(0.5, 0, -100)));

        assertEquals(list.get(0), ray.findClosestGeoPoint(list));

        //TC03: The last point is closest to the beginning of the Ray.
        list = new LinkedList<GeoPoint>();
        list.add(new GeoPoint(triangle,new Point3D(1, 1, -100)));
        list.add(new GeoPoint(triangle,new Point3D(-1, 1, -99)));
        list.add(new GeoPoint(triangle,new Point3D(0.5, 0, -100)));
        list.add(new GeoPoint(triangle,new Point3D(0, 2, -10)));

        assertEquals(list.get(3), ray.findClosestGeoPoint(list));
    }



}