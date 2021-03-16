package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for
     * {@link geometries.Triangle#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tri = new Triangle(new Point3D(0, 0, 0),new Point3D(0, 0, 1),  new Point3D(0, 1, 0) );
        assertEquals(new Vector(1,0,0), tri.getNormal(new Point3D(0, 0.5 ,0.1)), "Bad normal to triangle");
    }

}