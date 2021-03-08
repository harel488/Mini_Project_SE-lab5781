//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package primitives;

public class Ray {
    private Vector _direction;
    private Point3D _point;

    public Ray(Vector direction, Point3D point) {
        direction.normalize();
        this._direction = direction;
        this._point = point;
    }

    public Vector getDirection() {
        return this._direction;
    }

    public Point3D getPoint() {
        return this._point;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Ray ray = (Ray)o;
            return this._direction.equals(ray._direction) && this._point.equals(ray._point);
        } else {
            return false;
        }
    }

    public String toString() {
        return "_direction=" + this._direction + ", _point=" + this._point;
    }
}