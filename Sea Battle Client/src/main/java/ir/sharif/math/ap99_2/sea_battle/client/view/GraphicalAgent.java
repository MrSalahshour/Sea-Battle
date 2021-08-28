package ir.sharif.math.ap99_2.sea_battle.client.view;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventListener;
import ir.sharif.math.ap99_2.sea_battle.client.view.panel.*;
import ir.sharif.math.ap99_2.sea_battle.shared.events.*;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;

import javax.swing.*;
import java.util.EnumMap;
import java.util.Map;

public class GraphicalAgent {
    private final EventListener eventListener;
    private final MyFrame frame;
    private final Map<PanelType, JPanel> panels;
    private PanelType now;
    private String player1NameWatchingGame , player2NameWatchingGame;
    private boolean endGame = true;


    public GraphicalAgent(EventListener eventListener) {
        now = PanelType.LOGIN;
        this.eventListener = eventListener;
        this.frame = new MyFrame();
        panels = new EnumMap<>(PanelType.class);
        LoginView loginView =new LoginView(eventListener);
        panels.put(PanelType.LOGIN,loginView);
        MainMenuView mainMenuView = new MainMenuView(eventListener, this);
        panels.put(PanelType.MAIN_MENU,mainMenuView);
        ProfileView profileView = new ProfileView(this);
        panels.put(PanelType.PROFILE,profileView);
        ScoreBoardView scoreBoardView = new ScoreBoardView(this);
        panels.put(PanelType.SCORE_BOARD,scoreBoardView);
        LiveGameListPanel liveGameListPanel = new LiveGameListPanel(this);
        panels.put(PanelType.WATCH_GAME_LIST,liveGameListPanel);
        //add more panels
        updateMainPanel();

    }

    public void setNow(PanelType now) {
        this.now = now;
    }

    public void updateMainPanel(){
        frame.getMainPanel().removeAll();
        frame.getMainPanel().add(panels.get(now));
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(null,message);
    }

    public void goToScoreBoard(String scoreBoard){
        ScoreBoardView panel = (ScoreBoardView) panels.get(PanelType.SCORE_BOARD);
        panel.setShowPlayersTextArea(scoreBoard);
        now = PanelType.SCORE_BOARD;
        updateMainPanel();
    }

    public void goToProfile(String username,String score,String wins,String loses){
        ProfileView panel = (ProfileView) panels.get(PanelType.PROFILE);
        panel.setWinsLabelText(wins);
        panel.setUsernameLabelText(username);
        panel.setScoresLabelText(score);
        panel.setLosesLabelText(loses);
        now = PanelType.PROFILE;
        updateMainPanel();
    }

    public void goToMainMenu(){
        now = PanelType.MAIN_MENU;
        LoginView panel = (LoginView) panels.get(PanelType.LOGIN);
        panel.reset();
        updateMainPanel();
    }
    public void backToMainMenuFromGame(){
        now = PanelType.MAIN_MENU;
        endGame = true;
        updateMainPanel();

    }
    public void goToWatchGameList(){
        now = PanelType.WATCH_GAME_LIST;
        updateMainPanel();
    }

    public void gotoGamePanel(Board playerBoard,Board opponentBoard) {
        GamePanel gamePanel;
        if (now != PanelType.GAME_PANEL ) {
            gamePanel = new GamePanel(eventListener);
            panels.put(PanelType.GAME_PANEL, gamePanel);
            now = PanelType.GAME_PANEL;

        } else {
            gamePanel = (GamePanel) panels.get(PanelType.GAME_PANEL);
            //need to be checked
        }
        if (playerBoard == null){
            gamePanel.setTitleLabelText("Waiting for Other Player!");
        }
        else {
            gamePanel.setPlayerBoardPanel(playerBoard);
            gamePanel.setOpponentBoardPanel(opponentBoard);
            gamePanel.setTitleLabelText("");
        }
        updateMainPanel();
    }

    public void goToWatchGamePanel(Board player1Board,Board player2Board,String player1Name,String player2Name){
        WatchGamePanel watchGamePanel;
        if (now!=PanelType.WATCH_GAME){
            watchGamePanel = new WatchGamePanel(eventListener,this);
            watchGamePanel.setPlayer1BoardName(player1Name);
            watchGamePanel.setPlayer2BoardName(player2Name);
            panels.put(PanelType.WATCH_GAME, watchGamePanel);
            now = PanelType.WATCH_GAME;

        } else {
            watchGamePanel = (WatchGamePanel) panels.get(PanelType.WATCH_GAME);
            //need to be checked
        }
        watchGamePanel.setPlayer1BoardPanel(player1Board);
        watchGamePanel.setPlayer2BoardPanel(player2Board);
        updateMainPanel();
    }

    public void setPlayer2NameWatchingGame(String player2NameWatchingGame) {
        this.player2NameWatchingGame = player2NameWatchingGame;
    }

    public void setPlayer1NameWatchingGame(String player1NameWatchingGame) {
        this.player1NameWatchingGame = player1NameWatchingGame;
    }

    public PanelType getNow() {
        return now;
    }

    public void update(boolean isGameOn){

        if (endGame && now == PanelType.GAME_PANEL){
            now = PanelType.MAIN_MENU;
            updateMainPanel();
        }
        switch (now){
            case GAME_PANEL -> {
                eventListener.listen(new GetBoardEvent());
                if (isGameOn){
                    eventListener.listen(new GameDetailEvent());
                }
            }
            case SCORE_BOARD -> eventListener.listen(new GetScoreBoardEvent());

            case WATCH_GAME_LIST -> eventListener.listen(new GetLiveGameListEvent());

            case WATCH_GAME -> {
                if (player1NameWatchingGame!=null)
                    eventListener.listen(new WatchGameEvent(player1NameWatchingGame, player2NameWatchingGame));
            }
        }
    }

    public Map<PanelType, JPanel> getPanels() {
        return panels;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
}
