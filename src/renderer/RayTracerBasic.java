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
    /**
     * length of the shifting - moving the point out of the geometry in direction of the normal
     * and size of DELTA
     */

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
            if(unshaded(lightSource.getL(geoPoint.point),
                    geoPoint.geometry.getNormal(geoPoint.point),geoPoint,lightSource)) {
                if (nl * nv > 0) { // sign(nl) == sing(nv)
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
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


    private static final double DELTA = 0000001;
    /**
     * checks if there are no interferences in the way of the light source to the point. if there are
     * other geometries in the midle than there wouldn't be influence of this light source.
     * @param l direction of the light source
     * @param n normal vector to the geometry in the current point
     * @param geoPoint the point we check whether lighten or not
     * @return true if no shaded - the point is lighten by the light source. else false.
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint geoPoint, LightSource lightSource){
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA :  -DELTA);
        Point3D point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(lightDirection,point);
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(lightRay);

        //checking if the intersections point are not behind the light source. if so
        //there is no shading
        if(intersections!=null) {
            double distance = lightSource.getDistance(geoPoint.point);
            for (GeoPoint geo_point : intersections) {
                if (geo_point.point.distance(point) < distance)
                    return false;
            }
        }
        return true;
    };
}
