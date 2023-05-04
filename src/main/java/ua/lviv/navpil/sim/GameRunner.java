package ua.lviv.navpil.sim;

import javax.swing.*;

public class GameRunner implements Runnable {

    private final JPanel gamePanel;
    private final Game game;
    private final Speeds speeds;

    public static class Speeds {
        private final int betweenSteps;
        private final int insideSteps;

        public Speeds(int betweenSteps, int insideSteps) {
            this.betweenSteps = betweenSteps;
            this.insideSteps = insideSteps;
        }
    }

    public GameRunner(JPanel gamePanel, Game game, Speeds speeds) {
        this.gamePanel = gamePanel;
        this.game = game;
        this.speeds = speeds;
    }

    @Override
    public void run() {
        while (true) {
            game.step(() -> {
                try {
                    // sometimes a game will initiate a redraw during some steps, like during an avalanche
                    // this may be redrawn faster, than the usual delay between steps
                    Thread.sleep(speeds.insideSteps);
                } catch (InterruptedException e) {
                    return;
                }
                gamePanel.repaint();
            });
            gamePanel.repaint();
            try {
                // this is a redraw between the steps
                //noinspection BusyWait
                Thread.sleep(speeds.betweenSteps);
            } catch (InterruptedException e) {
                return;
            }
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
        }
    }
}
