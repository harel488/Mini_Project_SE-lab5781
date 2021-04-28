package unittests.elements;

import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;
import java.util.List;

public class cameraIntegrationTest {
    @Test
    public void SphereTest() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: two intersection points
        Camera camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        Sphere sp = new Sphere(new Point3D(0, 0, -3), 1);

        List<Point3D> allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = sp.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(2, allPoints.size(), "wrong, two intersection points");

        // TC02: 18 intersection points
        camera = new Camera(new Point3D(0, 0, 0.5),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, -2.5), 2.5);

        allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = sp.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(18, allPoints.size(), "wrong, 18 intersection points");

        // TC03: 10 intersection points
        camera = new Camera(new Point3D(0, 0, 0.5),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, -2), 2);

        allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = sp.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(10, allPoints.size(), "wrong, 10 intersection points");


        // TC04: 9 intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, -2), 4);

        allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = sp.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(9, allPoints.size(), "wrong, 10 intersection points");


        // TC04: 9 intersection points
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        sp = new Sphere(new Point3D(0, 0, 1), 0.5);

        allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = sp.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertNull(allPoints, "wrong, no intersection points");
    }

    @Test
    public void PlaneTest() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: 9 intersection points - plane parallel to XY plane;
        Camera camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        Plane pl = new Plane(new Point3D(0, 0, -5),new Vector(new Point3D(0,0,1)));

        List<Point3D> allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = pl.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(9, allPoints.size(), "wrong, 9 intersection points");


        // TC02: 9 intersection points - plane not parallel to XY plane;
        camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        pl = new Plane(new Point3D(0, 0, -5),new Vector(new Point3D(0,-1,5)));

        allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = pl.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(9, allPoints.size(), "wrong, 6 intersection points");


        // TC03: 6 intersection points
            camera = new Camera(Point3D.ZERO,
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

            pl = new Plane(new Point3D(0, 0, -5),new Vector(new Point3D(0,-5,1)));

            allPoints = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> lst = pl.findIntersections(ray);
                if (lst != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(lst);
                }
            }
        }

        assertEquals(6, allPoints.size(), "wrong, 6 intersection points");

    }


}




