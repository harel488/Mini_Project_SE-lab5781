package renderer;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import static geometries.Intersectable.GeoPoint;


/**
 * calculates the color of the intersection point between camera rays and geometries in the 3D model
 */
public class RayTracerBasic extends  RayTracerBase {
    public RayTracerBasic(Scene myScene) {
        super(myScene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if(intersections == null){
            return _scene.background;
        }
        else{
            GeoPoint closest = ray.findGeoClosestPoint(intersections);
            return calcColor(closest);
        }

    }

    /**
     * calculates the color of a 3D point in the scene
     * @param geoPoint point of intersection
     * @return point's color
     */
    public Color calcColor(GeoPoint geoPoint) {
        Color intensity = geoPoint.geometry.getEmission();
        intensity = intensity.add(_scene.ambientlight.getIntensity());
        return intensity;
    }
}
