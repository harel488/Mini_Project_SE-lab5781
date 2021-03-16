package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for
     * {@link geometries.Cylinder#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        Cylinder cyl = new Cylinder(1, new Ray(new Vector(new Point3D(0,0,1)),new Point3D(0,0,0)),1);

        Vector axisVec = cyl._axisRay.getDirection();
        // ============ Equivalence Partitions Tests ==============
        //TC01: point is on one of the bases
        assertEquals(axisVec, cyl.getNormal(new Point3D(0.1, 0.1, 0)),
                "Bad normal to cylinder where point is on base1");
        //TC02: point is on one of the bases
        assertEquals(axisVec , cyl.getNormal(new Point3D(0.1, 0.1, 1)),
                "\"Bad normal to cylinder where point is on base2\"");
        //TC03: point is not on one of the bases
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point3D(0, 1, 0.5)),
                "Bad normal to cylinder where point is not on one of the bases");

        // ============ boundary value Tests ==============
        double sqr2 = Math.sqrt(2);
        //TC10: point is on the intersection between the cylnder base and its 'tube' - on the circumference of the circle of the base
        assertEquals(axisVec, cyl.getNormal(new Point3D(sqr2, sqr2, 0)),
                "Bad normal to cylinder, where point on circumference of the circle of the base1");
        //TC11: same as above - on the other cylinder base
        assertEquals(axisVec, cyl.getNormal(new Point3D(-sqr2, -sqr2, 1)),
                "Bad normal to cylinder, where point on circumference of the circle of the base1");
        //TC12: point equals to cylinder axis ray head point
        assertEquals(axisVec, cyl.getNormal(new Point3D(0, 0, 0)),
                "Bad normal to cylinder, equals to cylinder axis ray head point");





    }
}