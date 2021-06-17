package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * represents the point of view of the scene
 */
public class Camera {
    final private Point3D _p0;
    final private Vector _vUp;
    final private Vector _vTo;
    final private Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;
    private int MULTI_SAMPLING_SAMPLES;

    public Camera setMULTI_SAMPLING_SAMPLES(int MULTI_SAMPLING_SAMPLES) {
        this.MULTI_SAMPLING_SAMPLES = MULTI_SAMPLING_SAMPLES;
        return this;
    }


    /**
     * constructor
     *
     * @param p0   - Camera lens center point
     * @param vUp- Vector in the direction up from the camera
     * @param vTo  - Vector in direction from camera to view plane
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vUP and vTO are not orthogonal");
        }
        _p0 = p0;
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();
        _vRight = _vTo.crossProduct(_vUp);
    }

    //*******Getters***********
    public Point3D getP0() {
        return _p0;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
    }
    //*********************************

    /**
     * setter for the size of view plane
     * chaining method
     *
     * @param width  actual width of the view plane
     * @param height actual height of the view plane
     * @return
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * setter for the distance from camera to view plane
     * chaining method
     *
     * @param distance distance from the camera to the view plane
     * @return
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * generates rays from ray to center of a pixel on the view plane
     *
     * @param nX number of pixels on a row
     * @param nY number of pixels on a column
     * @param j  position of a pixel on a row
     * @param i  position of a pixel on a column
     * @return the constructed ray that goes the center of the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;

        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(Yi));
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(Vij, _p0);
    }

    public List<Ray> constructGridThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = _p0.add(_vTo.scale(_distance));
        //finding center point of pixel
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;

        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(Yi));
        }

        //finding all the points required and build ray to each one
        List<Ray> multiSamplingRays = new LinkedList<Ray>();
        double moves = (MULTI_SAMPLING_SAMPLES - 1) / 2;

        double xSize = _width / nX / (MULTI_SAMPLING_SAMPLES-1);
        double ySize = _height / nY / (MULTI_SAMPLING_SAMPLES-1);


        Point3D point1 = Pij.add(_vRight.scale(-0.5*xSize)).add(_vUp.scale(-0.5*ySize));
        Point3D point2 = Pij.add(_vRight.scale(-0.5*xSize)).add(_vUp.scale(0.5*ySize));
        Point3D point3 = Pij.add(_vRight.scale(0.5*xSize)).add(_vUp.scale(-0.5*ySize));
        Point3D point4 = Pij.add(_vRight.scale(0.5*xSize)).add(_vUp.scale(0.5*ySize));
        multiSamplingRays.add(new Ray(point1.subtract(_p0), _p0));
        multiSamplingRays.add(new Ray(point2.subtract(_p0), _p0));
        multiSamplingRays.add(new Ray(point3.subtract(_p0), _p0));
        multiSamplingRays.add(new Ray(point4.subtract(_p0), _p0));

/**
         for (double k = -moves; k <= moves; k +=1.0) {
             Point3D point = Pij;
             if (k != 0) {
                 point = point.add(_vRight.scale(xSize * k));
             }
             for (double l = -moves; l <= moves; l += 1.0) {
                 if (l != 0) {
                     point = point.add(_vUp.scale(ySize * l));
                 }
                 multiSamplingRays.add(new Ray(point.subtract(_p0), _p0));

             }

         }**/
        return multiSamplingRays;


    }
}
