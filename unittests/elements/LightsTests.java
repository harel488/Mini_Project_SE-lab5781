package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000);
    private Camera camera3 = new Camera(new Point3D(0, 0, 220),
                                        new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                                       .setViewPlaneSize(200, 200) //
                                       .setDistance(200);


    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50)
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

    private static Geometry sphere1 = new Sphere(new Point3D(0, 0, -100), 50);
    private static Geometry sphere2 = new Sphere(new Point3D(100, 0, -100), 50);
    private static Geometry sphere3 = new Sphere(new Point3D(-100, 0, -100), 50);




    private static Geometry planeA = new Plane(new Point3D(500,0,0),
                                               new Vector(new Point3D(1,0,0)));
    private static Geometry planeB = new Plane(new Point3D(-500,0,0),
            new Vector(new Point3D(1,0,0)));
    private static Geometry planeC = new Plane(new Point3D(0,0,-20),
            new Vector(new Point3D(0,0,1)));



    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1._geometries.add(sphere);
        scene1._lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1._geometries.add(sphere);
        scene1._lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))
                .setkL(0.00001).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1._geometries.add(sphere);
        scene1._lights.add(new SpotLight(new Vector(1, 1, -2), new Color(500, 300, 0), new Point3D(-50, -50, 50))
                .setkL(0.00001).setkQ(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
        scene2._lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2._lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setkL(0.0005).setkQ(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2._lights.add(new SpotLight(new Vector(-2, -2, -1), new Color(500, 250, 250), new Point3D(10, -10, -130))
                .setkL(0.0001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of three spheres and two two planes stand to the right and left of the sphres,
     * lighted by a directional light in front of the scene, point light on top of if, and spot light
     * a little below the sphere pointing up
     */
    @Test
    public void myTest() {
        scene2._geometries.add(sphere1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                sphere2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)) ,
                sphere3.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                planeA.setMaterial(new Material().setkD(0.9).setkS(0.1).setnShininess(100)),
                planeB.setMaterial(new Material().setkD(0.9).setkS(0.1).setnShininess(100)));

        scene2._lights.add(new DirectionalLight(new Color(java.awt.Color.WHITE), new Vector(new Point3D(0,0,-1))));
        scene2._lights.add(new PointLight(new Color(java.awt.Color.RED),new Point3D(0,80,-100)).setkL(0.0005));
        scene2._lights.add(new SpotLight(new Vector(0,1,0),
                new Color(java.awt.Color.YELLOW),new Point3D(0,-100,-100)));


        ImageWriter imageWriter = new ImageWriter("myTest", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera3) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

}