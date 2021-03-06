package renderer;

import primitives.Color;
import primitives.ColorRay;
import primitives.Ray;
import scene.Scene;
import primitives.Color;

import java.util.List;

/**
 *  stores scene data and
 *  calculates the color of the intersection point between camera rays and geometries in the scene
 */
public abstract class RayTracerBase {

    protected Scene _scene;


    //constructor
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * calculates the color of the point the ray 'sees'
     * - the color of the point that intersects with the ray
     * @param ray
     * @return point's color
     */
    abstract public Color traceRay(Ray ray);

    abstract public Color traceRays(List<ColorRay> rays);
}