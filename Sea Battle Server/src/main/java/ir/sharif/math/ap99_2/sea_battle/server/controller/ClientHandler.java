package ir.sharif.math.ap99_2.sea_battle.server.controller;

import ir.sharif.math.ap99_2.sea_battle.server.controller.database.PlayerDB;
import ir.sharif.math.ap99_2.sea_battle.server.controller.game.GameLobby;
import ir.sharif.math.ap99_2.sea_battle.server.controller.game.Game;
import ir.sharif.math.ap99_2.sea_battle.server.controller.game.GameStatus;
import ir.sharif.math.ap99_2.sea_battle.server.controller.game.Side;

import ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters.ResponseSender;
import ir.sharif.math.ap99_2.sea_battle.server.model.Player;
import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.events.EventVisitor;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.GameDetail;
import ir.sharif.math.ap99_2.sea_battle.shared.response.*;

import java.util.Collections;
import java.util.LinkedList;

public class ClientHandler extends Thread implements EventVisitor {
    private final ResponseSender responseSender;
    private volatile boolean running;
    private Player currentPlayer;
    private Side side;
    private final PlayerDB playerDB;
    private final GameLobby gameLobby;
    private Game game;

    public ClientHandler(ResponseSender responseSender, PlayerDB playerDB, GameLobby gameLobby) {
        this.responseSender = responseSender;
        this.playerDB = playerDB;
        this.gameLobby = gameLobby;
        this.running = true;
    }

    public void run() {
        while (running) {
            Event event = responseSender.getEvent();
            Response response = event.visit(this);
            responseSender.sendResponse(response);
        }
    }


    @Override
    public Response getProfile() {
        String username = "Username: "+currentPlayer.getUsername();
        String score = "Score: "+currentPlayer.getScore();
        String wins = "Wins: "+currentPlayer.getWins();
        String loses = "Loses: "+currentPlayer.getLoses();
        return new GetProfileResponse(username,score,wins,loses);
    }

    @Override
    public Response login(String username, String password, int mode) {
        if (mode == 1)
            return signIn(username, password);
        if (mode == 2)
            return register(username, password);
        return Response.getWrongApi();
    }

    private Response signIn(String username, String password) {
        Player player = playerDB.getByUserName(username);
        if (player != null) {
            if (player.getPassword().equals(password)) {
                this.currentPlayer = player;
                currentPlayer.setOnline(true);
                playerDB.savePlayer(this.currentPlayer);
                return new LoginResponse(true, this.currentPlayer.getUsername());
            } else {
                return new LoginResponse(false, "wrong password");
            }
        } else {
            return new LoginResponse(false, "username not exist");
        }
    }

    private Response register(String username, String password) {
        if (username.equals("") || password.equals("")){
            return new LoginResponse(false, "invalid username or password");
        }
        Player player = playerDB.getByUserName(username);
        if (player == null) {
            player = new Player(username, password);
            this.currentPlayer = player;
            currentPlayer.setOnline(true);
            playerDB.savePlayer(player);
            return new LoginResponse(true, this.currentPlayer.getUsername());
        } else {
            return new LoginResponse(false, "username already exist");
        }
    }

    @Override
    public Response getScoreBoard() {
        LinkedList<Player> players = playerDB.all();
        Collections.sort(players);
        StringBuilder scoreBoard = new StringBuilder();
        for (Player player: players) {
            String status;
            if (player.isOnline())
                status ="Online";
            else
                status ="Offline";
            scoreBoard.append(" ").append(player.getUsername()).append(" , ").append("Score: ")
                    .append(player.getScore()).append(" , ").append(status).append("\n");
        }
        return new GetScoreBoardResponse(scoreBoard.toString());
    }

    @Override
    public Response clickOnCell(int x, int y) {
        game.clickOnCell(x,y,side);
        return getBoard();
    }

    @Override
    public Response getBoard() {
        if (game == null){
            return new GetBoardResponse(null, null);
        }
        Board player;
        Board opponent;
        if (side == Side.PLAYER_ONE){
            player = game.getPlayer1Board();
            opponent = game.getPlayer2Board();
        }
        else {
            player = game.getPlayer2Board();
            opponent = game.getPlayer1Board();
        }
        return new GetBoardResponse(player, opponent);
    }

    @Override
     public Response gameDetail() {
        if (game == null)
            return new VoidResponse();
        String playerTime;
        if (side == Side.PLAYER_ONE)
            playerTime = String.valueOf(game.getPlayer1Time());
        else
            playerTime = String.valueOf(game.getPlayer2Time());
        if (game.getGameStatus() == GameStatus.ENDED){
            String message;
            if (side == game.getWinner()){
                currentPlayer.increaseWins();
                message = "YOU WON!";
            }
            else{
                currentPlayer.increaseLoses();
                message = "YOU LOST!";
            }
            playerDB.savePlayer(currentPlayer);
            gameLobby.getLiveGames().remove(this.game);
            this.game = null;
            return new BackToMainMenuResponse(message);
        }

        return new GameDetailResponse("Time: "+playerTime);
    }

    @Override
    public Response clickOnReady() {
        game.clickOnReady(side);
        return getBoard();
    }

    @Override
    public Response clickOnReset() {
        game.clickOnReset(side);
        return getBoard();
    }

    @Override
    public Response newGame() {
        gameLobby.startGameRequest(this);
        return getBoard();
    }

    @Override
    public Response logout() {
        running = false;
        currentPlayer.setOnline(false);
        playerDB.savePlayer(currentPlayer);
        LinkedList<Game> deletedGames = new LinkedList<>();
        for (Game game:gameLobby.getLiveGames()) {
            if (game.getPlayer1().getUsername().equals(currentPlayer.getUsername())
            || game.getPlayer2().getUsername().equals(currentPlayer.getUsername())){
                deletedGames.add(game);
            }
        }
        for (Game game: deletedGames) {
            gameLobby.getLiveGames().remove(game);
        }
        return new VoidResponse();
    }

    @Override
    public Response getLiveGamesList() {
        if (gameLobby.getLiveGames().isEmpty()){
            return new ShowMessageResponse("There is No Game to Watch!");
        }
        else {
            LinkedList<GameDetail> gameDetailsList = new LinkedList<>();
            for (Game game: gameLobby.getLiveGames()) {
                GameDetail gameDetail = new GameDetail(game.getPlayer1().getUsername(),
                        game.getPlayer2().getUsername(),game.getPlayerMoves(Side.PLAYER_ONE)
                        ,game.getPlayerMoves(Side.PLAYER_TWO),game.getPlayerHitShips(Side.PLAYER_ONE)
                        ,game.getPlayerHitShips(Side.PLAYER_TWO),game.getSuccessfulBombs(Side.PLAYER_ONE)
                        ,game.getSuccessfulBombs(Side.PLAYER_TWO));
                gameDetailsList.add(gameDetail);
            }
            return new LiveGameListResponse(gameDetailsList);
        }
    }

    @Override
    public Response watchGame(String player1Name,String player2Name) {
        for (Game game: gameLobby.getLiveGames()) {
            if (game.getPlayer1().getUsername().equals(player1Name)
                    || game.getPlayer2().getUsername().equals(player1Name)){
                return new WatchGameResponse(game.getPlayer1Board(),game.getPlayer2Board(),player1Name,player2Name);
            }
        }
        return new VoidResponse();
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ResponseSender getResponseSender() {
        return responseSender;
    }
}
