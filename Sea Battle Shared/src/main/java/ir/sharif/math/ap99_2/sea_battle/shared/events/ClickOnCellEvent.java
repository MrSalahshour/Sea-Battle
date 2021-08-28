package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public class ClickOnCellEvent extends Event {
    private final int x,y;
    public ClickOnCellEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.clickOnCell(x,y);
    }
}
