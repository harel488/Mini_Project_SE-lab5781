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

    public SpotLight(Vector direction, Color intensity, Point3D position) {
        this(direction, intensity, position, 2.0);
    }

    public SpotLight(Vector direction, Color intensity, Point3D position, double radius) {
        super(intensity, position, radius);
        _direction = direction.normalized();
    }

    /**
     * c
     * original intenisy *
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

    private static final int PARTITION = 7;

    @Override
    public List<Point3D> randomPoints(Vector lightDirection) {

        List<Point3D> randomPoints = new LinkedList<Point3D>();
        Plane lightSourcePlane = new Plane(_position, _direction);
        Point3D p1 = _position.add(_direction.scale(50)).add(new Vector(0, 0, 5));
        Point3D p2 = _position.add(_direction.scale(50)).add(new Vector(5, 0, 0));
        Ray ray1 = new Ray(_direction.scale(-1), p1);
        Ray ray2 = new Ray(_direction.scale(-1), p2);
        Point3D planePoint1 = lightSourcePlane.findGeoIntersections(ray1).get(0).point;
        Point3D planePoint2 = lightSourcePlane.findGeoIntersections(ray2).get(0).point;

        Vector x = planePoint2.subtract(planePoint1).normalize();
        Vector y = _direction.crossProduct(x);

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
}


