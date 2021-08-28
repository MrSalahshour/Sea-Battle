package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class BackToMainMenuResponse extends Response {
    private final String message;

    public BackToMainMenuResponse(String message) {
        this.message = message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.BackToMainMenu(message);
    }
}
