package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.view.ReadyButton;
import ir.sharif.math.ap99_2.sea_battle.client.view.ResetMapButton;
import ir.sharif.math.ap99_2.sea_battle.client.view.TimerLabel;
import ir.sharif.math.ap99_2.sea_battle.shared.events.ClickOnReadyEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.events.ClickOnResetEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private BoardPanel playerBoardPanel;
    private int resetOccurrence = 0;
    private BoardPanel opponentBoardPanel;
    private ResetMapButton resetMapButton;
    private ReadyButton readyButton;
    private TimerLabel timerLabel;
    private JLabel titleLabel;
    private final EventListener eventListener;

    public GamePanel(EventListener eventListener) {
        this.eventListener = eventListener;
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        config();
        addElements();

    }

    public void config() {
        resetMapButton = new ResetMapButton();
        resetMapButton.setBounds(285, 142, resetMapButton.getbWidth(), resetMapButton.getbHeight());
        resetMapButton.addActionListener(this);
        readyButton = new ReadyButton();
        readyButton.setBounds(425, 142, readyButton.getbWidth(), readyButton.getbHeight());
        readyButton.addActionListener(this);
        timerLabel = new TimerLabel();
        timerLabel.setBackground(Color.BLACK);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setVerticalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(this.getFont().deriveFont(17.0f));
        timerLabel.setBounds(365, 515, timerLabel.getlWidth(), timerLabel.getlHeight());
        Config labelsConfig = Config.getConfig("labels");
        Config l100x20Properties = labelsConfig.getProperty(Config.class, "l300x20");
        int lWidth = l100x20Properties.getProperty(Integer.class, "width");
        int lHeight = l100x20Properties.getProperty(Integer.class, "height");
        titleLabel = new JLabel();
        titleLabel.setBounds(490, 0, lWidth, lHeight);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(17.0f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

    }

    private void addElements() {
        this.add(resetMapButton);
        this.add(readyButton);
        this.add(timerLabel);
        this.add(titleLabel);
    }

    public void setPlayerBoardPanel(Board board) {
        if (playerBoardPanel != null) {
            playerBoardPanel.updateBoard(board);
        } else {
            playerBoardPanel = new BoardPanel(board, false, eventListener, false);
            playerBoardPanel.setBounds(265, 190, playerBoardPanel
                    .getBoardWidth(), playerBoardPanel.getBoardHeight());
            this.add(playerBoardPanel);

        }
        this.revalidate();
        this.repaint();

    }

    public void setOpponentBoardPanel(Board board) {
        if (opponentBoardPanel != null) {
            opponentBoardPanel.updateBoard(board);
        } else {
            opponentBoardPanel = new BoardPanel(board, true, eventListener, false);
            opponentBoardPanel.setBounds(715, 190, opponentBoardPanel
                    .getBoardWidth(), opponentBoardPanel.getBoardHeight());
            this.add(opponentBoardPanel);
        }
        this.revalidate();
        this.repaint();
    }

    public BoardPanel getPlayerBoardPanel() {
        return playerBoardPanel;
    }

    public void setTimerLabelText(String timerLabelText) {
        this.timerLabel.setText(timerLabelText);
    }

    public void setTitleLabelText(String titleLabelText) {
        this.titleLabel.setText(titleLabelText);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerBoardPanel == null)
            return;
        if (e.getSource() == readyButton) {
            eventListener.listen(new ClickOnReadyEvent());
            readyButton.setEnabled(false);
            readyButton.removeActionListener(this);

        }
        if (e.getSource() == resetMapButton) {
            if (playerBoardPanel == null)
                return;
            resetOccurrence++;
            eventListener.listen(new ClickOnResetEvent());
            if (resetOccurrence == 3) {
                resetMapButton.setEnabled(false);
                resetMapButton.removeActionListener(this);
            }
        }


    }

}
