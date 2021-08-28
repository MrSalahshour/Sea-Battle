package ir.sharif.math.ap99_2.sea_battle.server.controller.game;

import ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters.ResponseSender;
import ir.sharif.math.ap99_2.sea_battle.server.model.Player;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Cell;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Ship;
import ir.sharif.math.ap99_2.sea_battle.shared.response.ShowMessageResponse;
import ir.sharif.math.ap99_2.sea_battle.shared.util.Loop;

import java.util.LinkedList;

public class Game {
    private final ResponseSender player1ResponseSender,player2ResponseSender;
    private Board player1Board,player2Board;
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private int player1Time = 30;
    private int player2Time = 30;
    private Side sideToTurn;
    private Side winner;
    private final Loop player1TimeUpdater;
    private final Loop player2TimeUpdater;
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    private final Player player1,player2;
    private int player1Moves = 0;
    private int player2Moves = 0;

    public Game(ResponseSender player1ResponseSender, ResponseSender player2ResponseSender, Player player1, Player player2) {
        this.player1ResponseSender = player1ResponseSender;
        this.player2ResponseSender = player2ResponseSender;
        this.player1 = player1;
        this.player2 = player2;
        Loop turnChecker = new Loop(5, this::checkForTurn);
        this.player1TimeUpdater = new Loop(1, this::updatePlayer1Time);
        this.player2TimeUpdater = new Loop(1, this::updatePlayer2Time);
        player1TimeUpdater.start();
        player2TimeUpdater.start();
        turnChecker.start();
        sideToTurn = Side.PLAYER_ONE;
        player1Board = BoardBuilder.buildNewBoard();
        player2Board = BoardBuilder.buildNewBoard();
    }

    synchronized public void clickOnCell(int x, int y, Side side){
        if (gameStatus == GameStatus.NOT_STARTED)
            return;
        if (side!=sideToTurn)
            return;
        Board board;
        if (side == Side.PLAYER_ONE){
            board = player2Board;
            player1Moves++;
        }
        else{
            board = player1Board;
            player2Moves++;
        }
        Cell cell = board.getCell(x,y);
        if (cell.isSelected())
            return;
        cell.setSelected(true);
        boolean isHit = checkForHitShip();
        checkForEnd();
        if (!isHit) {
            if (cell.isHasShip()){
                if (sideToTurn == Side.PLAYER_ONE)
                    player1Time = 25;
                else {
                    player2Time = 25;
                }
                return;
            }
            if (sideToTurn == Side.PLAYER_ONE){
                player1TimeUpdater.stop();
                player1Time = 25;
                player2Time = 25;
                player2TimeUpdater.restart();
            }
            else {
                player2TimeUpdater.stop();
                player2Time = 25;
                player1Time = 25;
                player1TimeUpdater.restart();
            }
            sideToTurn = sideToTurn.getOther();
        }
        else {
            if (sideToTurn == Side.PLAYER_ONE)
                player1Time = 25;
            else {
                player2Time = 25;
            }
        }
    }
    synchronized public void clickOnReset(Side side){
        if (gameStatus == GameStatus.PLAYING)
            return;

        if (side == Side.PLAYER_ONE && !player1Ready){
            player1Board = BoardBuilder.buildNewBoard();
            player1Time+=10;
        }
        else{
            if (!player2Ready){
                player2Board = BoardBuilder.buildNewBoard();
                player2Time+=10;
            }
        }
    }
    synchronized public void clickOnReady(Side side){
        if (side == Side.PLAYER_ONE)
            setPlayer1Ready(true);
        else
            setPlayer2Ready(true);

        if (player1Ready && player2Ready)
            startGame();
        else {
            ResponseSender responseSender;
            if (side == Side.PLAYER_ONE)
                responseSender = player2ResponseSender;
            else
                responseSender = player1ResponseSender;
            responseSender.sendResponse(new ShowMessageResponse("Other Player is not Ready yet!"));
        }
    }
    private void startGame(){
        gameStatus = GameStatus.PLAYING;
        player1TimeUpdater.stop();
        player2TimeUpdater.stop();
        player1Time = 25;
        player2Time = 25;
        player1TimeUpdater.restart();
    }
    public void checkForEnd(){
        First:for (int i = 1; i <11 ; i++) {
            for (int j = 1; j <11 ; j++) {
                Cell cell = player1Board.getCell(i,j);
                if (cell.isHasShip() && !cell.isSelected())
                    break First;
            }
            if (i==10){
                winner = Side.PLAYER_TWO;
                gameStatus = GameStatus.ENDED;
            }
        }
        Second:for (int i = 1; i <11 ; i++) {
            for (int j = 1; j <11 ; j++) {
                Cell cell = player2Board.getCell(i,j);
                if (cell.isHasShip() && !cell.isSelected())
                    break Second;
            }
            if (i==10){
                winner = Side.PLAYER_ONE;
                gameStatus = GameStatus.ENDED;
            }
        }
    }

