package elements;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SpotLightTest {
    @Test
    public void test12() {
         Scene scene1 = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
         Camera camera1 = new Camera(new Point3D(0, 0, 100), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150) //
                .setDistance(100);
        Geometry sphere = new Sphere(new Point3D(0,0,-30),10).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300));;
        Geometry plane = new Plane(new Point3D(0,0,-50),new Vector(0,0,1)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300));;

        SpotLight spot = new SpotLight(new Vector(0, 0, -1),
                new Color(400, 240, 0),
                new Point3D(0, 0, 0));
        scene1._geometries.add(sphere,plane);
        scene1._lights.add(spot);

        List<Point3D> lst = spot.randomPoints(new Vector(0, 0, -1));

        ImageWriter imageWriter = new ImageWriter("mytest12", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();

    }
}