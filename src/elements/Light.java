package elements;

import primitives.Color;

class Light {

    private Color _intensity = Color.BLACK;

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    public Color getIntensity() {
        return _intensity;
    }
}
