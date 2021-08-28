package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;


public abstract class Event {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public abstract Response visit(EventVisitor eventVisitor);
}
