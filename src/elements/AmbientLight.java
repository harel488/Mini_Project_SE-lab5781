package elements;

import primitives.Color;

/**
 * Ambient Light Class
 */
public class AmbientLight {
    /**
     *the intensity of the ambient light color
     */
    final private Color _intensity;

    /**
     * Constructor
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, double Ka) {
        _intensity = Ia.scale(Ka);
    }

    /**
     * getter for intensity color
     * @return intensity
     */
    public Color getIntensity() {
        return _intensity;
    }

}