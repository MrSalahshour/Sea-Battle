package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LiveGameListPanel extends JPanel implements ActionListener {

    private final JButton backToMenuButton = new JButton("BACK");
    private JScrollPane listOfGames;
    private final GraphicalAgent graphicalAgent;
    private final JPanel panel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private int panelY = 0;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public LiveGameListPanel(GraphicalAgent graphicalAgent) {
        this.graphicalAgent = graphicalAgent;
        Config mainPanel = Config.getConfig("mainPanel");
        int x = mainPanel.getProperty(Integer.class, "x");
        int y = mainPanel.getProperty(Integer.class, "y");
        int width = mainPanel.getProperty(Integer.class, "width");
        int height = mainPanel.getProperty(Integer.class, "height");
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        configElements();

    }

    private void configElements() {
        Config listScrollPane = Config.getConfig("gameListScrollPane");
        int width = listScrollPane.getProperty(Integer.class, "width");
        int height = listScrollPane.getProperty(Integer.class, "height");

        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b200x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");
        backToMenuButton.setBounds(70, 40, bWidth, bHeight);
        backToMenuButton.addActionListener(this);
        this.add(backToMenuButton);


        panel.setSize(width, height);
        panel.setLayout(new GridBagLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(5, 0, 5, 0);
        this.listOfGames = new JScrollPane(panel);
        listOfGames.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.setBounds(340, 40, width, height);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(listOfGames, BorderLayout.CENTER);
        this.add(mainPanel);
    }

    public void resetPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        panelY = 0;
    }

    public void addToList(LiveGameDetailPanel liveGameDetailPanel) {
        liveGameDetailPanel.setPreferredSize(new Dimension
                (liveGameDetailPanel.getPanelWidth(), liveGameDetailPanel.getPanelHeight()));
        gridBagConstraints.gridy = panelY;
        panel.add(liveGameDetailPanel, gridBagConstraints);
        panel.revalidate();
        panel.repaint();
        listOfGames.revalidate();
        listOfGames.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();
        this.revalidate();
        this.repaint();
        panelY++;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMenuButton) {
            graphicalAgent.goToMainMenu();
        }
    }
}
