package ir.sharif.math.ap99_2.sea_battle.client.controller;

import ir.sharif.math.ap99_2.sea_battle.client.listener.EventSender;
import ir.sharif.math.ap99_2.sea_battle.client.view.GraphicalAgent;
import ir.sharif.math.ap99_2.sea_battle.client.view.PanelType;
import ir.sharif.math.ap99_2.sea_battle.client.view.panel.GamePanel;
import ir.sharif.math.ap99_2.sea_battle.client.view.panel.LiveGameDetailPanel;
import ir.sharif.math.ap99_2.sea_battle.client.view.panel.LiveGameListPanel;
import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.GameDetail;
import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;
import ir.sharif.math.ap99_2.sea_battle.shared.response.ResponseVisitor;
import ir.sharif.math.ap99_2.sea_battle.shared.util.Loop;

import javax.swing.*;
import java.util.*;

public class MainController implements ResponseVisitor  {
    private final EventSender eventSender;
    private final List<Event> events;
    private final Loop loop;
    private final GraphicalAgent graphicalAgent;

    public MainController(EventSender eventSender) {
        this.eventSender = eventSender;
        this.events = new LinkedList<>();
        this.loop = new Loop(10, this::sendEvents);
        this.graphicalAgent = new GraphicalAgent(this::addEvent);


    }
    public void start() {
        loop.start();
        Loop updater = new Loop(2, this::update);
        updater.start();
    }


    private void addEvent(Event event) {
        synchronized (events) {
            events.add(event);
        }
    }

    private void sendEvents() {
        List<Event> temp;
        synchronized (events) {
            temp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : temp) {
            Response response = eventSender.send(event);
            response.visit(this);
        }
    }

    @Override
    public void getProfile(String username, String score, String wins, String loses) {
        graphicalAgent.goToProfile(username, score, wins, loses);

    }

    @Override
    public void login(boolean success, String message) {
        if(success)
            graphicalAgent.goToMainMenu();
        else
            graphicalAgent.showMessage(message);
    }

    @Override
    public void getScoreBoard(String scoreBoard) {
        graphicalAgent.goToScoreBoard(scoreBoard);
    }

    @Override
    public void showMessage(String message) {
        graphicalAgent.showMessage(message);
    }

    @Override
    public void visitBoards(Board playerBoard, Board opponentBoard) {
        graphicalAgent.gotoGamePanel(playerBoard,opponentBoard);
    }

    @Override
    public void setGameDetail(String playerTimer) {
        if (graphicalAgent.getNow() == PanelType.GAME_PANEL){
            GamePanel gamePanel = (GamePanel) graphicalAgent.getPanels().get(PanelType.GAME_PANEL);
            gamePanel.setTimerLabelText(playerTimer);
            gamePanel.revalidate();
            gamePanel.repaint();
        }
    }

    @Override
    public void BackToMainMenu(String message) {
        graphicalAgent.backToMainMenuFromGame();
        JOptionPane.showMessageDialog(null,message);

    }

    @Override
    public void setLiveGamesList(LinkedList<GameDetail> gameDetailsList) {
        graphicalAgent.goToWatchGameList();
        LiveGameListPanel liveGameListPanel = (LiveGameListPanel) graphicalAgent.getPanels().get(PanelType.WATCH_GAME_LIST);
        liveGameListPanel.resetPanel();
        for (GameDetail gameDetail: gameDetailsList) {
            LiveGameDetailPanel liveGameDetailPanel = new LiveGameDetailPanel(graphicalAgent.getEventListener());
            liveGameDetailPanel.setPlayer1Name(gameDetail.getPlayer1Name());
            liveGameDetailPanel.setPlayer2Name(gameDetail.getPlayer2Name());
            liveGameDetailPanel.setPlayer1Moves("Moves: "+gameDetail.getPlayer1Moves());
            liveGameDetailPanel.setPlayer2Moves("Moves: "+gameDetail.getPlayer2Moves());
            liveGameDetailPanel.setPlayer1HitShips("Hit Ships: "+ gameDetail.getPlayer1HitShips());
            liveGameDetailPanel.setPlayer2HitShips("Hit Ships: "+ gameDetail.getPlayer2HitShips());
            liveGameDetailPanel.setPlayer1SuccessfulBombs("Successful Bombs: "+gameDetail.getPlayer1SuccessfulBombs());
            liveGameDetailPanel.setPlayer2SuccessfulBombs("Successful Bombs: "+gameDetail.getPlayer2SuccessfulBombs());
            liveGameDetailPanel.revalidate();
            liveGameDetailPanel.repaint();
            liveGameListPanel.addToList(liveGameDetailPanel);
        }
    }

    @Override
    public void voidAction() {
    }

    @Override
    public void watchGame(String player1Name,String player2Name, Board player1Board, Board player2Board) {
        graphicalAgent.setPlayer1NameWatchingGame(player1Name);
        graphicalAgent.setPlayer2NameWatchingGame(player2Name);
        graphicalAgent.goToWatchGamePanel(player1Board,player2Board,player1Name,player2Name);
    }


    private void update(){

        if (graphicalAgent.getPanels().containsKey(PanelType.GAME_PANEL)){
            GamePanel gamePanel = (GamePanel) graphicalAgent.getPanels().get(PanelType.GAME_PANEL);
            graphicalAgent.update(gamePanel.getPlayerBoardPanel() != null);
        }
        else {
            graphicalAgent.update(false);
        }

    }





}
