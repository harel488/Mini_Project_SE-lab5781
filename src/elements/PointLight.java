package elements;

import geometries.Plane;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * represent a 'bulb' - light source which light evenly in all directions
 */
public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _radius=3;
    private double _kC = 1;
    private double _kL = 0;
    private double _kQ = 0;

    /**
     * constructor - get values of the cloror and intensity of the light source.
     * decay coefficient have default values and setters.
     * @param intensity genuine intensity of the light source
     * @param position position in the 3D model of the light source
     */
    public PointLight(Color intensity, Point3D position,double radius) {
        super(intensity);
        this._position = position;
        _radius = radius;
    }
    public PointLight(Color intensity, Point3D position) {
        this(intensity,position,5);
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

    private static final int PARTITION = 5;
    @Override
    public List<Point3D> randomPoints(Vector lightDirection) {
        List<Point3D> randomPoints = new LinkedList<Point3D>();
        Plane lightSourcePlane = new Plane(_position, lightDirection);
        Point3D p1 = _position.add(lightDirection.scale(10)
                    .add(new Vector(lightDirection.getHead().getY() * -1, lightDirection.getHead().getX(),0)));
        Ray ray1 = new Ray(lightDirection.scale(-1),p1);

        Point3D planePoint1 = lightSourcePlane.findIntersections(ray1).get(0);
        Vector x = planePoint1.subtract(_position).normalize();
        Vector y = x.crossProduct(lightDirection);
        Point3D xPoint;
        Point3D yPoint;
        double distance =  _radius / PARTITION;
        for (int i = -PARTITION; i <= PARTITION ; i++) {
            if (Util.alignZero(i*distance) != 0) {
                xPoint = _position.add(x.scale(i * distance));
            } else {
                xPoint = _position;
            }
            double maxY = Math.sqrt((_radius * _radius) - (i * distance) * (i * distance));
            int moves = (int) (maxY / distance);
            for (int j = -moves; j <= moves; j++) {
                if (Util.alignZero(j*distance) != 0) {
                    randomPoints.add(xPoint.add(y.scale(j * distance)));
                }
            }

        }
        return randomPoints;
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
