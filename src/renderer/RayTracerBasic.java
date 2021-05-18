package renderer;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * calculates the color of the intersection point between camera rays and geometries in the 3D model
 */
public class RayTracerBasic extends  RayTracerBase {
    public RayTracerBasic(Scene myScene) {
        super(myScene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = _scene.geometries.findIntersections(ray);
        if(intersections == null){
            return _scene.background;
        }
        else{
            Point3D closest = ray.findClosestPoint(intersections);
            return calcColor(closest);
        }

    }

    /**
     * calculates the color of a 3D point in the scene
     * @param point point of intersection
     * @return point's color
     */
    public Color calcColor(Point3D point){
        return _scene.ambientlight.getIntensity();
    }
}
