package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public class GameDetailEvent extends Event {
    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.gameDetail();
    }
}
