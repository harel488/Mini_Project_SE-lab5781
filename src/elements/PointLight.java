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
    protected double _radius;
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

    /**
     * default constructor - light source is only 3D point with no dimensions
     * @param intensity genuine intensity of the light source
     * @param position position in the 3D model of the light source
     */
    public PointLight(Color intensity, Point3D position) {
        this(intensity,position,0);
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

    /**
     *
     * @param point
     * @return distance from light source position (center of it) to point
     */
    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }

    @Override
    public List<Point3D> lightPoints(Vector lightDirection, int minPoints) {
        Plane lightSourcePlane = new Plane(_position, lightDirection);
        return circlePoint(_position,_radius,lightSourcePlane,minPoints);
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


    /**
     * gets a 2D circle in the 3D model which represent the light source shape
     * and produces multiple points distributed on the surface
     * @param center center of the light source
     * @param radius radius of the lights source
     * @param plane the plane containing the circle
     * @param minPoints min points to produce
     * @return list of points distributed on the surface,at least as the minimum points required,
     */
    public List<Point3D> circlePoint(Point3D center, double radius, Plane plane, int minPoints) {
        List<Point3D> pointsInCircle = new LinkedList<Point3D>();
        if (radius==0){
            pointsInCircle.add(center);
            return pointsInCircle;
        }

        Vector normal = plane.getNormal(_position);

        //x axis
        Vector x;
        //defining the plane by two orthogonal vectors included in it;
        //multiple scalar of orthogonal vectors is 0. therefore we create vector x so x*normal =0
        if(normal.getHead().getX() ==0 && normal.getHead().getY()  ==0){    //the orthogonal vector crates by the formula below would be zero vector
            x = new Vector(1,0,0);
        }
        else {
            x = new Vector(normal.getHead().getY() * -1, normal.getHead().getX(), 0).normalize();
        }
        //y axix
        Vector y = x.crossProduct(normal);

        Point3D xPoint;     // movement in the x axis
        Point3D yPoint;     // movement in the y axis
        //number of moves from the center to each side in the direction of vector X(or it opposite direction)
        //if the are was a square then the formula was :square (mn Points) / 2
        //since the are is a circle (area is about 80 percent) and our coverage is about 80-90
        // total we get about 2/3 of the original wanted amount
        // there we multiple the previews formula by 1.4 times
        int PARTITION = (int) (Math.sqrt(minPoints) / 2 * 1.4);
        double distance = _radius / PARTITION;
        for (int i = -PARTITION; i <= PARTITION; i++) {
            if (Util.alignZero(i * distance) != 0) {
                xPoint = _position.add(x.scale(i * distance));
            } else {
                xPoint = _position;
            }
            double maxY = Math.sqrt((_radius * _radius) - (i * distance) * (i * distance));
            int moves = (int) (maxY / distance);
            for (int j = -moves; j <= moves; j++) {
                if (Util.alignZero(j * distance) != 0) {
                    pointsInCircle.add(xPoint.add(y.scale(j * distance)));
                }
            }

        }
        return pointsInCircle;
    }
}