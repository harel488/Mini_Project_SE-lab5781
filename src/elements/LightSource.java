package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

/**
 * represent all light sources(include point light and directional light etc, excludes ambient light etc)
 */
public interface LightSource {
    /**
     * distance from the center of the light source to the point
     * @param point
     * @return distance from the center of the light source to the point
     */
    double getDistance(Point3D point);

    /**
     *
     * @return random point on the light source
     */
    List<Point3D> lightPoints(Vector lightDirection, int minPoints);


    /**
     * calculates intensity of light coming from the light source to the point
     * @param p
     * @return intensity of light in the point lighten only from this light source
     */
    public Color getIntensity(Point3D p);

    /**
     * getter
     * @param p
     * @return return vector from light source to the lighten point
     * */
    public Vector getL(Point3D p);


}
