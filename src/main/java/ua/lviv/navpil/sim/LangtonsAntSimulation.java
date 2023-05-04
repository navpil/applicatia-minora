package ua.lviv.navpil.sim;

import java.util.Random;

public class LangtonsAntSimulation implements Game {

    private final Type type;

    enum Type {
        /**
         * Original rules
         */
        SIMPLE,
        /**
         * Three alternating colors, which do not result in any emergent behavior
         */
        RLR
    }

    public static void main(String[] args) {
        RunSimulation.startSimulation(new LangtonsAntSimulation(200, 200, Type.SIMPLE),
                "Langton's Ant", 3, new GameRunner.Speeds(1, 1));
    }

    private final Random random = new Random();

    private final int[][] sandbox;
    private final int xsize;
    private final int ysize;

    private int antX;
    private int antY;
    private int color;
    private Direction direction;

    public LangtonsAntSimulation(int xsize, int ysize, Type type) {
        this.xsize = xsize;
        this.ysize = ysize;
        sandbox = createSandbox();
        antX = xsize / 2;//random.nextInt(xsize);
        antY = ysize / 2;//random.nextInt(ysize);
        color = sandbox[antX][antY];
        sandbox[antX][antY] = GamePanel.RED;
        direction = Direction.values()[random.nextInt(4)];
        this.type = type;
    }

    private int[][] createSandbox() {
        final int[][] sandbox;
        sandbox = new int[xsize][];
        for (int i = 0; i < sandbox.length; i++) {
            sandbox[i] = new int[ysize];
        }
        return sandbox;
    }


    @Override
    public void step(Runnable ignore) {
        if (type == Type.SIMPLE) {
            /*
             * At a white square, turn 90° clockwise, flip the color of the square, move forward one unit
             * At a black square, turn 90° counter-clockwise, flip the color of the square, move forward one unit
             */
            if (color == GamePanel.WHITE) {
                direction = direction.clockwise();
                sandbox[antX][antY] = GamePanel.BLACK;
            } else {
                direction = direction.counterClockwise();
                sandbox[antX][antY] = GamePanel.WHITE;
            }

        } else {
            if (color == GamePanel.WHITE) {
                //deosil
                direction = direction.clockwise();
                sandbox[antX][antY] = GamePanel.BLACK;
            } else if (color == GamePanel.BLACK) {
                //widdershins
                direction = direction.counterClockwise();
                sandbox[antX][antY] = GamePanel.GREEN;
            } else {
                //deosil
                direction = direction.clockwise();
                sandbox[antX][antY] = GamePanel.WHITE;
            }
        }

        antX = normalizeX(direction.nextX(antX));
        antY = normalizeY(direction.nextY(antY));
        color = sandbox[antX][antY];
        sandbox[antX][antY] = GamePanel.RED;

    }

    private int normalizeX(int nextX) {
        return normalize(nextX, xsize);
    }

    private int normalizeY(int nextY) {
        return normalize(nextY, ysize);
    }
    private int normalize(int next, int maxSize) {
        if (next < 0) {
            return maxSize - 1;
        } else if  (next == maxSize) {
            return 0;
        }
        return next;
    }

    @Override
    public int[][] getBoxes() {
        return sandbox;
    }

    enum Direction {
        NORTH(0), EAST(1), SOUTH(2), WEST(3);

        private final int i;

        Direction(int i) {
            this.i = i;
        }

        public Direction clockwise() {
            if (this == WEST) {
                return NORTH;
            } else {
                return Direction.values()[i+1];
            }
        }
        public Direction counterClockwise() {
            if (this == NORTH) {
                return WEST;
            } else {
                return Direction.values()[i-1];
            }
        }

        public int nextX(int antX) {
            if (this == EAST) {
                return antX + 1;
            }
            if (this == WEST) {
                return antX - 1;
            }
            return antX;
        }
        public int nextY(int antY) {
            if (this == NORTH) {
                return antY + 1;
            }
            if (this == SOUTH) {
                return antY - 1;
            }
            return antY;
        }
    }

}
