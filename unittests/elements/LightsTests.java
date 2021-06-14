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
    private Camera camera3 = new Camera(new Point3D(0, 50, 160),
                                        new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                                       .setViewPlaneSize(200, 200) //
                                       .setDistance(110);


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
     * a little below the sphere point up
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


    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void miniProject01() {
        Geometry door1 = new Polygon(new Point3D(0,31,0),  new Point3D(0,61,0),
                new Point3D(15,61,0),  new Point3D(15,31,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));

        Geometry middle1 = new Polygon(new Point3D(0,0,0),  new Point3D(-1,0,0),
                new Point3D(-1,61,0),  new Point3D(0,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300));

        Geometry door2 = new Polygon(new Point3D(-1,31,0),  new Point3D(-1,61,0),
                new Point3D(-16,61,0),  new Point3D(-16,31,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));
        Geometry middle2 = new Polygon(new Point3D(-16,0,0),  new Point3D(-17,0,0),
                new Point3D(-17,61,0),  new Point3D(-16,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        /////
        Geometry middle3 = new Polygon(new Point3D(15,31,0),  new Point3D(15,29,0),
                new Point3D(-16,29,0),  new Point3D(-16,31,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry middle4 = new Polygon(new Point3D(-32,0,0),  new Point3D(-33,0,0),
                new Point3D(-33,61,0),  new Point3D(-32,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry door3 = new Polygon(new Point3D(0,30,0),  new Point3D(0,0,0),
                new Point3D(15,0,0),  new Point3D(15,30,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));
        Geometry door4 = new Polygon(new Point3D(-1,30,0),  new Point3D(-1,0,0),
                new Point3D(-16,0,0),  new Point3D(-16,30,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));

        Geometry door5 = new Polygon(new Point3D(-17,61,0),  new Point3D(-17,0,0),
                new Point3D(-32,0,0),  new Point3D(-32,61,0))
                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setkR(1));
        Geometry door6 = new Polygon(new Point3D(-33,61,0),  new Point3D(-33,0,0),
                new Point3D(-48,0,0),  new Point3D(-48,61,0))
                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setkR(1));
        Geometry floor= new Plane(new Point3D(0,0,0),new Vector(0,1,0))
                .setEmission(new Color(java.awt.Color.GRAY))
                .setMaterial(new Material().setkD(0.4).setkS(0.05).setnShininess(100));
        Geometry roof= new Plane(new Point3D(0,80,0),new Vector(0,1,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.3))
                .setMaterial(new Material().setkD(0.7));
        Geometry wallRight= new Plane(new Point3D(35,0,0),new Vector(1,0,0))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));
        Geometry wallLeft= new Plane(new Point3D(-100,0,0),new Vector(1,0,0))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry wallBehind= new Plane(new Point3D(0,0,200),new Vector(0,0,1))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));
        Geometry wallFront= new Plane(new Point3D(0,0,-20),new Vector(0,0,1))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));

        Geometry handle1 = new Sphere(new Point3D(2,33,1),1)
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle2 = new Sphere(new Point3D(2,28,1),1)
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle3 = new Sphere(new Point3D(-14,33,1),1)
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle4 = new Sphere(new Point3D(-14,28,1),1)
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));


        //foot1
        Geometry foot11 = new Polygon(new Point3D(-40,0,60),new Point3D(-40,0,62),
                new Point3D(-40,20,62),new Point3D(-40,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot12 = new Polygon(new Point3D(-40,0,62),new Point3D(-42,0,62),
                new Point3D(-42,20,62),new Point3D(-40,20,62))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot13 = new Polygon(new Point3D(-42,0,60),new Point3D(-42,0,62),
                new Point3D(-42,20,62),new Point3D(-42,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot14 = new Polygon(new Point3D(-40,0,60),new Point3D(-42,0,60),
                new Point3D(-42,20,60),new Point3D(-40,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        //foot2

        Geometry foot21 = new Polygon(new Point3D(-40,0,80),new Point3D(-40,0,82),
                new Point3D(-40,20,82),new Point3D(-40,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot22 = new Polygon(new Point3D(-40,0,82),new Point3D(-42,0,82),
                new Point3D(-42,20,82),new Point3D(-40,20,82))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot23 = new Polygon(new Point3D(-42,0,80),new Point3D(-42,0,82),
                new Point3D(-42,20,82),new Point3D(-42,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot24 = new Polygon(new Point3D(-40,0,80),new Point3D(-42,0,80),
                new Point3D(-42,20,80),new Point3D(-40,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        //foot3
        Geometry foot31 = new Polygon(new Point3D(-70,0,60),new Point3D(-70,0,62),
                new Point3D(-70,20,62),new Point3D(-70,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot32 = new Polygon(new Point3D(-70,0,62),new Point3D(-72,0,62),
                new Point3D(-72,20,62),new Point3D(-70,20,62))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot33 = new Polygon(new Point3D(-72,0,60),new Point3D(-72,0,62),
                new Point3D(-72,20,62),new Point3D(-72,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot34 = new Polygon(new Point3D(-70,0,60),new Point3D(-72,0,60),
                new Point3D(-72,20,60),new Point3D(-70,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        //foot4
        Geometry foot41 = new Polygon(new Point3D(-70,0,80),new Point3D(-70,0,82),
                new Point3D(-70,20,82),new Point3D(-70,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot42 = new Polygon(new Point3D(-70,0,82),new Point3D(-72,0,82),
                new Point3D(-72,20,82),new Point3D(-70,20,82))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot43 = new Polygon(new Point3D(-72,0,80),new Point3D(-72,0,82),
                new Point3D(-72,20,82),new Point3D(-72,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot44 = new Polygon(new Point3D(-70,0,80),new Point3D(-72,0,80),
                new Point3D(-72,20,80),new Point3D(-70,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Geometry plateDown = new Polygon(new Point3D(-37,20,58),new Point3D(-37,20,84),
                new Point3D(-75,20,84),new Point3D(-75,20,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateUp = new Polygon(new Point3D(-37,21,58),new Point3D(-37,21,84),
                new Point3D(-75,21,84),new Point3D(-75,21,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide1 = new Polygon(new Point3D(-37,20,58),new Point3D(-37,21,58),
                new Point3D(-75,21,58),new Point3D(-75,20,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide2 = new Polygon(new Point3D(-37,20,58),new Point3D(-37,21,58),
                new Point3D(-37,21,84),new Point3D(-37,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide3 = new Polygon(new Point3D(-37,20,84),new Point3D(-37,21,84),
                new Point3D(-75,21,84),new Point3D(-75,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide4 = new Polygon(new Point3D(-75,20,58),new Point3D(-75,21,58),
                new Point3D(-75,21,84),new Point3D(-75,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        //decor outer
        Geometry triangle1 = new Triangle((new Point3D(-56,25,71)),new Point3D(-53,21,69),
                new Point3D(-53,21,73))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle2 = new Triangle((new Point3D(-56,25,71)),new Point3D(-53,21,73),
                new Point3D(-59,21,73))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle3 = new Triangle((new Point3D(-56,25,71)),new Point3D(-59,21,73),
                new Point3D(-59,21,69))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle4 = new Triangle((new Point3D(-56,25,71)),new Point3D(-59,21,69),
                new Point3D(-53,21,69))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        //decor inner
        Geometry triangle5 = new Triangle((new Point3D(-56,30,71)),new Point3D(-50,21,66),
                new Point3D(-50,21,76))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setkT(0.8));
        Geometry triangle6 = new Triangle((new Point3D(-56,30,71)),new Point3D(-50,21,76),
                new Point3D(-62,21,76))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setkT(0.8));
        Geometry triangle7 = new Triangle((new Point3D(-56,30,71)),new Point3D(-62,21,76),
                new Point3D(-62,21,66))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setkT(0.8));
        Geometry triangle8 = new Triangle((new Point3D(-56,30,71)),new Point3D(-62,21,66),
                new Point3D(-50,21,66))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setkT(0.8));

//wallSpheres
        Geometry sphere1 = new Sphere(new Point3D(35, 30, 50), 4)
                .setEmission(new Color(java.awt.Color.RED).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));
        Geometry sphere2 = new Sphere(new Point3D(35, 30, 75), 4)
                .setEmission(new Color(java.awt.Color.ORANGE).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));
        Geometry sphere3 = new Sphere(new Point3D(35, 30, 100), 4)
                .setEmission(new Color(java.awt.Color.GREEN).scale(0.3))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));


        scene1._geometries.add(door1,middle1,middle2,door2,door3,door4,door5,middle3,floor,wallBehind,handle1,handle2,handle3,handle4,roof
                ,wallRight,wallLeft,foot11,foot12,foot13,foot14,foot21,foot22,foot23,foot24,foot31,foot32,foot33,foot34,foot41,foot42,foot43,foot44,
                plateUp,plateDown,plateSide1,plateSide2,plateSide3,plateSide4,middle4,door6,triangle1,triangle2,triangle3,triangle4,
                triangle5,triangle6,triangle7,triangle8,wallFront,sphere1,sphere2,sphere3);
        scene1._lights.add(new PointLight(new Color(java.awt.Color.YELLOW)
                .add(new Color(java.awt.Color.YELLOW)).scale(0.2), new Point3D(0, 50, 40),3));
        scene1._lights.add(new SpotLight(new Vector(0,-1,0),new Color(java.awt.Color.orange), new Point3D(-56, 50, 71),3));
        scene1._lights.add(new SpotLight(new Vector(1.5,-1,0),new Color(java.awt.Color.CYAN).scale(0.8), new Point3D(10, 55, 75),3));

        ImageWriter imageWriter = new ImageWriter("test1", 300, 300);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera3) //
                .setRayTracer(new RayTracerBasic(scene1)
                        .setMIN_SHADOW_SAMPLES(100));
        render.renderImage();
        render.writeToImage();
    }



}