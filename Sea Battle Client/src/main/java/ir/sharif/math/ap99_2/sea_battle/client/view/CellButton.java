package ir.sharif.math.ap99_2.sea_battle.client.view;


import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    private final int xCoordinate;
    private final int yCoordinate;

    public CellButton(int xCoordinate, int yCoordinate) {
        super();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.setBackground(Color.WHITE);

    }

    public void shipHandle() {
        this.setBackground(Color.BLUE);

    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

}
