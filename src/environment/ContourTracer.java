/*
 *     Copyright 2010, 2015, 2017 Julian de Hoog (julian@dehoog.ca),
 *     Victor Spirin (victor.spirin@cs.ox.ac.uk),
 *     Christian Clausen (christian.clausen@uni-bremen.de
 *
 *     This file is part of MRESim 2.3, a simulator for testing the behaviour
 *     of multiple robots exploring unknown environments.
 *
 *     If you use MRESim, I would appreciate an acknowledgement and/or a citation
 *     of our papers:
 *
 *     @inproceedings{deHoog2009,
 *         title = "Role-Based Autonomous Multi-Robot Exploration",
 *         author = "Julian de Hoog, Stephen Cameron and Arnoud Visser",
 *         year = "2009",
 *         booktitle =
 *     "International Conference on Advanced Cognitive Technologies and Applications (COGNITIVE)",
 *         location = "Athens, Greece",
 *         month = "November",
 *     }
 *
 *     @incollection{spirin2015mresim,
 *       title={MRESim, a Multi-robot Exploration Simulator for the Rescue Simulation League},
 *       author={Spirin, Victor and de Hoog, Julian and Visser, Arnoud and Cameron, Stephen},
 *       booktitle={RoboCup 2014: Robot World Cup XVIII},
 *       pages={106--117},
 *       year={2015},
 *       publisher={Springer}
 *     }
 *
 *     MRESim is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     MRESim is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with MRESim.
 *     If not, see <http://www.gnu.org/licenses/>.
 */
package environment;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author julh
 */
public class ContourTracer {

    public static void mergeContours(LinkedList<LinkedList<Point>> contours) {
        for (int i = 0; i < contours.size(); i++) {
            LinkedList<Point> contour = contours.get(i);

            Point p1 = contour.getFirst();
            Point p2 = contour.getLast();
            LinkedList<Point> conn1 = hasNeighbor(contours, contour, p1);
            LinkedList<Point> conn2 = hasNeighbor(contours, contour, p1);
            if (conn1 != null) {
                contour.addAll(conn1);
                contours.remove(conn1);
            }
            if (conn2 != null) {
                contour.addAll(conn2);
                contours.remove(conn2);
            }

        }
    }

    private static LinkedList<Point> hasNeighbor(LinkedList<LinkedList<Point>> contours, LinkedList<Point> containing, Point p) {
        for (int k = 0; k < contours.size(); k++) {
            LinkedList<Point> contour = contours.get(k);
            if (containing == contour) {
                break;
            }

            Point pf = isNeighbor(p, contour.getFirst());
            Point pl = isNeighbor(p, contour.getLast());
            if (pf != null) {
                contour.add(pf);
                return contour;
            } else if (pl != null) {
                contour.add(pl);
                return contour;
            } else {
                return null;
            }
        }

        return null;
    }

    private static Point isNeighbor(Point p, Point q) {
        if (p.x - q.x == 2) {
            return new Point(p.x - 1, p.y);
        }
        if (p.x - q.x == -2) {
            return new Point(p.x + 1, p.y);
        }
        if (p.y - q.y == 2) {
            return new Point(p.x, p.y - 1);
        }
        if (p.y - q.y == -2) {
            return new Point(p.x, p.y + 1);
        }
        return null;
    }

    // Order is important!
    private static enum direction {
        NE, E, SE, S, SW, W, NW, N
    }

    private static Point dir2Point(Point pt, direction dir) {
        switch (dir) {
            case NE:
                return new Point(pt.x + 1, pt.y - 1);
            case E:
                return new Point(pt.x + 1, pt.y);
            case SE:
                return new Point(pt.x + 1, pt.y + 1);
            case S:
                return new Point(pt.x, pt.y + 1);
            case SW:
                return new Point(pt.x - 1, pt.y + 1);
            case W:
                return new Point(pt.x - 1, pt.y);
            case NW:
                return new Point(pt.x - 1, pt.y - 1);
            case N:
                return new Point(pt.x, pt.y - 1);
        }

        // this point should never be reached
        return null;
    }

    private static direction points2dir(Point from, Point to) {
        if (from.x - to.x == 1) {
            if (from.y - to.y == 1) {
                return direction.SE;
            } else if (from.y - to.y == 0) {
                return direction.E;
            } else if (from.y - to.y == -1) {
                return direction.NE;
            }
        } else if (from.x - to.x == 0) {
            if (from.y - to.y == 1) {
                return direction.S;
            } else if (from.y - to.y == -1) {
                return direction.N;
            }
        } else if (from.x - to.x == -1) {
            if (from.y - to.y == 1) {
                return direction.SW;
            } else if (from.y - to.y == 0) {
                return direction.W;
            } else if (from.y - to.y == -1) {
                return direction.NW;
            }
        }

        // this point should never be reached
        return null;
    }

    private static direction searchDir(Point from, Point to) {
        direction dir = points2dir(from, to);
        int searchIndex = (dir.ordinal() + 6) % 8;
        return direction.values()[searchIndex];
    }

