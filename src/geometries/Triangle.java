package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 *To represent a triangle in a 3D space by three Point3Ds'
 * Creating a object of a triangle creates a polygon with 3 vertices.
 * @see geometries.Polygon - extending Polygn
 * @author Daniel Honig
 * @author Harel Isaschar
 */
public class Triangle extends Polygon {
    /**
     * constructor receiving 3 Point3D contained in the triangle.
     * calling the Polygon constructor
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3)  {
        super(p1,p2,p3);
    }


}
