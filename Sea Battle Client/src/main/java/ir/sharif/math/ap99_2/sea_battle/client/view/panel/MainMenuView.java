package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.shared.events.GetLiveGameListEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.events.GetProfileEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.events.GetScoreBoardEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.events.NewGameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel implements ActionListener {

    private final JButton newGameButton = new JButton("New Game");
    private final JButton watchGameButton = new JButton("Watch Game");
    private final JButton scoreBoardButton = new JButton("ScoreBoard");
    private final JButton profileButton = new JButton("Profile");
    private final EventListener eventListener;
    private final GraphicalAgent graphicalAgent;


    public MainMenuView(EventListener eventListener, GraphicalAgent graphicalAgent) {
        this.eventListener = eventListener;
        this.graphicalAgent = graphicalAgent;
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        configElements();
        addElements();
    }

    private void configElements() {
        newGameButton.setFont(newGameButton.getFont().deriveFont(15.0f));
        watchGameButton.setFont(watchGameButton.getFont().deriveFont(15.0f));
        scoreBoardButton.setFont(scoreBoardButton.getFont().deriveFont(15.0f));
        profileButton.setFont(profileButton.getFont().deriveFont(15.0f));

        newGameButton.setBackground(Color.WHITE);
        watchGameButton.setBackground(Color.WHITE);
        scoreBoardButton.setBackground(Color.WHITE);
        profileButton.setBackground(Color.WHITE);

        newGameButton.setForeground(Color.BLACK);
        watchGameButton.setForeground(Color.BLACK);
        scoreBoardButton.setForeground(Color.BLACK);
        profileButton.setForeground(Color.BLACK);

        newGameButton.setFocusable(false);
        watchGameButton.setFocusable(false);
        scoreBoardButton.setFocusable(false);
        profileButton.setFocusable(false);

        newGameButton.addActionListener(this);
        watchGameButton.addActionListener(this);
        scoreBoardButton.addActionListener(this);
        profileButton.addActionListener(this);

        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b400x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");

        newGameButton.setBounds(440, 165, bWidth, bHeight);
        watchGameButton.setBounds(440, 265, bWidth, bHeight);
        profileButton.setBounds(440, 365, bWidth, bHeight);
        scoreBoardButton.setBounds(440, 465, bWidth, bHeight);
    }

    private void addElements() {
        this.add(newGameButton);
        this.add(watchGameButton);
        this.add(scoreBoardButton);
        this.add(profileButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            eventListener.listen(new NewGameEvent());
            graphicalAgent.setEndGame(false);
        }
        if (e.getSource() == watchGameButton) {
            eventListener.listen(new GetLiveGameListEvent());
        }
        if (e.getSource() == scoreBoardButton) {
            eventListener.listen(new GetScoreBoardEvent());
        }
        if (e.getSource() == profileButton) {
            eventListener.listen(new GetProfileEvent());
        }


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }
}
