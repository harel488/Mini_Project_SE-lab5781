package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent a 'bulb' - light source which light evenly in all directions
 */
public class PointLight extends Light implements LightSource {
    private Point3D _position;
    private double _kC = 1;
    private double _kL = 0;
    private double _kQ = 0;

    /**
     * constructor - get values of the cloror and intensity of the light source.
     * decay coefficient have default values and setters.
     * @param intensity genuine intensity of the light source
     * @param position position in the 3D model of the light source
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this._position = position;
    }

    /**
     *  sets constant decay coefficient (chaining method)
     * @param kC constant decay coefficient
     * @return point light's instance
     */
    public PointLight setkC(double kC) {
        this._kC = kC;
        return this;
    }


    /**
     * sets constant linear coefficient (chaining method)
     * @param kL linear decay coefficient
     * @return point light's instance
     */
    public PointLight setkL(double kL) {
        this._kL = kL;
        return this;
    }
    /**
     * sets constant quadrant coefficient (chaining method)
     * @param kQ quadrant  decay coefficient
     * @return point light's instance
     */
    public PointLight setkQ(double kQ) {
        this._kQ = kQ;
        return this;
    }

    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }

    /**
     * Calculates the intensity of light coming from the light source to the point, by the formula:
     * original intenisy / (kC +   kL * distance between light source and point  +  kQ * squared distance )
     * @param point intensity of light in the point lighten only from this light source
     * @return
     */
    @Override
    public Color getIntensity(Point3D point) {
        double dSquared = point.distanceSquared(_position);
        double d = point.distance(_position);

        return (_intensity.reduce(_kC  +  ( _kL * d)  +  (_kQ * dSquared)) );
    }

    /**
     * getter
     * @param point
     * @return vector from the light spot to the lighten point
     */
    @Override
    public Vector getL(Point3D point) {
        if (point.equals(_position)) {
            return null;
        }
        return point.subtract(_position).normalize();
    }
}
