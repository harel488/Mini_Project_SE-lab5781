package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A collection of bodies depicting a scene
 * We chose the Linked list because the method that
 * works a lot is add() and the Linked list is more efficient
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables = new LinkedList<>(); //initialize the List

    /**
     * Activates the add() method and
     * adds the list of entities received by the Constructor
     * @param geos
     */
    public Geometries(Intersectable... geos) {
        add(geos);
    }

    public void add(Intersectable... geos) {
        Collections.addAll(_intersectables, geos);
    }

    /**
     * for each of the bodies in _intersectables do
     * activate the findIntersections method and adding to result List
     * the intersections points
     * @param ray
     * @return
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable item : _intersectables) {
            List<Point3D> itemPoints = item.findIntersections(ray);
            if(itemPoints!= null){

                if(result== null)//for the first time addition
                    result= new LinkedList<>();

                result.addAll(itemPoints);
            }
        }
        return result;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if(itemPoints!= null){

                if(result== null)//for the first time addition
                    result= new LinkedList<>();

                result.addAll(itemPoints);
            }
        }
        return result;
    }
}