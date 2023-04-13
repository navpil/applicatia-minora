package ua.lviv.navpil.sim;

import javax.swing.*;
import java.util.concurrent.Executors;

public class RunSimulation {

    public static void startSimulation(Game game, String simulationName) {
        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setTitle(simulationName);

        JPanel innerPanel = new GamePanel(game);
        frame.add(innerPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Executors.newSingleThreadExecutor().submit(new GameRunner(innerPanel, game));
    }


}
