package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for
     * {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(1, 0, 0),new Point3D(0, 0, 1),  new Point3D(0, 1, 0) );
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane pl = new Plane(new Point3D(4, 0, 0),new Point3D(0, 0, 4),  new Point3D(0, 4, 0) );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray starts before and crosses the Plane
        List<Point3D> result = pl.findIntersections(new Ray(new Vector(1, 1, 1), new Point3D(1, 1, 0)));
        assertEquals( 1, result.size(),"Wrong: ray does intersects the plane");
        // TC02: Ray starts after and doesn't crosses the Plane
        assertNull(pl.findIntersections(new Ray( new Vector(1, 2, 1),new Point3D(5, 5, 5))),
                "ERROR: Plane is behind the ray");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is not included in the plane
        assertNull(pl.findIntersections(new Ray( new Vector(1, -1, 0),new Point3D(5, 5, 5))),
                "ERROR: Ray is parallel to the plane");
        // TC12: Ray is included in the plane
        assertNull(pl.findIntersections(new Ray( new Vector(1, -1, 0),new Point3D(0, 4, 0))),
                "ERROR: Ray is included in the plane - no intersection points");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray head is before the plane
        result = pl.findIntersections(new Ray( new Vector(1, 1, 1),new Point3D(1, 1, 1)));
        assertEquals( 1, result.size(),"Wrong: ray intersects the plane(Ray orthogonal to the plane)");
        // TC14: Ray head is in the plane
        assertNull(pl.findIntersections(new Ray( new Vector(1, 1, 1),new Point3D(0, 4, 0))),
                "ERROR: Ray head is in the plane - no intersections (Ray orthogonal to the plane");
        // TC15: Ray head is after the plane
        assertNull(pl.findIntersections(new Ray( new Vector(1, 1, 1),new Point3D(5, 5, 5))),
                "ERROR: Ray head is after the plane - no intersections (Ray orthogonal to the plane");
        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        //(𝑃0 is in the plane, but not the ray)
        assertNull(pl.findIntersections(new Ray( new Vector(1, 2, 1),new Point3D(0, 4, 0))),
                "ERROR: Ray head is in the plane - no intersections");
        // TC16: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q)
        assertNull(pl.findIntersections(new Ray( new Vector(1, 2, 1),new Point3D(4, 0, 0))),
                "ERROR: Ray head is in the plane - no intersections");










    }

    }