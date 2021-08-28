package ir.sharif.math.ap99_2.sea_battle.client.view.panel;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.view.ImageLoader;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;
import ir.sharif.math.ap99_2.sea_battle.shared.events.LoginEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel implements ActionListener {
    private Mode mode;
    private final JTextField userNameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel userNameLabel = new JLabel("UserName: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JLabel welcomeLabel = new JLabel("Welcome To Sea Battle", SwingConstants.CENTER);
    private final JButton loginButton = new JButton("Login");
    private final JButton registerButton = new JButton("Register");
    private final EventListener eventListener;

    public LoginView(EventListener eventListener) {
        this.eventListener = eventListener;
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


    private void addElements() {
        this.add(userNameField);
        this.add(userNameLabel);
        this.add(passwordField);
        this.add(passwordLabel);
        this.add(loginButton);
        this.add(welcomeLabel);
        this.add(registerButton);
    }

    private void configElements() {
        Config textFieldConfig = Config.getConfig("textFields");
        Config tF200x20Properties = textFieldConfig.getProperty(Config.class, "tF200x20");
        int tWidth = tF200x20Properties.getProperty(Integer.class, "width");
        int tHeight = tF200x20Properties.getProperty(Integer.class, "height");
        userNameField.setBounds(590, 255, tWidth, tHeight);
        passwordField.setBounds(590, 305, tWidth, tHeight);

        Config labelsConfig = Config.getConfig("labels");
        Config l100x20Properties = labelsConfig.getProperty(Config.class, "l100x20");
        int lWidth = l100x20Properties.getProperty(Integer.class, "width");
        int lHeight = l100x20Properties.getProperty(Integer.class, "height");

        userNameLabel.setBounds(490, 255, lWidth, lHeight);
        passwordLabel.setBounds(490, 305, lWidth, lHeight);
        welcomeLabel.setBounds(290, 0, 700, 200);


        Config buttonsConfig = Config.getConfig("buttons");
        Config b200x50Properties = buttonsConfig.getProperty(Config.class, "b200x50");
        int bWidth = b200x50Properties.getProperty(Integer.class, "width");
        int bHeight = b200x50Properties.getProperty(Integer.class, "height");

        registerButton.setBounds(540, 425, bWidth, bHeight);
        loginButton.setBounds(540, 350, bWidth, bHeight);

        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(35.0f));
        loginButton.setFont(loginButton.getFont().deriveFont(15.0f));
        userNameLabel.setFont(userNameLabel.getFont().deriveFont(17.0f));
        userNameField.setFont(userNameField.getFont().deriveFont(15.0f));
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(17.0f));
        passwordField.setFont(passwordField.getFont().deriveFont(15.0f));
        registerButton.setFont(registerButton.getFont().deriveFont(15.0f));

        userNameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        welcomeLabel.setForeground(Color.WHITE);


        loginButton.setFocusable(false);
        registerButton.setFocusable(false);

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

    }

    public void reset() {
        passwordField.setEchoChar((char) 0);
        userNameField.setText("");
    }


    public enum Mode {
        LOGIN(1), REGISTER(2);
        private final int value;

        public int getValue() {
            return value;
        }

        Mode(int value) {
            this.value = value;
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getUserNameField() {
        return userNameField.getText();
    }

    public String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            this.setMode(Mode.LOGIN);
            handleLoginEvent();
        }
        if (e.getSource() == registerButton) {
            this.setMode(Mode.REGISTER);
            handleLoginEvent();
        }
    }

    private void handleLoginEvent() {
        eventListener.listen(new LoginEvent(getUserNameField(), getPasswordField(), mode.getValue()));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getBackgroundImage(), 0, 0, null);
    }

}
