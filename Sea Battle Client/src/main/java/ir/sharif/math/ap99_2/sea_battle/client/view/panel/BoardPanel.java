package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.client.view.CellButton;
import ir.sharif.math.ap99_2.sea_battle.shared.events.ClickOnCellEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class BoardPanel extends JPanel implements ActionListener {
    private final LinkedList<CellButton> cellButtons = new LinkedList<>();
    private final Board board;
    private final boolean isOpponent;
    private final EventListener eventListener;
    private final int boardWidth;
    private final int boardHeight;
    private final boolean watched;

    public BoardPanel(Board board, boolean isOpponent, EventListener eventListener, boolean watched) {
        this.watched = watched;
        this.board = board;
        this.isOpponent = isOpponent;
        this.eventListener = eventListener;
        Config mainPanel = Config.getConfig("boardPanel");
        this.boardWidth = mainPanel.getProperty(Integer.class, "width");
        this.boardHeight = mainPanel.getProperty(Integer.class, "height");
        GridLayout gridLayout = new GridLayout(10, 10);
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);
        this.setBackground(Color.BLACK);
        this.setLayout(gridLayout);
        addElements();
    }

    private void addElements() {
        Config mainPanel = Config.getConfig("cell");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                CellButton cellButton = new CellButton(j, i);
                Cell cell = board.getCell(j, i);
                if (cell.isHasShip() && cell.isSelected()) {
                    Image cross = ImageLoader.getCrossImage().
                            getScaledInstance(width, height, Image.SCALE_DEFAULT);
                    cellButton.setIcon(new ImageIcon(cross));
                } else if (!cell.isHasShip() && cell.isSelected()) {
                    cellButton.setBackground(Color.DARK_GRAY);
                } else if (cell.isHasShip() && !isOpponent && !watched) {
                    cellButton.setBackground(Color.BLUE);
                    this.revalidate();
                    this.repaint();
                }
                cellButton.addActionListener(this);
                cellButtons.add(cellButton);
                this.add(cellButton);
            }
        }
    }

    public void updateBoard(Board board) {
        Config mainPanel = Config.getConfig("cell");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                int index = (i - 1) * 10 + j - 1;
                CellButton cellButton = cellButtons.get(index);
                cellButton.setBackground(Color.WHITE);
                Cell cell = board.getCell(j, i);
                if (cell.isHasShip() && cell.isSelected()) {
                    Image cross = ImageLoader.getCrossImage().
                            getScaledInstance(width, height, Image.SCALE_DEFAULT);
                    cellButton.setIcon(new ImageIcon(cross));
                } else if (!cell.isHasShip() && cell.isSelected()) {
                    cellButton.setBackground(Color.DARK_GRAY);
                } else if (cell.isHasShip() && !isOpponent && !watched) {
                    cellButton.setBackground(Color.BLUE);
                    this.revalidate();
                    this.repaint();
                }
            }
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isOpponent) {
            for (CellButton cellButton : cellButtons) {
                if (e.getSource() == cellButton) {
                    int x = cellButton.getxCoordinate();
                    int y = cellButton.getyCoordinate();
                    Cell cell = board.getCell(x, y);
                    if (cell.isSelected())
                        break;
                    else {

                        eventListener.listen(new ClickOnCellEvent(x, y));
                    }
                }

            }
        }
    }
}
