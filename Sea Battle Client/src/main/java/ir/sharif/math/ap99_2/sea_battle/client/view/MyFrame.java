package ir.sharif.math.ap99_2.sea_battle.client.view;

import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.client.view.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private MainPanel mainPanel;

    public MyFrame( ) {
        this.mainPanel = new MainPanel();
        this.config();
        setContentPane(mainPanel);
    }

    private void config() {
        Config frameConfig = Config.getConfig("frame");
        int width = frameConfig.getProperty(Integer.class, "width");
        int height = frameConfig.getProperty(Integer.class, "height");
        setSize(new Dimension(width,height));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(frameConfig.getProperty(Boolean.class, "resizable"));
        setUndecorated(frameConfig.getProperty(Boolean.class, "undecorated"));
        setTitle(frameConfig.getProperty(String.class, "title"));
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setContentPane(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        super.setContentPane(mainPanel);
        super.revalidate();
        super.repaint();
        super.pack();
    }


}
