package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    /**
     * Test method for
     * {@link Geometries#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {

        // =============== Boundary Values Tests ==================
        //TC01 Testing an empty collection
        Geometries g1 = new Geometries();
        Ray r1 = new Ray(new Vector(1, 1, 1), new Point3D(0, 0, 0));
        assertEquals(g1.findIntersections(r1), null, "The test was faild because its an empty list");

        //TC02 No shape is cut
        Geometries g2 = new Geometries(new Polygon(new Point3D(1.11, 1.8, 0), new Point3D(1.58, 0.76, 0), new Point3D(-1.34, 2.76, 0)),
                new Sphere(new Point3D(-2, -1, 0), 0.5));
        g2.findIntersections(new Ray(new Vector(0, 0, 1), new Point3D(0, 0, 0)));
        assertEquals(g2.findIntersections(new Ray(new Vector(0, 0, 1), new Point3D(0, 0, 0))), null, "there is a cut shape");

        //TC03 only one shape is cut
        Geometries g3 = new Geometries(new Polygon(new Point3D(-1, -1, 0), new Point3D(2, -1, 0), new Point3D(0, 2, 0)), new Sphere(new Point3D(-2, -1, 0), 0.5));
        assertEquals(g3.findIntersections(new Ray(new Vector(0, 0, -1), new Point3D(0, 0, 3))).size(), 1, "Test failed zero or more than one shapes cut");

        // ============ Equivalence Partitions Tests ==============
        //TC04 part of shape is cut
        Geometries g4 = new Geometries(new Polygon(new Point3D(-1, -1, 0), new Point3D(2, -1, 0), new Point3D(0, 2, 0)), new Sphere(new Point3D(0, 0, 3), 0.5),
                new Sphere(new Point3D(-2, -1, 0), 0.2));
        assertEquals(g4.findIntersections(new Ray(new Vector(0, 0, -1), new Point3D(0, 0, 4))).size(), 3, "Test failed");
    }
}

