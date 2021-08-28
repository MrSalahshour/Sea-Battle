package ir.sharif.math.ap99_2.sea_battle.shared.response;

import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;

public class WatchGameResponse extends Response {
    private final Board player1Board;
    private final Board player2Board;
    private final String player1Name;
    private final String player2Name;

    public WatchGameResponse(Board player1Board, Board player2Board, String player1Name, String player2Name) {
        this.player1Board = player1Board;
        this.player2Board = player2Board;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.watchGame(player1Name,player2Name,player1Board,player2Board);
    }
}
