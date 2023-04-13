package ua.lviv.navpil.sim;

import javax.swing.*;

public class GameRunner implements Runnable {

    private final JPanel gamePanel;
    private final Game game;

    public GameRunner(JPanel gamePanel, Game game) {
        this.gamePanel = gamePanel;
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            game.step(() -> {
                try {
                    // sometimes a game will initiate a redraw during some steps, like during an avalanche
                    // this may be redrawn faster, than the usual delay between steps
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
                gamePanel.repaint();
            });
            gamePanel.repaint();
            try {
                // this is a redraw between the steps
                //noinspection BusyWait
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
        }
    }
}
