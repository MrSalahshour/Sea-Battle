package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public class WatchGameEvent extends Event {
    private final String player1Name;
    private final String player2Name;

    public WatchGameEvent(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.watchGame(player1Name,player2Name);
    }
}
