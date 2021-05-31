package elements;

import primitives.Color;


/**
 * represent all light - include amnient light and all light sources
 */
class Light {

    protected Color _intensity = Color.BLACK;

    /**
     * constructor - gets light intensity
     * @param intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     *
     * @return original light source intensity (zero distance from the light source)
     */
    public Color getIntensity() {
        return _intensity;
    }
}
