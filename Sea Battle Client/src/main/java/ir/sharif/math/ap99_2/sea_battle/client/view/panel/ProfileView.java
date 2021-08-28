package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileView extends JPanel implements ActionListener {
    private final JLabel usernameLabel = new JLabel();
    private final JLabel winsLabel = new JLabel();
    private final JLabel losesLabel = new JLabel();
    private final JLabel scoresLabel = new JLabel();
    private final JButton backToMenu = new JButton("Back To Menu");
    private final GraphicalAgent graphicalAgent;

    public ProfileView(GraphicalAgent graphicalAgent) {
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
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        winsLabel.setVerticalAlignment(SwingConstants.CENTER);
        losesLabel.setVerticalAlignment(SwingConstants.CENTER);
        scoresLabel.setVerticalAlignment(SwingConstants.CENTER);

        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        losesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoresLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Config labelsConfig = Config.getConfig("labels");
        Config l100x20Properties = labelsConfig.getProperty(Config.class, "l200x20");
        int lWidth = l100x20Properties.getProperty(Integer.class, "width");
        int lHeight = l100x20Properties.getProperty(Integer.class, "height");

        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b200x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");

        usernameLabel.setBounds(540, 237, lWidth, lHeight);
        scoresLabel.setBounds(540, 282, lWidth, lHeight);
        winsLabel.setBounds(540, 307, lWidth, lHeight);
        losesLabel.setBounds(540, 332, lWidth, lHeight);
        backToMenu.setBounds(540, 377, bWidth, bHeight);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(17.0f));
        scoresLabel.setFont(scoresLabel.getFont().deriveFont(17.0f));
        winsLabel.setFont(winsLabel.getFont().deriveFont(17.0f));
        losesLabel.setFont(losesLabel.getFont().deriveFont(17.0f));
        backToMenu.setFont(backToMenu.getFont().deriveFont(17.0f));
        usernameLabel.setForeground(Color.WHITE);
        scoresLabel.setForeground(Color.WHITE);
        winsLabel.setForeground(Color.WHITE);
        losesLabel.setForeground(Color.WHITE);
        backToMenu.setFocusable(false);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        losesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backToMenu.addActionListener(this);


    }

    private void addElements() {
        this.add(usernameLabel);
        this.add(scoresLabel);
        this.add(losesLabel);
        this.add(winsLabel);
        this.add(backToMenu);
    }

    public void setUsernameLabelText(String usernameLabel) {
        this.usernameLabel.setText(usernameLabel);
    }

    public void setWinsLabelText(String winsLabel) {
        this.winsLabel.setText(winsLabel);
    }

    public void setLosesLabelText(String losesLabel) {
        this.losesLabel.setText(losesLabel);
    }

    public void setScoresLabelText(String scoresLabel) {
        this.scoresLabel.setText(scoresLabel);
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
