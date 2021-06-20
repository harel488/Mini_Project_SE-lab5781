package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

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
    int MIN_SHADOW_SAMPLES;
    boolean MULTISAMPLING=false;

    public void setMAX_LEVEL(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
    }

    public RayTracerBasic setMULTISAMPLING() {
        MULTISAMPLING = true;
        return this;
    }


    /**
     * setter to the number of shadow rays
     * @param MIN_SHADOW_SAMPLES min shadow rays to build
     * @return this - builder pattern
     */
    public RayTracerBasic setMIN_SHADOW_SAMPLES(int MIN_SHADOW_SAMPLES) {
        this.MIN_SHADOW_SAMPLES = MIN_SHADOW_SAMPLES;
        return this;
    }


    /**
     * constructor
     * @param myScene - all scene elements and geometries
     */
    public RayTracerBasic(Scene myScene) {
        super(myScene);
    }


    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint=findClosestIntersection(ray);
        return closestPoint==null? _scene._background:calcColor(closestPoint,ray);
    }

    @Override
    public Color traceRays(List<ColorRay> rays) {
        if(MULTISAMPLING){
            return traceBeam(rays,0);
        }
        Color col = Color.BLACK;
        for (ColorRay ray:rays) {
                col = col.add(traceRay(ray.getRay()));
        }
        return  col.reduce(rays.size());
    }

    int MAX_LEVEL =4;
    public Color traceBeam(List<ColorRay> colorRays, int level) {
        for (ColorRay colorRay: colorRays) {
            if (colorRay.getColor() == null) {
                colorRay.setColor(traceRay(colorRay.getRay()));
            }
        }
        Color leftDown = colorRays.get(0).getColor();
        Color leftUp = colorRays.get(1).getColor();
        Color rightDown = colorRays.get(2).getColor();
        Color rightUp = colorRays.get(3).getColor();

        if(level == MAX_LEVEL  ||  (  (leftDown.same(leftUp))  && leftDown.same(rightDown)  && leftDown.same(rightUp) )  ){
            return ( leftDown.add(leftUp).add(rightDown).add(rightUp) ).reduce(colorRays.size());
        }
        else {
            Point3D c0 = colorRays.get(0).getRay().getDirection().getHead();   //corner left down
            Point3D c1 = colorRays.get(1).getRay().getDirection().getHead();   //corner left up
            Point3D c2 = colorRays.get(3).getRay().getDirection().getHead();   //corner right up

            Point3D p0 = colorRays.get(0).getRay().getPoint();
            Vector right = c2.subtract(c1).normalize();
            Vector down = c0.subtract(c1).normalize();

            double rightLength = c2.distance(c1);
            double downLength = c0.distance(c1);
            Ray middleUp = new Ray(new Vector(c1.add(right.scale(rightLength * 0.5))), p0);
            Ray middleDown = new Ray(new Vector(c0.add(right.scale(rightLength * 0.5))), p0);
            Ray middleLeft = new Ray(new Vector(c1.add(down.scale(downLength * 0.5))), p0);
            Ray middleRight = new Ray(new Vector(c2.add(down.scale(rightLength * 0.5))), p0);
            Ray center = new Ray(new Vector(c1.add(right.scale(rightLength * 0.5)).add(down.scale(downLength * 0.5))), p0);

            //calculating ray colors before recursive call to avoid unnecessary recalculations
            ColorRay middle_up = new ColorRay(   middleUp,traceRay(middleUp)  );
            ColorRay middle_down = new ColorRay(middleDown,traceRay(middleDown));
            ColorRay middle_left = new ColorRay(middleLeft,traceRay(middleLeft));
            ColorRay middle_right = new ColorRay(middleRight,traceRay(middleRight));
            ColorRay middle = new ColorRay(center,traceRay(center));

            List<ColorRay> leftUpBeam = new LinkedList<>();
            leftUpBeam.add(middle_left);
            leftUpBeam.add(colorRays.get(1));
            leftUpBeam.add(middle_up);
            leftUpBeam.add(middle);
            List<ColorRay> rightUpBeam = new LinkedList<>();
            rightUpBeam.add(middle);
            rightUpBeam.add(middle_up);
            rightUpBeam.add(colorRays.get(3));
            rightUpBeam.add(middle_right);

            List<ColorRay> leftDownBeam = new LinkedList<>();
            leftDownBeam.add(colorRays.get(0));
            leftDownBeam.add(middle_left);
            leftDownBeam.add(middle);
            leftDownBeam.add(middle_down);

            List<ColorRay> RightDownBeam = new LinkedList<>();
            RightDownBeam.add(middle_down);
            RightDownBeam.add(middle);
            RightDownBeam.add(middle_right);
            RightDownBeam.add(colorRays.get(2));

            level++;
            return traceBeam(leftUpBeam,level).scale(0.25)
                    .add(traceBeam(rightUpBeam,level).scale(0.25))
                    .add(traceBeam(leftDownBeam,level).scale(0.25))
                    .add(traceBeam(RightDownBeam,level).scale(0.25));
        }
    }




    /**
     * recursive calcColor -
     * calcColor is a recursive function to produce an effect of
     * reflection and refraction.
     * the deep of The recursion depth is blocked from above by maximum levels defined above.
     * @param intersection - the current geometry point
     * @param ray - intersected ray
     * @param level - level of recursive reflection and refraction calculate
     * @return if level > 1 : emission + local effects +global effects.
     * else : only emission + local effects
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));

    }

    /**
     * calculates the color of a 3D point in the scene
     * @param point point of intersection
     * @return point's color
     */
    public Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }


    /**
     * calculate and adding the specular and diffusive reflections
     * @param geoPoint - the current point on geometry
     * @param ray - intersected ray
     * @return Color after calculate Local effects
     */
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
                double ktr = transparency(lightSource, l, n, geoPoint);
                Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity))
                        .add(calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }

        }

        return color;

    }

    /**
     * calculate the specular effect from the body
     * The light decreases exponentially as the angle between V and R increases
     * @param ks - the geometry specular Coefficient of exclusion
     * @param l - the light ray that strikes the geometry
     * @param n - normal to the geometry
     * @param v - camera vTO
     * @param nShininess
     * @param lightIntensity
     * @return Ks* (max(0,-v*r))^nShininess
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double nl = Util.alignZero(n.dotProduct(l));
        Vector R = l.add(n.scale(-2 * nl)); // The reflection vector of light
        double VR = -1 * Util.alignZero(R.dotProduct(v));
        if (VR <= 0) {
            return Color.BLACK;
        }
        return lightIntensity.scale(ks * Math.pow(Math.max(0, VR), nShininess));
    }

    /**
     * calculate the diffuse effect from the body
     * the diffuse is maximum in the normal direction and fades towards the sides.
     * @param kd - the geometry diffusive Coefficient of exclusion
     * @param l -  the light ray that strikes the geometry
     * @param n - normal to the geometry
     * @param lightIntensity
     * @return KD * |nl| *Il (lightIntensity)
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double nl = Math.abs(Util.alignZero(n.dotProduct(l))); // cos of angle between them
        return lightIntensity.scale(nl * kd);
    }



    /**
     * checks if there are no interferences in the way of the light source to the point. if there are
     * other geometries in the middle than there wouldn't be influence of this light source.
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
     * the reflection and refraction calculating
     * is performed by recursion with stop conditions depending on MAX_CALC_COLOR_LEVEL
     * @param gp - the current point on the geometry
     * @param v - intersected ray direction
     * @param level - level for the recursive calling from calcColor()
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
     * using calcColor function to scale the color in a closest geoPoint with kT and kR
     * @param ray - reflected/refracted ray
     * @param level -level for the recursive calling from calcColor()
     * @param kx - kt/kr of material
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? _scene._background : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }


    /**
     * constructing the refracted ray following transparency
     * the ray point is in the intersection point moving by n
     * and the direction of the new ray is the same direction
     * point.add(n.scale(v.dotProduct(n)*DELTA))
     * @param n - the normal to the geometry
     * @param point - intersection point
     * @param v - the intersected ray direction
     * @return new ray- moving head of ray by n vector
     */
    private Ray constructRefractedRay(Vector n,Point3D point,Vector v) {
        return new Ray(point,v,n);
    }

    /**
     *  constructing the reflected ray when the geometry Has a mirror effect
     *  the calculate is very similar to specular light calculate
     * @param n -  the normal to the geometry
     * @param point -intersection point
     * @param v - the intersected ray direction
     * @return r = v - 2 * (v * n) * n
     */
    private Ray constructReflectedRay(Vector n,Point3D point,Vector v) {
        double s=v.dotProduct(n)*2;
        Vector r=v.subtract(n.scale(s));
        return new Ray(point,r,n);
    }

    /**
     * finding the closest intersections to the ray head in the scene elements
     * @param ray
     * @return closest geoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){
        var points=_scene._geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }



    /**
     * calculates the intensity of light coming from light source to geo point
     * @param light light source
     * @param l light source direction
     * @param n normal to the geometry which contains the geoPoint
     * @param geoPoint checked point - whether lighten or shaded
     * @return value 0%1 represent percentage of light approaching to the point from the light source
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        List<Ray> lightRays = new LinkedList<>();
        List<Point3D> lightPoints = new LinkedList<>();
        if ( MIN_SHADOW_SAMPLES != 0){                 //activate soft shadow
            lightPoints = light.lightPoints(l, MIN_SHADOW_SAMPLES);
        }

        double lightDistance = light.getDistance(geoPoint.point);
        //directional light - no position,  or no need to active soft shadow (lightPoints size =0)
        if (lightPoints == null || MIN_SHADOW_SAMPLES == 0){
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
                    }
                }
                ktrTotal += ktr;
            }
        }
        return ( ktrTotal /  lightRays.size() );
    }
}