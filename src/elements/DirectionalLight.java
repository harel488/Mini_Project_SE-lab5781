package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 *  represents a 'sun light source - light source with only direction but no position
 */
public class DirectionalLight extends Light implements LightSource{
    private final Vector _direction;
    double _radius;

    /**
     * constructor - gets color and direction of the light source
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction, double radius) {
        super(intensity);
        _direction = direction;
    }

    public DirectionalLight(Color intensity, Vector direction) {
        this(intensity,direction,0);
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Point3D> lightPoints(Vector lightDircection, int minPoints) {
        return null;
    }




    /**
     * intensity of light in the point lighten only from this light source,
     * equals to the original intensity
     * @param p
     * @return intensity of light in the point lighten only from this light source
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * getter
     * @param p
     * @return vector from the light spot to the lighten point
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normalize();
    }


}
