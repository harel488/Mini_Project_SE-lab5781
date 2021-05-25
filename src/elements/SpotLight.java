package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector _direction;

    public SpotLight(Vector direction,Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _direction = direction;
    }
}
