package ua.lviv.navpil.sim.points;

import java.util.HashSet;
import java.util.Set;

public class PointUtil {
    
    public static Set<Point> findAdjacent(Point point, Configuration configuration, int xsize, int ysize) {

        int x = point.x();
        int y = point.y();

        HashSet<Point> adjacent = new HashSet<>();

        //Orghogonal points
        if (x > 0) {
            adjacent.add(new Point(x - 1, y));
        }
        if (x < xsize - 1) {
            adjacent.add(new Point(x + 1, y));
        }
        if (y > 0) {
            adjacent.add(new Point(x, y - 1));
        }
        if (y < ysize - 1) {
            adjacent.add(new Point(x, y + 1));
        }

        //Hexagonal
        if (configuration.surroundingPoints() > 4) {
            if (x > 0 && y > 0) {
                adjacent.add(new Point(x - 1, y - 1));
            }
            if (x < xsize - 1 && y > 0) {
                adjacent.add(new Point(x + 1, y - 1));
            }


        }

        //Octogonal
        if (configuration.surroundingPoints() > 6) {
            if (x < xsize - 1 && y < ysize - 1) {
                adjacent.add(new Point(x + 1, y + 1));
            }
            if (x > 0 && y < ysize - 1) {
                adjacent.add(new Point(x - 1, y + 1));
            }
        }

        if (configuration.wrapAround()) {
            if (x == 0) {
                adjacent.add(new Point(xsize - 1, y));
            }
            if (x == xsize - 1) {
                adjacent.add(new Point(0, y));
            }
            if (y == 0) {
                adjacent.add(new Point(x, ysize - 1));
            }
            if (y == ysize - 1) {
                adjacent.add(new Point(x, 0));
            }

            //Hexagonal
            if (configuration.surroundingPoints() > 4) {
                if (x == 0 && y == 0) {
                    adjacent.add(new Point(xsize - 1, ysize - 1));
                }
                if (x == xsize - 1 && y == 0) {
                    adjacent.add(new Point(0, ysize - 1));
                }


            }

            //Octogonal
            if (configuration.surroundingPoints() > 6) {
                if (x == xsize - 1 && y == ysize - 1) {
                    adjacent.add(new Point(0, 0));
                }
                if (x == 0 && y == ysize - 1) {
                    adjacent.add(new Point(xsize - 1, 0));
                }
            }

        }

        return adjacent;
    }
    
}
