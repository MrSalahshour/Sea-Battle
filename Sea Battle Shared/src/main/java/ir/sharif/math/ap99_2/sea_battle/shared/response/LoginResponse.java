package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class LoginResponse extends Response {
    private final boolean success;
    private final String message;
    private String token;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.login(success,message);
    }
}
