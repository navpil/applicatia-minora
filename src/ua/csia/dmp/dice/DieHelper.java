package ua.csia.dmp.dice;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 06.03.15
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class DieHelper {

    public static void main2(String [] args) {
        List<NumberStat> stats = new DieHelper().stats(new Die(1, 2, 3, 4, 5, 6), new Die(1, 2, 3, 4, 5, 6));
        System.out.println(stats);
        List<NumberStat> perfectStats = new ArrayList<NumberStat>();
        perfectStats.add(new NumberStat(1, 4.0 / 16));
        perfectStats.add(new NumberStat(2, 6.0 / 16));
        perfectStats.add(new NumberStat(3, 4.0 / 16));
        perfectStats.add(new NumberStat(4, 1.0 / 16));
        perfectStats.add(new NumberStat(5, 1.0 / 16));
        System.out.println(perfectStats);
        double sum = 0;
        double []ideal = new double[] {0.0625, 0.25, 0.375};
        double []minuses = new double[] {0.0625, 0.25 + 0.0625, 0.375 + 0.25 + 0.0625};
        int counter = 0;
        System.out.println(minuses[0] + ", " + minuses[1] + ", " + minuses[2]);
        for (NumberStat stat : stats) {
            sum += stat.getCount();
            System.out.print("Number[" + stat.getNumber() +"]: " + sum);
            System.out.print(", " + (sum - minuses[0]));
            System.out.print(", " + (sum - minuses[1]));
            System.out.print(", " + (sum - minuses[2]));
            System.out.println();
//            s += stat.getCount();
//            if(s >= ideal[counter]) {
//                System.out.println("Threshold: " + stat.getNumber() + " with " + s);
//                counter++;
//                s = 0;
//                if(counter >= ideal.length) {
//                    break;
//                }
//            }
        }

    }


    public static void main_(String [] args) {

        List<Die> dice1 = new ArrayList<Die>();
        for(int zeroes = 1; zeroes < 6; zeroes++) {
            for(int ones = 0; ones <= (6 - zeroes); ones++) {
                int twos = 6 - zeroes - ones;
                dice1.add(generateDie(new Face(0, zeroes), new Face(1, ones), new Face(2, twos)));
            }
        }

        List<Die> dice2 = new ArrayList<Die>();
        for(int ones = 1; ones < 6; ones++) {
            for(int twos = 0; twos <= (6 - ones); twos++) {
                int threes = 6 - twos - ones;
                dice2.add(generateDie(new Face(1, ones), new Face(2, twos), new Face(3, threes)));
            }
        }
        DieHelper dieHelper = new DieHelper();

        List<NumberStat> perfectStats = new ArrayList<NumberStat>();
        perfectStats.add(new NumberStat(1, 3.0 / 8));
        perfectStats.add(new NumberStat(2, 3.0 / 8));
        perfectStats.add(new NumberStat(3, 1.0 / 8));
        perfectStats.add(new NumberStat(4, 1.0 / 8));

        for (Die die1 : dice1) {
            for (Die die2 : dice2) {
                List<NumberStat> stats = dieHelper.stats(die1, die2);
                Collections.sort(stats);
                if(generalCondition(stats) && goodEnough(stats, perfectStats, 0.5)) {
                    System.out.println("Dice: " + die1 + ", " + die2);
                    System.out.println(stats);
                    System.out.println(perfectStats);
                }
            }
        }

        List<NumberStat> almostPerfectStats = new ArrayList<NumberStat>();
        almostPerfectStats.add(new NumberStat(1, 2.0 / 6));
        almostPerfectStats.add(new NumberStat(2, 2.0 / 6));
        almostPerfectStats.add(new NumberStat(3, 1.0 / 6));
        almostPerfectStats.add(new NumberStat(4, 1.0 / 6));
        System.out.println("Almost perfect stats: " + almostPerfectStats);

    }
    public static void main(String [] args) {

        List<Die> dice1 = new ArrayList<Die>();
        for(int zeroes = 1; zeroes < 6; zeroes++) {
            for(int ones = 0; ones <= (6 - zeroes); ones++) {
                for(int twos = 0; twos <= (6 - ones - zeroes); twos++) {
                    int threes = 6 - zeroes - ones - twos;
                    dice1.add(generateDie(new Face(0, zeroes), new Face(1, ones), new Face(2, twos), new Face(3, threes)));
                }
            }
        }

        List<Die> dice2 = new ArrayList<Die>();
        for(int ones = 1; ones < 6; ones++) {
            for(int twos = 0; twos <= (6 - ones); twos++) {
                for(int threes = 0; threes <= (6 - ones - twos); threes++) {
                    int fours = 6 - twos - ones - threes;
                    dice2.add(generateDie(new Face(1, ones), new Face(2, twos), new Face(3, threes), new Face(4, fours)));
                }
            }
        }
        DieHelper dieHelper = new DieHelper();

        List<NumberStat> perfectStats = new ArrayList<NumberStat>();
        perfectStats.add(new NumberStat(1, 4.0 / 16));
        perfectStats.add(new NumberStat(2, 6.0 / 16));
        perfectStats.add(new NumberStat(3, 4.0 / 16));
        perfectStats.add(new NumberStat(4, 1.0 / 16));
        perfectStats.add(new NumberStat(5, 1.0 / 16));

        for (Die die1 : dice1) {
            for (Die die2 : dice2) {
                List<NumberStat> stats = dieHelper.stats(die1, die2);
                Collections.sort(stats);
                if(
                        //generalCondition2(stats)
                        goodEnough(stats, perfectStats, 0.34)
                        ) {
                    System.out.println("Dice: " + die1 + ", " + die2);
                    System.out.println(stats);
                    System.out.println(perfectStats);
                }
            }
        }

    }

    private static boolean goodEnough(List<NumberStat> stats, List<NumberStat> perfectStats, double threshold) {
        if(stats.size() != perfectStats.size()) {
            return false;
        }
        int number = 1;
        for (NumberStat stat : stats) {
            if(stat.getNumber() != number++) {
                return false;
            }
        }
        Iterator<NumberStat> statIterator = stats.iterator();
        Iterator<NumberStat> iterator = perfectStats.iterator();
        while(iterator.hasNext()) {
            NumberStat stat = statIterator.next();
            NumberStat perfect = iterator.next();
            if((Math.abs(stat.getCount() - perfect.getCount()) / perfect.getCount()) > threshold) {
                return false;
            }
        }
        return true;
    }

    private static boolean generalCondition(List<NumberStat> stats) {
        return (closeEnough(stats.get(0).getCount(), stats.get(1).getCount()) &&
                closeEnough(stats.get(2).getCount(), stats.get(3).getCount()));
    }

    private static boolean generalCondition2(List<NumberStat> stats) {
        return (stats.size() == 5 && closeEnough(stats.get(0).getCount(), stats.get(2).getCount()) &&
                closeEnough(stats.get(2).getCount(), stats.get(4).getCount()));
    }

    private static boolean closeEnough(double count1, double count2) {
        return Math.abs(count1 - count2) < 0.01;
    }

    private static Die generateSecondDie(int ones, int twos, int threes) {
        int[] faces = new int[6];
        int counter = 0;
        for(int i = 0; i < ones; i++) {
            faces[counter++] = 1;
        }
        for(int i = 0; i < twos; i++) {
            faces[counter++] = 2;
        }
        for(int i = 0; i < threes; i++) {
            faces[counter++] = 3;
        }
        return new Die(faces);    }


    private static Die generateDie(Face ... facs) {
        int[] faces = new int[6];
        int counter = 0;
        for (Face face : facs) {
            for(int i = 0; i < face.getCount(); i++) {
                faces[counter++] = face.getValue();
            }
        }
        return new Die(faces);
    }

    private List<NumberStat> stats(Die d1, Die d2) {
        IntMap intMap = new IntMap();
        for (int face1 : d1.getFaces()) {
            for (int face2 : d2.getFaces()) {
                intMap.add(face1 + face2);
            }
        }
        return intMap.generateStats();  //To change body of created methods use File | Settings | File Templates.
    }

    private static class IntMap {
        private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        public void add(int key) {
            if (!map.containsKey(key)) {
                 map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }

        public List<NumberStat> generateStats() {
            ArrayList<NumberStat> stats = new ArrayList<NumberStat>();
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                stats.add(new NumberStat(entry.getKey(), (double)entry.getValue() / 36));
            }
            return stats;
        }
    }
}
