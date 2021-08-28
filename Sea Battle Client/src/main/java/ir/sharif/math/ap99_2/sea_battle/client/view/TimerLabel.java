package ir.sharif.math.ap99_2.sea_battle.client.view;

import ir.sharif.math.ap99_2.sea_battle.client.util.Config;

import javax.swing.*;

public class TimerLabel extends JLabel {
    private final int lWidth;
    private final int lHeight;

    public TimerLabel() {
        Config labelsConfig = Config.getConfig("labels");
        Config l100x20Properties = labelsConfig.getProperty(Config.class,"l100x20");
        this.lWidth = l100x20Properties.getProperty(Integer.class,"width");
        this.lHeight = l100x20Properties.getProperty(Integer.class,"height");


    }

    public int getlWidth() {
        return lWidth;
    }

    public int getlHeight() {
        return lHeight;
    }
}
