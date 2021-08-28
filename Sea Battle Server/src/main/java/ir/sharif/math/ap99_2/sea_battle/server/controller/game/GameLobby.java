package ir.sharif.math.ap99_2.sea_battle.server.controller.game;

import ir.sharif.math.ap99_2.sea_battle.server.controller.ClientHandler;

import java.util.LinkedList;

public class GameLobby {
    private ClientHandler waiting;
    private final LinkedList<Game> liveGames = new LinkedList<>();

    public GameLobby() {
    }

    public synchronized void startGameRequest(ClientHandler clientHandler) {
        if (waiting == null) {
            waiting = clientHandler;
            clientHandler.setSide(Side.PLAYER_ONE);
        } else {
            if (waiting != clientHandler) {
                Game game = new Game(clientHandler.getResponseSender(), waiting.getResponseSender(), clientHandler.getCurrentPlayer(),waiting.getCurrentPlayer()); // new
                clientHandler.setSide(Side.PLAYER_TWO);
                waiting.setGame(game);
                clientHandler.setGame(game);
                if (liveGames.isEmpty())
                    liveGames.add(game);
                else {
                    for (Game g : liveGames) {
                        if (g.getPlayer1().getUsername().equals(game.getPlayer1().getUsername())
                                || g.getPlayer1().getUsername().equals(game.getPlayer2().getUsername()))
                            continue;
                        liveGames.add(game);
                    }
                }
                waiting = null;
            }
        }
    }

    public LinkedList<Game> getLiveGames() {
        return liveGames;
    }
}
