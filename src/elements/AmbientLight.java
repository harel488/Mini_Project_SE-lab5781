package elements;

import primitives.Color;

/**
 * Ambient Light Class
 */
public class AmbientLight extends Light {


    /**
     * Constructor
     * @param Ia color
     * @param Ka intensity
     */
    public AmbientLight(Color Ia, double Ka) {
        super( Ia.scale(Ka));
    }
    public AmbientLight() {
        super(Color.BLACK);
    }




}