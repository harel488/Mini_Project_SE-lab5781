package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.rmi.UnexpectedException;
import java.util.MissingResourceException;

/**
 * generates the image and holds all the relevant data of the scene
 */
public class Render {

    Camera _camera;
    ImageWriter _imageWriter;
    RayTracerBase _rayTracer;


    ///    setters (chaining method) //////
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }


    /**
     * paints all the pixels of the picture
     */
    public void renderImage(){
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (_rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            int Ny = _imageWriter.getNy();
            int Nx = _imageWriter.getNx();
            for (int j = 0; j < Ny; j++) {
                for (int i = 0; i < Nx; i++) {
                    Ray ray = _camera.constructRayThroughPixel(Nx, Ny, j, i);
                    Color pixelColor = _rayTracer.traceRay(ray);
                    _imageWriter.writePixel(j, i, pixelColor);
                }

            }
        }
        catch (MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     * prints grid - every cube size is interval * interval
     * @param interval space between grid's line
     * @param color color of the grid
     */
    public void printGrid(int interval, Color color) {
        for (int i = 0; i < _imageWriter.getNy(); i++) {
            for (int j = 0; j < _imageWriter.getNx(); j++) {
                if (j % interval == 0 || i % interval == 0) {
                    _imageWriter.writePixel(i, j, color);
                }
                /**_imageWriter.writeToImage();*/
            }
        }
    }

    /**
     * produces png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public void writeToImage(){
        _imageWriter.writeToImage();
    }

}
