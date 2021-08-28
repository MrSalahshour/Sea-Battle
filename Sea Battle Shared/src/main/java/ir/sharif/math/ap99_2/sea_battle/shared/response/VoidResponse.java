package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class VoidResponse extends Response {
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.voidAction();
    }
}
