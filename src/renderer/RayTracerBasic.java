package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import javax.naming.LimitExceededException;
import java.util.LinkedList;
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

    //Two constants for stopping conditions in the recursion of transparencies / reflections
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;
    int MIN_SHADOW_SAMPLES = 0;

    /**
     * setter to the number of ray tracing
     * @param MIN_SHADOW_SAMPLES
     * @return this - builder pattern
     */
    public RayTracerBasic setMIN_SHADOW_SAMPLES(int MIN_SHADOW_SAMPLES) {
        this.MIN_SHADOW_SAMPLES = MIN_SHADOW_SAMPLES;
        return this;
    }


    /**
     * constructor
     * @param myScene
     */
    public RayTracerBasic(Scene myScene) {
        super(myScene);
    }


    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint=findClosestIntersection(ray);
        return closestPoint==null? _scene._background:calcColor(closestPoint,ray);
    }

    /**
     * recursive calcColor
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));

    }

    /**
     * calculates the color of a 3D point in the scene
     * call to the recursive func
     * @param point point of intersection
     * @return point's color
     */
    public Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }


    /**
     * calculate and adding the specular and diffusive reflections
     * @param geoPoint
     * @param ray
     * @return Color after calculate Local effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray,double k) {
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
                double ktr = transparency(lightSource, l, n, geoPoint);
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity))
                            .add(calcSpecular(ks, l, n, v, nShininess, lightIntensity));
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
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(lightRay);

        //checking if the intersections point are not behind the light source. if so
        //there is no shading
        if (intersections == null) return true;
        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial()._kT == 0)
                return false;
        }
        return true;

    }

    /**
     *calculate global effects Kt and Kr(reflections and refractions)
     * by reflected and refracted rays
     * @param gp
     * @param v
     * @param level
     * @param k
     * @return Color in geoPoint after reflections and refractions effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material._kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(n,gp.point,v), level, material._kR, kkr);
        double kkt = k * material._kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(n,gp.point,v), level, material._kT, kkt));
        return color;
    }


    /**
     * using calcColor function to scale the color in a closest geoPoint kT and kR
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? _scene._background : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }


    /**
     * point.add(n.scale(v.dotProduct(n)*DELTA))
     * @param n
     * @param point
     * @param v
     * @return new ray- moving head of ray by n
     */
    private Ray constructRefractedRay(Vector n,Point3D point,Vector v) {
        return new Ray(point,v,n);
    }

    /**
     *  r = v - 2 * (v * n) * n
     * @param n
     * @param point
     * @param v
     * @return
     */
    private Ray constructReflectedRay(Vector n,Point3D point,Vector v) {
        double s=v.dotProduct(n)*2;
        Vector r=v.subtract(n.scale(s));
        return new Ray(point,r,n);
    }

    /**
     * finding the closest intersections to the ray head  in the scene elements
     * @param ray
     * @return closest geoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){
        var points=_scene._geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
   /** private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        List<Point3D> lightPoints = light.randomPoints(l);
        double total = 0;
        for (Point3D point: lightPoints) {
            double lightDistance = light.getDistance(geopoint.point);
            var intersections = _scene._geometries.findGeoIntersections(new Ray(geopoint.point,point.subtract(geopoint.point),n));
            if (intersections == null){
                total = total +1.0;
            }
            else {
                double ktr = 1.0;
                for (GeoPoint gp : intersections) {
                    if (Util.alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                        System.out.println( ktr+" " +gp.geometry.getClass());
                        ktr = ktr *gp.geometry.getMaterial()._kT;
                        System.out.println(ktr);
                        if (ktr < MIN_CALC_COLOR_K) break;
                    }
                }
            }
        }

        return total / lightPoints.size();
    }**/

    /**
     * calculates the intesity of light coming from light source to geo point
     * @param light light source
     * @param l light source direction
     * @param n normal to the geometry which contains the geopoint
     * @param geoPoint checked point - whether lighten or shaded
     * @return value 0%1 represent percentage of lighe approaching to the point from the light source
     */
   private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
       List<Ray> lightRays = new LinkedList<>();
       List<Point3D> lightPoints = new LinkedList<>();
       if ( MIN_SHADOW_SAMPLES != 0){                 //activate soft shadow
          lightPoints = light.lightPoints(l, MIN_SHADOW_SAMPLES);
       }

       double lightDistance = light.getDistance(geoPoint.point);
       //directional light - no position,  or no need to active soft shadow (lightPoints size =0)
       if (lightPoints ==null || lightPoints.size() == 0){
           lightRays.add(new Ray(geoPoint.point, l.scale(-1),n));
       }
       //light point with position in the 3D model -
       //construct shadow rays to many point in the light source.Distribution as uniform as possible.
       else{
           for (Point3D lightPoint: lightPoints){
               lightRays.add(new Ray(geoPoint.point, lightPoint.subtract(geoPoint.point).normalize(),n));
           }
       }

       double ktrTotal = 0.0;
       //finding intersection of the shadow rays
       for (Ray lightRay: lightRays) {
           var intersections = _scene._geometries.findGeoIntersections(lightRay);
           if (intersections == null) {          // no shadow
               ktrTotal += 1.0;
           }
           else {                               //calculate shadow influence
               double ktr = 1.0;
               for (GeoPoint gp : intersections) {
                   if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                       ktr *= gp.geometry.getMaterial()._kT;
                       if (ktr < MIN_CALC_COLOR_K) {
                           break;
                       }
                       ;
                   }
               }
               ktrTotal += ktr;
           }
       }
       return ( ktrTotal /  lightRays.size() );
   }
}


