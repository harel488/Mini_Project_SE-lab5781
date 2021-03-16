package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder cyl = new Cylinder(1, new Ray(new Vector(new Point3D(0,0,1)),new Point3D(0,0,0)),1);

        Vector axisVec = cyl._axisRay.getDirection();
        // ============ Equivalence Partitions Tests ==============
        //point is on one of the bases
        assertEquals(axisVec, cyl.getNormal(new Point3D(0.5, 0.5, 0)),
                "Bad normal to cylinder where point is on base1");
        //point is on one of the bases
        assertEquals(axisVec , cyl.getNormal(new Point3D(0.2, 0.2, 1)),
                "\"Bad normal to cylinder where point is on base2\"");
        //point is not on one of the bases
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point3D(0, 1, 0.5)),
                "Bad normal to cylinder where point is not on one of the bases");

        double sqr2 = Math.sqrt(2);
        // ============ boundary value analysis Tests ==============
        //point is on the intersection between the cylnder base and its 'tube' - on the circumference of the circle of the base
        assertEquals(axisVec, cyl.getNormal(new Point3D(sqr2, sqr2, 0)),
                "Bad normal to cylinder, where point on circumference of the circle of the base1");
        //same as above - on the other cylinder base
        assertEquals(axisVec, cyl.getNormal(new Point3D(-sqr2, -sqr2, 1)),
                "Bad normal to cylinder, where point on circumference of the circle of the base1");




    }
}