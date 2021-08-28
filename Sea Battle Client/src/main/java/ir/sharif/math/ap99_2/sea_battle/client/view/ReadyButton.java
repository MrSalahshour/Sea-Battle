package ir.sharif.math.ap99_2.sea_battle.client.view;

import ir.sharif.math.ap99_2.sea_battle.client.util.Config;

import javax.swing.*;
import java.awt.*;

public class ReadyButton extends JButton {
    private final int bWidth;
    private final int bHeight;
    public ReadyButton() {
        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class,"b120x25");
        this.bWidth = b200x50Properties.getProperty(Integer.class,"width");
        this.bHeight = b200x50Properties.getProperty(Integer.class,"height");
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setFont(this.getFont().deriveFont(17.0f));
        this.setText("Ready");
    }

    public int getbWidth() {
        return bWidth;
    }

    public int getbHeight() {
        return bHeight;
    }
}
