package primitives;

public class Material {
    public double _kD = 0;
    public double _kS = 0;
    public int _nShininess = 0;


    public Material setkD(double kD) {
        _kD = kD;
        return this;
    }

    public Material setkS(double kS) {
        _kS = kS;
        return this;
    }

    public Material setnShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }
}
