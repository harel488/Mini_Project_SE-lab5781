package geometries;

import primitives.Point3D;

/**
 *
 * @author daniel and harel
 */
public class Triangle extends Polygon {
    /**
     * constructor receiving 3 3D points contained in the triangle
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3)  {
        super(p1,p2,p3);
    }
}
