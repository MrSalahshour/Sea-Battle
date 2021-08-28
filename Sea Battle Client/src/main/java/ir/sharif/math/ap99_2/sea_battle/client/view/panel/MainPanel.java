package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.shared.util.Loop;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private int fps;


    public MainPanel() {
        initialize();
        new Loop(fps, this::update).start();

    }

    private void initialize() {
        config();
    }

    private void config() {
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        setLayout(null);
        setBounds(x, y, width, height);
        setPreferredSize(new Dimension(width, height));
        fps = mainPanel.getProperty(Integer.class, "fps");
    }


    private void update() {
        super.revalidate();
        super.repaint();
    }
}
