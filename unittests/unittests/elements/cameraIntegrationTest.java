package unittests.elements;

import elements.Camera;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;
import java.util.List;

/**
 * integration tests of construction rays from camera through view plane and finding their intersections with
 * geometries in the scene
 */
public class cameraIntegrationTest {

    /**
     * generates view plane and find intersections
     * @param geo 3D geometry in the scene
     * @param cam the point view of the scene
     * @return intersectiom point of camera rays and the geometry
     */
    private List<Point3D> findIntersections(Geometry geo, Camera cam) {
        List<Point3D> allPoints = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cam.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = geo.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        return allPoints;
    }

    /**
     * integration test of construction rays through view plane and find their intersections with sphere
     */
    @Test
    public void SphereTest() {

        // TC01: two intersection points
        Camera camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        Sphere sp = new Sphere(new Point3D(0, 0, -3), 1);

        assertEquals(2, findIntersections(sp, camera).size(), "wrong, two intersection points");


        // TC02: 18 intersection points
        camera = new Camera(new Point3D(0, 0, 0.5),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        sp = new Sphere(new Point3D(0, 0, -2.5), 2.5);

        assertEquals(18, findIntersections(sp, camera).size(), "wrong, 18 intersection points");


        // TC03: 10 intersection points
        camera = new Camera(new Point3D(0, 0, 0.5),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, -2), 2);

        assertEquals(10, findIntersections(sp, camera).size(), "wrong, 10 intersection points");


        // TC04: 9 intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, -2), 4);

        assertEquals(9, findIntersections(sp, camera).size(), "wrong, 9 intersection points");


        // TC05: no intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, 1), 0.5);

        assertNull(findIntersections(sp, camera), "wrong, no intersection points exists");

    }
    /**
     * integration test of construction rays through view plane and find their intersections with plane
     */
    @Test
    public void PlaneTest() {
        // TC01: 9 intersection points - plane parallel to XY plane;
        Camera camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        Plane pl = new Plane(new Point3D(0, 0, -5), new Vector(new Point3D(0, 0, 1)));

        assertEquals(9, findIntersections(pl, camera).size(), "wrong, 9 intersection points");


        // TC02: 9 intersection points - plane not parallel to XY plane;
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        pl = new Plane(new Point3D(0, 0, -5), new Vector(new Point3D(0, -1, 5)));

        assertEquals(9, findIntersections(pl, camera).size(), "wrong, 9 intersection points");


        // TC03: 6 intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(
                        0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        pl = new Plane(new Point3D(0, 0, -5), new Vector(new Point3D(0, -5, 1)));

        assertEquals(6, findIntersections(pl, camera).size(), "wrong, 6 intersection points");

        // TC04: no intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(
                        0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        pl = new Plane(new Point3D(0, 0, -5), new Vector(new Point3D(0, 1, 0)));

        assertNull(findIntersections(pl, camera), "wrong, no intersection points");

    }
    /**
     * integration test of construction rays through view plane and find their intersections with triangle
     */
    @Test
    public void TriangleTest() {
        // TC01: one intersection points
        Camera camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        Triangle tri = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));

        List<Point3D> allPoints = findIntersections(tri, camera);

        assertEquals(1, allPoints.size(), "wrong, one intersection points");

        // TC02: two intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        tri = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));

        allPoints = findIntersections(tri, camera);

        assertEquals(2, allPoints.size(), "wrong, two intersection points");

    }
}




