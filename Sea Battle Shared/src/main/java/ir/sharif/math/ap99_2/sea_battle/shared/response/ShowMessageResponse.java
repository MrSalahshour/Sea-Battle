package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class ShowMessageResponse extends Response {
    private final String message;

    public ShowMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.showMessage(message);
    }
}
