package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public class LoginEvent extends Event {
    private final String username;
    private final String password;
    private final int mode;
    public LoginEvent(String username, String password, int mode) {
        this.username = username;
        this.password = password;
        this.mode = mode;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return eventVisitor.login(username,password,mode);
    }
}
