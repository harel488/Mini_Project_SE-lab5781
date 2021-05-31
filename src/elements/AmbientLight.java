package elements;

import primitives.Color;

/**
 * Ambient Light Class
 */
public class AmbientLight extends Light {


    /**
     * Constructor
     * @param Ia color
     * @param Ka intensity in percentages(between 0 and 1)
     */
    public AmbientLight(Color Ia, double Ka) {
        super( Ia.scale(Ka));
    }

    /**
     * default constructor - color black and no attenuation
     */
    public AmbientLight() {
        super(Color.BLACK);
    }




}