package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector _direction;

    public SpotLight(Vector direction,Color intensity, Point3D position) {
        super(intensity, position);
        _direction = direction;
    }

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

    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }
}
