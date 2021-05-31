package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;


/**
 * calculates the color of the intersection point between camera rays and geometries in the 3D model
 */
public class RayTracerBasic extends RayTracerBase {
    public RayTracerBasic(Scene myScene) {
        super(myScene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return _scene._background;
        } else {
            GeoPoint closest = ray.findClosestGeoPoint(intersections);
            return calcColor(closest, ray);
        }

    }

    /**
     * calculates the color of a 3D point in the scene
     *
     * @param geoPoint point of intersection
     * @return point's color
     */
    public Color calcColor(GeoPoint geoPoint, Ray ray) {
        return _scene._ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material._nShininess;
        double kd = material._kD, ks = material._kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;

    }

    /**
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return Ks* (max(0,-v*r))^nShininess
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double nl = Util.alignZero(n.dotProduct(l));
        Vector R = l.add(n.scale(-2 * nl));
        double VR = -1 * Util.alignZero(R.dotProduct(v));
        if (VR <= 0) {
            return Color.BLACK;
        }
        return lightIntensity.scale(ks * Math.pow(Math.max(0, VR), nShininess));
    }

    /**
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return KD * |nl| *Il (lightIntensity)
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double nl = Math.abs(Util.alignZero(n.dotProduct(l)));
        return lightIntensity.scale(nl * kd);
    }
}
