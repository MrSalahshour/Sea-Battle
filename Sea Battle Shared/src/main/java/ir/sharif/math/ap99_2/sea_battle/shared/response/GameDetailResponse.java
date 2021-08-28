package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class GameDetailResponse extends Response{
    private final String playerTimer;

    public GameDetailResponse(String playerTimer ) {
        this.playerTimer = playerTimer;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.setGameDetail(playerTimer);
    }
}
