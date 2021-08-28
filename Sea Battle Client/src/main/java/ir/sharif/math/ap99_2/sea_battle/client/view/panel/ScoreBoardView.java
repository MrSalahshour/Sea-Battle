package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoardView extends JPanel implements ActionListener {
    private final JButton backToMenu = new JButton("Back To Menu");
    private final JTextArea showPlayersTextArea = new JTextArea();
    private final GraphicalAgent graphicalAgent;

    public ScoreBoardView(GraphicalAgent graphicalAgent) {
        this.graphicalAgent = graphicalAgent;
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        JScrollPane scrollPane = configElements();
        addElements(scrollPane);
    }

    private void addElements(JScrollPane scrollPane) {
        this.add(scrollPane);
        this.add(backToMenu);

    }

    private JScrollPane configElements() {
        showPlayersTextArea.setEditable(false);
        showPlayersTextArea.setWrapStyleWord(true);
        showPlayersTextArea.setOpaque(false);
        showPlayersTextArea.setLineWrap(true);

        backToMenu.setFont(backToMenu.getFont().deriveFont(17.0f));
        backToMenu.setFocusable(false);
        backToMenu.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(showPlayersTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b200x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");

        Config textAreasConfig = Config.getConfig("textAreas");
        Config tA250x250Properties = textAreasConfig.getProperty(Config.class, "tA250x250");
        int tAWidth = tA250x250Properties.getProperty(Integer.class, "width");
        int tAHeight = tA250x250Properties.getProperty(Integer.class, "height");

        backToMenu.setBounds(540, 450, bWidth, bHeight);
        scrollPane.setBounds(515, 180, tAWidth, tAHeight);
        return scrollPane;

    }

    public void setShowPlayersTextArea(String showPlayersTextArea) {
        this.showPlayersTextArea.setText(showPlayersTextArea);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMenu) {
            graphicalAgent.goToMainMenu();
        }
    }
}
