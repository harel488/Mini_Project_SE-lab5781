package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for
     * {@link geometries.Tube#getNormal(Point3D)} .
     */
    @Test
    void getNormal() {
        // ===========
        //
        //
        // =   Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tub = new Tube(new Ray(new Vector(new Point3D(0, 0, 1)), new Point3D(0, 0, 0)), 5);
        assertEquals (new Vector(0, 1, 0), tub.getNormal(new Point3D(0, 5, 7)), "Bad normal to tube");
    }
}
