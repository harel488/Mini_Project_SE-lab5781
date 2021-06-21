package elements;

import geometries.Plane;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

/**
 * represents a 'spot light' -light source with place and direction. light intensity
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor
     * @param direction
     * @param intensity
     * @param position
     * @param radius
     */
    public SpotLight(Vector direction, Color intensity, Point3D position, double radius) {
        super(intensity, position, radius);
        _direction = direction.normalized();
    }

    /**
     * default constructor. light source is a point and has no dimensions or shape
     * @param direction
     * @param intensity
     * @param position
     */
    public SpotLight(Vector direction, Color intensity, Point3D position) {
        this(direction, intensity, position, 0);
    }



    /**
5     * original intenisy *
     * MAXIMUM[ calar multiplication (spot light direction * l vector (vector between the spot light source and
     * the lighten point)   ,   0 ]
     * /
     * (kC +   kL * distance between light source and point  +  kQ * squared distance
     *
     * @param p
     * @return intensity of light in the point lighten only from this light source
     */
    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color intensity = super.getIntensity(p);

        return (intensity.scale(factor));
    }

    /**
     * getter
     *
     * @param p
     * @return vector from the light spot to the lighten point
     */
    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }


    /**
     *
     * @param lightDirection
     * @param minPoints
     * @return list of points distributed on the surface of the light source.min size of the list is as the minimum points required
     */
    @Override
    public List<Point3D> lightPoints(Vector lightDirection, int minPoints) {
        Plane lightSourcePlane = new Plane(_position, _direction);
        return super.circlePoint(_position, _radius, lightSourcePlane, minPoints);
    }
}