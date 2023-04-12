package ua.lviv.navpil.jpanelcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class RunJPanelWithOneDimensionalCalculator {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setTitle("Check the list");

        JPanel innerPanel = new JPanel();
        frame.add(innerPanel);

        innerPanel.setLayout(null);
        final List<JPanel> panels = new ArrayList<JPanel>();
        panels.add(getPanel(Color.GREEN));
        panels.add(getPanel(Color.white));
        panels.add(getPanel(Color.red));
        panels.add(getPanel(Color.blue));

        final List<OneDimensionalSize> sizes = new ArrayList<OneDimensionalSize>();
        sizes.add(new OneDimensionalSize(0.2));
        sizes.add(new OneDimensionalSize(0.4));
        sizes.add(new OneDimensionalSize(25));
        sizes.add(new OneDimensionalSize(0.1, 40, 100));

        for (JPanel panel : panels) {
            innerPanel.add(panel);
        }

        innerPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int totalWidth = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                int[] ints = OneDimensionalCalculator.calculate(sizes, totalWidth);
                int index = 0;
                int widthAdder = 0;
                for (JPanel panel : panels) {
                    int width = ints[index++];
                    panel.setBounds(widthAdder, 0, width, height);
                    widthAdder += width;
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);}

    private static JPanel getPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }
}