    private static Point findNextPixelOnContour(OccupancyGrid occGrid, Point pt, direction dir) {
        direction currDir;
        Point currPoint;

        for (int i = 0; i < 8; i++) {
            currDir = direction.values()[(dir.ordinal() + i) % 8];
            currPoint = dir2Point(pt, currDir);

            if (occGrid.locationExists(currPoint.x, currPoint.y)
                    && occGrid.frontierCellAt(currPoint.x, currPoint.y)) {
                int dx = currPoint.x - pt.x;
                int dy = currPoint.y - pt.y;
                boolean diagonal = (dx != 0) && (dy != 0);
                //  --only add diagonal cells if there is space on both sides. Otherwise path has to go 'manhattan' way
                if (diagonal && !(occGrid.freeSpaceAt(pt.x + dx, pt.y) && occGrid.freeSpaceAt(pt.x, pt.y + dy))) {
                    //continue;
                } else {
                    return currPoint;
                }
            }

        }

        // couldn't find any further points
        return null;
    }

    private static LinkedList<Point> traceContour(OccupancyGrid occGrid, int startX, int startY, direction startDir) {
        if (!occGrid.frontierCellAt(startX, startY)) {
            System.err.println("This cannot happen!");
        }

        Point currPixel, nextPixel;
        direction searchStart;
        LinkedList<Point> pts = new LinkedList<Point>();

        Point firstPixel = new Point(startX, startY);
        pts.add(firstPixel);
        Point secondPixel = findNextPixelOnContour(occGrid, firstPixel, startDir);

        //if there is no further pixel, this is a one-pixel component and we're done
        if (secondPixel == null) {
            return pts;
        }

        //System.out.println("Firstpixel: " + firstPixel.x + " " + firstPixel.y);
        //System.out.println("Secondpixel: " + secondPixel.x + " " + secondPixel.y);
        searchStart = searchDir(secondPixel, firstPixel);
        //System.out.println("SS: " + searchStart.toString());

        currPixel = new Point(secondPixel.x, secondPixel.y);
        //System.out.println("CP: " + currPixel.x + " " + currPixel.y);

        nextPixel = findNextPixelOnContour(occGrid, currPixel, searchStart);
        //System.out.println("NP: " + nextPixel.x + " " + nextPixel.y);

        // In loop until all pixels on contour have been found
        while (!(currPixel.equals(firstPixel) && nextPixel.equals(secondPixel))) {
            if (pts.contains(currPixel)) {
                break;
            }
            pts.add(currPixel);
            searchStart = searchDir(nextPixel, currPixel);
            //System.out.println("SS: " + searchStart.toString());
            currPixel = nextPixel;
            //System.out.println("CP: " + currPixel.x + " " + currPixel.y);
            nextPixel = findNextPixelOnContour(occGrid, currPixel, searchStart);
            //System.out.println("NP: " + nextPixel.x + " " + nextPixel.y);
        }
        return pts;
    }

    private static int[][] updateLabels(int[][] labels, LinkedList<Point> contour) {
        contour.stream().forEach((p) -> {
            labels[p.x][p.y] = 1;
        });

        return labels;
    }

    public static void saveLabelsToPNG(String filename, int[][] labels) {
        try {
            // retrieve image
            BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            //Graphics g = bi.getGraphics();
            for (int i = 0; i < 800; i++) {
                for (int j = 0; j < 600; j++) {
                    if (labels[i][j] == 1) {
                        bi.setRGB(i, j, Color.WHITE.getRGB());
                    } else if (labels[i][j] == 0) {
                        bi.setRGB(i, j, Color.BLACK.getRGB());
                    } else {
                        bi.setRGB(i, j, Color.RED.getRGB());
                    }
                }
            }
            File outputfile = new File(filename);
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {

        }
    }

    public static LinkedList<LinkedList<Point>> findAllContours(OccupancyGrid occGrid) {
        LinkedList<LinkedList<Point>> contourList = new LinkedList<>();
        LinkedList<Point> currContour;

        int[][] labels = new int[occGrid.width][occGrid.height];

        for (int[] label : labels) {
            for (int j = 0; j < labels[0].length; j++) {
                label[j] = 0;
            }
        }

        for (int j = 0; j < occGrid.height; j++) {
            for (int i = 0; i < occGrid.width; i++) {
                if (occGrid.frontierCellAt(i, j) && labels[i][j] == 0) {
                    //&& (!occGrid.locationExists(i, j - 1) || (!occGrid.frontierCellAt(i - 1, j - 1) && !occGrid.frontierCellAt(i, j - 1) && !occGrid.frontierCellAt(i + 1, j - 1)))

                    //saveLabelsToPNG("contours", labels);
                    // We must have found external contour of new component
                    currContour = traceContour(occGrid, i, j, direction.NE);

                    if (currContour.size() <= 1) {
                        continue;
                    }
                    boolean interesting = false;
                    for (Point p : currContour) {
                        if (labels[p.x][p.y] == 1) {
                            interesting = false;
                            break;
                        }
                        if (occGrid.frontierBorderCellAt(p.x, p.y)) {
                            interesting = true;
                            // this contour should be added
                            //labels = updateLabels(labels, currContour);
                            //contourList.add(currContour);
                            //break;
                        }
                    }
                    if (interesting) {
                        labels = updateLabels(labels, currContour);
                        contourList.add(currContour);
                    }
                }

            }
        }
        //saveLabelsToPNG("contours", labels);
        return contourList;
    }
}
