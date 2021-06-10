package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * represents a 'spot light' -light source with place and direction. light intensity
 */
public class SpotLight extends PointLight{
    private Vector _direction;

    public SpotLight(Vector direction,Color intensity, Point3D position) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * c
     * original intenisy *
     * MAXIMUM[ calar multiplication (spot light direction * l vector (vector between the spot light source and
     * the lighten point)   ,   0 ]
     * /
     * (kC +   kL * distance between light source and point  +  kQ * squared distance
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
     * @param p
     * @return vector from the light spot to the lighten point
     */
    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }


}