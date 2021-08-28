package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.shared.events.WatchGameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LiveGameDetailPanel extends JPanel implements ActionListener {
    private JLabel player1Name, player2Name, vS, player1Moves, player2Moves, player1HitShips, player2HitShips, player1SuccessfulBombs, player2SuccessfulBombs;
    private final JButton watchGameButton = new JButton("Watch Game");
    private final int panelWidth, panelHeight;
    private final EventListener eventListener;


    public LiveGameDetailPanel(EventListener eventListener) {
        this.eventListener = eventListener;
        this.setBackground(Color.WHITE);
        Config mainPanel = Config.getConfig("gameDetailPanel");
        this.panelWidth = mainPanel.getProperty(Integer.class, "width");
        this.panelHeight = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        configElements();
        addElements();
    }

    private void configElements() {
        Config labelsConfig = Config.getConfig("labels");
        Config l100x20Properties = labelsConfig.getProperty(Config.class, "l100x20");
        int vSWidth = l100x20Properties.getProperty(Integer.class, "width");
        int vSHeight = l100x20Properties.getProperty(Integer.class, "height");
        vS = new JLabel("VS");
        vS.setHorizontalAlignment(SwingConstants.CENTER);
        vS.setForeground(Color.BLACK);
        vS.setBounds(250, 24, vSWidth, vSHeight);
        Config l200x20Properties = labelsConfig.getProperty(Config.class, "l200x20");
        int labelWidth = l200x20Properties.getProperty(Integer.class, "width");
        int labelHeight = l200x20Properties.getProperty(Integer.class, "height");
        player1Name = new JLabel();
        player1Name.setHorizontalAlignment(SwingConstants.CENTER);
        player1Name.setForeground(Color.BLACK);
        player1Name.setBounds(25, 24, labelWidth, labelHeight);
        player1Name.setFont(player1Name.getFont().deriveFont(13.0f));
        player2Name = new JLabel();
        player2Name.setHorizontalAlignment(SwingConstants.CENTER);
        player2Name.setForeground(Color.BLACK);
        player2Name.setBounds(375, 24, labelWidth, labelHeight);
        player2Name.setFont(player2Name.getFont().deriveFont(13.0f));
        player1Moves = new JLabel();
        player1Moves.setHorizontalAlignment(SwingConstants.CENTER);
        player1Moves.setForeground(Color.BLACK);
        player1Moves.setBounds(25, 68, labelWidth, labelHeight);
        player1Moves.setFont(player1Moves.getFont().deriveFont(13.0f));
        player2Moves = new JLabel();
        player2Moves.setHorizontalAlignment(SwingConstants.CENTER);
        player2Moves.setForeground(Color.BLACK);
        player2Moves.setBounds(375, 68, labelWidth, labelHeight);
        player2Moves.setFont(player2Moves.getFont().deriveFont(13.0f));
        player1HitShips = new JLabel();
        player1HitShips.setHorizontalAlignment(SwingConstants.CENTER);
        player1HitShips.setForeground(Color.BLACK);
        player1HitShips.setBounds(25, 112, labelWidth, labelHeight);
        player1HitShips.setFont(player1HitShips.getFont().deriveFont(13.0f));
        player2HitShips = new JLabel();
        player2HitShips.setHorizontalAlignment(SwingConstants.CENTER);
        player2HitShips.setForeground(Color.BLACK);
        player2HitShips.setBounds(375, 112, labelWidth, labelHeight);
        player2HitShips.setFont(player2HitShips.getFont().deriveFont(13.0f));
        player1SuccessfulBombs = new JLabel();
        player1SuccessfulBombs.setHorizontalAlignment(SwingConstants.CENTER);
        player1SuccessfulBombs.setForeground(Color.BLACK);
        player1SuccessfulBombs.setBounds(25, 156, labelWidth, labelHeight);
        player1SuccessfulBombs.setFont(player1SuccessfulBombs.getFont().deriveFont(13.0f));
        player2SuccessfulBombs = new JLabel();
        player2SuccessfulBombs.setHorizontalAlignment(SwingConstants.CENTER);
        player2SuccessfulBombs.setForeground(Color.BLACK);
        player2SuccessfulBombs.setBounds(375, 156, labelWidth, labelHeight);
        player2SuccessfulBombs.setFont(player2SuccessfulBombs.getFont().deriveFont(13.0f));
        Config buttonsConfig = Config.getConfig("buttons");
        Config b120x25Properties = buttonsConfig.getProperty(Config.class, "b120x25");
        int bWidth = b120x25Properties.getProperty(Integer.class, "width");
        int bHeight = b120x25Properties.getProperty(Integer.class, "height");
        watchGameButton.setBounds(240, 87, bWidth, bHeight);
        watchGameButton.addActionListener(this);
    }

    private void addElements() {
        this.add(player1Name);
        this.add(player2Name);
        this.add(vS);
        this.add(player1Moves);
        this.add(player2Moves);
        this.add(player1HitShips);
        this.add(player2HitShips);
        this.add(player1SuccessfulBombs);
        this.add(player2SuccessfulBombs);
        this.add(watchGameButton);
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name.setText(player1Name);
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name.setText(player2Name);
    }

    public void setPlayer1Moves(String player1Moves) {
        this.player1Moves.setText(player1Moves);
    }

    public void setPlayer2Moves(String player2Moves) {
        this.player2Moves.setText(player2Moves);
    }

    public void setPlayer1HitShips(String player1HitShips) {
        this.player1HitShips.setText(player1HitShips);
    }

    public void setPlayer2HitShips(String player2HitShips) {
        this.player2HitShips.setText(player2HitShips);
    }

    public void setPlayer1SuccessfulBombs(String player1SuccessfulBombs) {
        this.player1SuccessfulBombs.setText(player1SuccessfulBombs);
    }

    public void setPlayer2SuccessfulBombs(String player2SuccessfulBombs) {
        this.player2SuccessfulBombs.setText(player2SuccessfulBombs);
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == watchGameButton) {
            eventListener.listen(new WatchGameEvent(player1Name.getText(), player2Name.getText()));
        }
    }
}
