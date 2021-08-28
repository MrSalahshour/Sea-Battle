package ir.sharif.math.ap99_2.sea_battle.shared.response;

import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;

public class GetBoardResponse extends Response {
    private final Board playerBoard;
    private final Board opponentBoard;

    public GetBoardResponse(Board playerBoard, Board opponentBoard) {
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.visitBoards(playerBoard, opponentBoard);
    }
}
