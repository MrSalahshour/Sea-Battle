package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;

import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WatchGamePanel extends JPanel implements ActionListener {
    private BoardPanel player1BoardPanel;
    private BoardPanel player2BoardPanel;
    private final JButton backToMenuButton = new JButton("BACK");
    private final EventListener eventListener;
    private final GraphicalAgent graphicalAgent;
    private final JLabel player1BoardName = new JLabel();
    private final JLabel player2BoardName = new JLabel();

    public WatchGamePanel(EventListener eventListener, GraphicalAgent graphicalAgent) {
        this.eventListener = eventListener;
        this.graphicalAgent = graphicalAgent;
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        config();
    }

    public void config() {

        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b200x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");
        Config labelsConfig = Config.getConfig("labels");
        Config l200x50Properties = labelsConfig.getProperty(Config.class, "l100x20");
        int lWidth = l200x50Properties.getProperty(Integer.class, "width");
        int lHeight = l200x50Properties.getProperty(Integer.class, "height");
        player1BoardName.setBounds(365, 140, lWidth, lHeight);
        player2BoardName.setBounds(815, 140, lWidth, lHeight);
        player1BoardName.setHorizontalAlignment(SwingConstants.CENTER);
        player2BoardName.setHorizontalAlignment(SwingConstants.CENTER);
        player1BoardName.setForeground(Color.WHITE);
        player2BoardName.setForeground(Color.WHITE);
        backToMenuButton.setBounds(10, 10, bWidth, bHeight);
        backToMenuButton.addActionListener(this);
        this.add(backToMenuButton);
        this.add(player1BoardName);
        this.add(player2BoardName);

    }

    public void setPlayer1BoardPanel(Board board) {
        if (player1BoardPanel != null) {
            player1BoardPanel.updateBoard(board);
        } else {
            player1BoardPanel = new BoardPanel(board, false, eventListener, true);
            player1BoardPanel.setBounds(265, 190, player1BoardPanel
                    .getBoardWidth(), player1BoardPanel.getBoardHeight());
            this.add(player1BoardPanel);
        }
        this.revalidate();
        this.repaint();

    }

    public void setPlayer2BoardPanel(Board board) {
        if (player2BoardPanel != null) {
            player2BoardPanel.updateBoard(board);
        } else {
            player2BoardPanel = new BoardPanel(board, false, eventListener, true);
            player2BoardPanel.setBounds(715, 190, player2BoardPanel
                    .getBoardWidth(), player2BoardPanel.getBoardHeight());
            this.add(player2BoardPanel);
        }
        this.revalidate();
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }

    public void setPlayer1BoardName(String player1BoardName) {
        this.player1BoardName.setText(player1BoardName);
    }

    public void setPlayer2BoardName(String player2BoardName) {
        this.player2BoardName.setText(player2BoardName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMenuButton) {
            graphicalAgent.goToMainMenu();
        }

    }
}
