package primitives;

public class ColorRay {
    Ray _ray;
    /**
     * 'color of the rau' - the color that the ray sees, calculated by ray tracer basic
     */
    Color _color;

    public ColorRay(Ray ray, Color color) {
        _ray = ray;
        _color = color;
    }

    public ColorRay(Ray ray) {
        _ray = ray;
        _color = null;
    }

    public ColorRay setColor(Color color) {
        _color = color;
        return this;
    }

    public Color getColor() {
        return _color;
    }

    public Ray getRay() {
        return _ray;
    }

    public static void mult(ColorRay ray) {
        ray.setColor(new Color(7,7,7));
    }

}