    public boolean checkForHitShip(){
        if (sideToTurn ==Side.PLAYER_ONE){
            return handleShipHit(player2Board);
        }
        else {
            return handleShipHit(player1Board);
        }
    }

    private boolean handleShipHit(Board playerBoard) {
        LinkedList<Ship> playerShips = playerBoard.getShips();
        for (Ship playerShip : playerShips) {
            if (!playerShip.isAlive())
                continue;
            LinkedList<Cell> locations = playerShip.getLocations();
            for (int i = 0; i <locations.size() ; i++) {
                if (!locations.get(i).isSelected())
                    break;
                if (i == locations.size()-1){
                    for (int k = 0; k <playerShip.getAdjacentCells().size(); k++) {
                        playerShip.getAdjacentCells().get(k).setSelected(true);
                    }
                    playerShip.setAlive(false);
                    return true;
                }
            }
        }
        return false;
    }

    public void checkForTurn(){
        if (gameStatus == GameStatus.NOT_STARTED){
            if (player1Time == 0 && player2Time==0){
                setPlayer1Ready(true);
                setPlayer2Ready(true);
                startGame();
            }
            if (player1Time == 0 && !player1Ready){
                setPlayer1Ready(true);
                player1TimeUpdater.stop();
            }
            if (player2Time == 0 && !player2Ready){
                setPlayer2Ready(true);
                player2TimeUpdater.stop();
            }
            if (player1Ready && player2Ready)
                startGame();
        }
        if (gameStatus == GameStatus.PLAYING){
            if (sideToTurn == Side.PLAYER_ONE){
                if (player1Time == 0){
                    player1TimeUpdater.stop();
                    player1Time = 25;
                    sideToTurn = sideToTurn.getOther();
                    player2TimeUpdater.restart();
                }
            }
            else {
                if (player2Time == 0){
                    player2TimeUpdater.stop();
                    player2Time = 25;
                    sideToTurn = sideToTurn.getOther();
                    player1TimeUpdater.restart();
                }
            }
        }

    }

    public Board getPlayer1Board() {
        return player1Board;
    }

    public Board getPlayer2Board() {
        return player2Board;
    }

    public int getPlayer1Time() {
        return player1Time;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setPlayer1Ready(boolean player1Ready) {
        this.player1Ready = player1Ready;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2Ready(boolean player2Ready) {
        this.player2Ready = player2Ready;
    }

    public int getPlayer2Time() {
        return player2Time;
    }

    public Side getWinner() {
        return winner;
    }

    private void updatePlayer1Time(){
        player1Time--;
    }

    private void updatePlayer2Time(){
        player2Time--;
    }

    public int getPlayerMoves(Side side){
        int moves;
        if (side == Side.PLAYER_ONE)
            moves = player1Moves;
        else
            moves = player2Moves;
        return moves;
    }
    public int getPlayerHitShips(Side side){
        int hitSips = 0;
        LinkedList<Ship> ships;
        if (side == Side.PLAYER_ONE)
            ships = player2Board.getShips();
        else
            ships = player1Board.getShips();
        for (Ship ship:ships) {
            if (!ship.isAlive())
                hitSips++;
        }
        return hitSips;
    }
    public int getSuccessfulBombs(Side side){
        int successfulBombs = 0;
        LinkedList<Cell> cells;
        if (side == Side.PLAYER_ONE)
            cells = player2Board.getCells();
        else
            cells = player1Board.getCells();
        for (Cell cell: cells) {
            if (cell.isSelected() && cell.isHasShip())
                successfulBombs++;
        }
        return successfulBombs;
    }

}
