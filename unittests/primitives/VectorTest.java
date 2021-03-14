package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Unit test foe primitives.Vector class
 * @author Daniel Honig
 * @author Harel Isaschar
 */
class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);

    /**
     * Test method for {@link Vector#ZeroVector}
     */
    @Test
    void testZeroPoint(){
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
            out.println("good: Vector 0 not created");
        }

    }

    /**
     * Test method for{@link Vector#add(Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        Vector vplus=v1.add(v2);
        assertEquals(vplus,new Vector(-1,-2,-3),"v1.add(v2) incorrect");
    }

    /**
     *  Test method for{@link Vector#subtract(Vector)}
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector vminus = v1.subtract(v2);
        assertEquals(vminus,new Vector(3,6,9),"v1.subtract(v2) incorrect");
    }

    /**
     * Test method for{@link Vector#scale(double)}
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1.scale(2),new Vector(2,4,6),"ERROR: scale() wrong value");

        // =============== Boundary Values Tests ==================
        Vector vscale = v1.scale(-0.9999999999999999999999999);
        assertEquals(new Vector(-1,-2,-3),vscale);
    }

    /**
     * Test method for{@link Vector#dotProduct(Vector)}
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1.dotProduct(v2),-28,"ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 3, -2);
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }


    @Test
    void dotProduct2() {
        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0.000000000000877887, 3, -2);
        double v1DotV3 = v1.dotProduct(v3);
        assertEquals(0,alignZero(v1DotV3),"ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(0,v1DotV3,"ERROR: dotProduct() for orthogonal vectors is not zero");

        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for{@link Vector#crossProduct(Vector)}
     */
    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);


        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    /**
     * Test method for{@link Vector#lengthSquared()}
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1.lengthSquared(),14,"ERROR: lengthSquared() wrong result");
    }

    /**
     * Test method for{@link Vector#length()}
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1.length(),Math.sqrt(14),"ERROR: length() wrong result");
    }

    /**
     * Test method for{@link Vector#normalized()}
     */
    @Test
    void normalized() {
        // ============ Equivalence Partitions Tests ==============
        Vector vcopy=v1.normalized();
        assertEquals(vcopy,v1.normalize(),"ERROR: normalized() doesn't create new Vector");
        //Testing the length of normalized Vector
        assertEquals(vcopy.length(),1,"ERROR: normalize Vector length must be 1");
    }

    /**
     * Test method for{@link Vector#normalize()}
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals (vCopy ,vCopyNormalize,"ERROR: normalize() function creates a new vector");
        //Testing the length of normalize Vector
        assertEquals(v.normalize().length(),1,"ERROR: normalize Vector length must be 1");
    }
}