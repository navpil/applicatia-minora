package ua.csia.dmp.dice;

public class NumberStat implements Comparable<NumberStat> {
    private final int number;
    private final double count;

    public NumberStat(int number, double count) {
        this.number = number;
        this.count = count;
    }

    public int getNumber() {
        return number;
    }

    public double getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "NumberStat{" +
                "number=" + number +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(NumberStat o) {
        return number - o.getNumber();
    }
}
