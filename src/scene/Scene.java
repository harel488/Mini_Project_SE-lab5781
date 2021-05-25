package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * represents the 3D model - including the 3D geometries and lighting
 */
public class Scene {

    private final String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(Color.BLACK,1.d); ;
    public Geometries _geometries = null;
    public List<LightSource> _lights=new LinkedList<LightSource>();

    //constructor
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /////    setters (chaining method)   ///////
    public Scene setBackground(Color background) {
        this._background = background;
        return  this;
    }

    public Scene setAmbientLight(AmbientLight ambientlight) {
        this._ambientLight = ambientlight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this._geometries = geometries;
        return  this;
    }

    public Scene setLights(List<LightSource> lights) {
        this._lights = lights;
        return this;
    }
}