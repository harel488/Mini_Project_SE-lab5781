package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private Point3D _position;
    private double _kC = 1;
    private double _kL = 0;
    private double _kQ = 0;

    public PointLight setkC(double kC) {
        this._kC = kC;
        return this;
    }

    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this._position = position;
    }

    public PointLight setkL(double kL) {
        this._kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this._kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
    }

    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }
}
