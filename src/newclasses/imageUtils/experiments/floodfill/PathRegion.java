package newclasses.imageUtils.experiments.floodfill;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PathRegion {
    private int[][] index;
    private int NO_INDEX = -1;
    private List<Point2D> region;
    private Point2D start;
    private Point2D prev;

    public PathRegion(int width, int height, int x0, int y0) {
        this.index = new int[width][height];
        for (int i=0; i<width; ++i) {
            for (int j=0; j<height; ++j) {
                index[i][j] = NO_INDEX;
            }
        }

        region = new ArrayList<Point2D>();
        start = new Point2D(x0, y0);
        updateRegion(x0, y0);
    }

    public synchronized void updateRegion(int newX, int newY) {
        Point2D curr = new Point2D(newX, newY);
        int regionIndex = region.size();
        index[newX][newY] = regionIndex;
        region.add(curr);

        //if we have at least 3 points
        if (regionIndex > 1) {
            Point2D[] p = new Point2D[3];
            sortX(start, prev, curr, p);

            double x1 = p[0].getX();
            double x2 = p[1].getX();
            double x3 = p[2].getX();
            double y1 = -p[0].getY();
            double y2 = -p[1].getY();
            double y3 = -p[2].getY();

            double m1 = (y3 - y1)/(x3-x1);
            double m2 = (y2 - y1)/(x2-x1);
            double m3 = (y3 - y2)/(x3-x2);
            double k1 = y1 - (m1 * x1);
            double k2 = y2 - (m2 * x2);
            double k3 = y3 - (m3 * x3);

            if (y2 < y3) {
                //case 1 middle point is lower than L1
                for (int x = (int) x1; x < (int) x2; ++x) {
                    double yMin = Math.floor((m2 * x) + k2);
                    double yMax = Math.ceil((m1 * x) + k1);

                    for (int y = (int) yMin; y < (int) yMax; ++y) {
                        Point2D pN = new Point2D(x, -y);
                        int regionIndexN = region.size();
                        index[x][-y] = regionIndexN;
                        region.add(pN);
                    }
                }

                for (int x = (int) x2; x < (int) x3; ++x) {
                    double yMin = Math.floor((m3 * x) + k3);
                    double yMax = Math.ceil((m1 * x) + k1);

                    for (int y = (int) yMin; y < (int) yMax; ++y) {
                        Point2D pN = new Point2D(x, -y);
                        int regionIndexN = region.size();
                        index[x][-y] = regionIndexN;
                        region.add(pN);
                    }
                }
            } else {
                //case 2 middle point is above than L1
                for (int x = (int) x1; x < (int) x2; ++x) {
                    double yMin = Math.floor((m1 * x) + k1);
                    double yMax = Math.ceil((m2 * x) + k2);

                    for (int y = (int) yMin; y < (int) yMax; ++y) {
                        Point2D pN = new Point2D(x, -y);
                        int regionIndexN = region.size();
                        index[x][-y] = regionIndexN;
                        region.add(pN);
                    }
                }

                for (int x = (int) x2; x < (int) x3; ++x) {
                    double yMin = Math.floor((m1 * x) + k1);
                    double yMax = Math.ceil((m3 * x) + k3);

                    for (int y = (int) yMin; y < (int) yMax; ++y) {
                        Point2D pN = new Point2D(x, -y);
                        int regionIndexN = region.size();
                        index[x][-y] = regionIndexN;
                        region.add(pN);
                    }
                }
            }
        }

        //update previous values
        prev = new Point2D(newX, newY);
    }


    public boolean hasPoint(int x, int y) {
        return index[x][y] != -1;
    }

    public Point2D getPoint(int x, int y) {
        return region.get(index[x][y]);
    }

    public List<Point2D> getRegion() {
        return region;
    }

    //must contain 3 elements
    public static void sortX(Point2D p0, Point2D p1, Point2D p2, Point2D[] points) {
        if (p1.getX() > p0.getX()) {
            points[0] = p0;
            points[1] = p1;
        } else {
            points[0] = p1;
            points[1] = p0;
        }

        if (p2.getX() > points[0].getX() && p2.getX() > points[1].getX()) {
            points[2] = p2;
        } else if (p2.getX() < points[0].getX() && p2.getX() < points[1].getX() ){
            points[2] = points[1];
            points[1] = points[0];
            points[0] = p2;
        } else {
            points[2] = points[1];
            points[1] = p2;
        }
    }
}
