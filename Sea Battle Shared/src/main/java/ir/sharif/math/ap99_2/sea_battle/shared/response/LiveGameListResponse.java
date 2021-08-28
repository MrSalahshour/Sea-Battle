package ir.sharif.math.ap99_2.sea_battle.shared.response;

import ir.sharif.math.ap99_2.sea_battle.shared.model.GameDetail;

import java.util.LinkedList;

public class LiveGameListResponse extends Response{
    private final LinkedList<GameDetail> gameDetailsList;

    public LiveGameListResponse(LinkedList<GameDetail> gameDetailsList) {
        this.gameDetailsList = gameDetailsList;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.setLiveGamesList(gameDetailsList);

    }
}
