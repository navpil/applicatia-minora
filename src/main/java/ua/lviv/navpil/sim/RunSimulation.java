package ua.lviv.navpil.sim;

import javax.swing.*;
import java.util.concurrent.Executors;

public class RunSimulation {

    public static void startSimulation(Game game, String simulationName) {
        startSimulation(game, simulationName, 10, new GameRunner.Speeds(100, 10));
    }

    public static void startSimulation(Game game, String simulationName,
                                       int cellSize, GameRunner.Speeds speeds) {
        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setTitle(simulationName);

        JPanel innerPanel = new GamePanel(game, cellSize);
        frame.add(innerPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Executors.newSingleThreadExecutor().submit(new GameRunner(innerPanel, game, speeds));
    }


}
