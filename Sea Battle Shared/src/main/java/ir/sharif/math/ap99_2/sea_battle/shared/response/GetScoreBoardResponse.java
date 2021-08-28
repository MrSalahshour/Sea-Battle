package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class GetScoreBoardResponse extends Response {
    private final String scoreBoard;

    public GetScoreBoardResponse(String scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public String getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.getScoreBoard(scoreBoard);
    }
}
