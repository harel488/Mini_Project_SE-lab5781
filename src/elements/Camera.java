package elements;

import primitives.ColorRay;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
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
    private int Min_MULTI_SAMPLING_SAMPLES;
    static public int Beam_Samples_NUM = 4 ;  // for adaptive super sampling we build a beam represented by 4,one for each rays pixel vertex

    //Auxiliary data for calculating the rays without unnecessary repetitions
    //represent the row of pixel up to the row that is being calculated at the moment
    List<ColorRay> upperPoints = new ArrayList<>();

    public Camera setMin_MULTI_SAMPLING_SAMPLES(int min_MULTI_SAMPLING_SAMPLES) {
        this.Min_MULTI_SAMPLING_SAMPLES = min_MULTI_SAMPLING_SAMPLES;
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
    public ColorRay constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pij = centerPixel(nX,nY,j,i);
        return new ColorRay(new Ray(Pij.subtract(_p0), _p0));
    }

    /**
     * generates multiple ray throw the pixel
     * @param nX number of pixels on a row
     * @param nY number of pixels on a column
     * @param j  position of a pixel on a row
     * @param i  position of a pixel on a column
     * @return multiple rays that goes throw the pixel, distributed evenly all over the pixel area
     */
    public List<ColorRay> constructGridThroughPixel(int nX, int nY, int j, int i) {
        List<ColorRay> multiSamplingRays = new LinkedList<ColorRay>();

        if (Min_MULTI_SAMPLING_SAMPLES == 0) {      // no need to activate multi sampling
            multiSamplingRays.add(constructRayThroughPixel(nX, nY, j, i));
        }
        else if (Min_MULTI_SAMPLING_SAMPLES == Beam_Samples_NUM) {      // no need to activate multi sampling
            return constructAdaptiveSsRays(nX,nY,j,i);
        }
        else {
            Point3D Pij = centerPixel(nX, nY, j, i);
            double pixelWidth = _width / nX;
            double pixelHeight = _height / nY;
            Point3D leftUp = Pij.add(_vRight.scale(-0.5*pixelWidth).add(_vUp.scale(0.5*pixelHeight)));
            int moves =(int) (Math.sqrt(Min_MULTI_SAMPLING_SAMPLES)) ;
            //distance between every sample to the one right to him
            double rightSize = pixelWidth / moves;
            //distance between every sample to the one below him
            double downSize = pixelWidth / moves;
            for (int k = 0; k <= moves; k ++) {
                Point3D point = leftUp;
                if (k != 0) {
                    point = point.add(_vRight.scale(rightSize * k));
                }
                for (double l = 0; l <= moves; l += 1.0) {
                    if (l != 0) {
                        point = point.add(_vUp.scale(downSize * l));
                    }
                    multiSamplingRays.add(new ColorRay(new Ray(point.subtract(_p0), _p0)));
                }
            }
        }
        return multiSamplingRays;
    }

    /**
     * construct camera rays for adaptive super sampling
     * @param nX number of pixels on a row
     * @param nY number of pixels on a column
     * @param j  position of a pixel on a row
     * @param i  position of a pixel on a column
     * @return beam of 4 rays - from the camera to every vertex of the pixel
     */
    public List<ColorRay> constructAdaptiveSsRays(int nX, int nY, int j, int i) {
        List<ColorRay> AdaptiveSsRays = new LinkedList<ColorRay>();
        //4 rays of the beam
        ColorRay left_down;
        ColorRay left_up;
        ColorRay right_down;
        ColorRay right_up;

        Point3D Pij = centerPixel(nX, nY, j, i);
        double moves = (Min_MULTI_SAMPLING_SAMPLES - 1) / 2;
        double xSize = _width / nX / (Min_MULTI_SAMPLING_SAMPLES - 1);
        double ySize = _height / nY / (Min_MULTI_SAMPLING_SAMPLES - 1);

        Point3D leftDown = Pij.add(_vRight.scale(-0.5 * xSize)).add(_vUp.scale(-0.5 * ySize));
        Point3D rightDown = Pij.add(_vRight.scale(0.5 * xSize)).add(_vUp.scale(-0.5 * ySize));
        left_down = new ColorRay(new Ray(leftDown.subtract(_p0), _p0));
        right_down = new ColorRay(new Ray(rightDown.subtract(_p0), _p0));

        //in the first line of pixels we have no preliminary data
        //so we have to calculate the upper rays as well
        if (i == 0) {
            Point3D leftUp = Pij.add(_vRight.scale(-0.5 * xSize)).add(_vUp.scale(0.5 * ySize));
            Point3D rightUp = Pij.add(_vRight.scale(0.5 * xSize)).add(_vUp.scale(0.5 * ySize));
            left_up = new ColorRay(new Ray(leftUp.subtract(_p0), _p0));
            right_up = new ColorRay(new Ray(rightUp.subtract(_p0), _p0));
        }
        else {      //the upper values has been calculated in the upper pixel
            left_up = upperPoints.get(j);
            right_up = upperPoints.get(j + 1);
        }

        if (i == 0) {
            try{
                Thread.currentThread().wait(10);
            }
            catch (Exception ex)
            {

            }

            //System.out.print("");   //wait
            upperPoints.add(left_down);
            if (j == nX-1) {
                upperPoints.add(right_down);
            }
        } else {
            upperPoints.set(j, left_down);
            if (j == nX-1) {
                upperPoints.set(nX, right_down);
            }
        }

        AdaptiveSsRays.add(left_down);
        AdaptiveSsRays.add(left_up);
        AdaptiveSsRays.add(right_down);  //left down represents to the next pixel, but here its right down pixel
        AdaptiveSsRays.add(right_up);
        return AdaptiveSsRays;
    }

        /**
         *
         * @param nX number of columns of the view plane
         * @param nY number of rows of the view plane
         * @param j index of the column of the pixel
         * @param i index of the row of the pixel
         * @return the center of the pixel (point in the 3D model)
         */
    public Point3D centerPixel(int nX, int nY, int j, int i) {
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
        return Pij;
    }
}