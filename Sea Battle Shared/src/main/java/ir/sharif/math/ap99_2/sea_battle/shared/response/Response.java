package ir.sharif.math.ap99_2.sea_battle.shared.response;


public abstract class Response {
    public abstract void visit(ResponseVisitor responseVisitor);


    protected String nonNull(String string) {
        if (string == null) string = "";
        return string;
    }

    public static WrongAPI getWrongApi() {
        return WrongAPI.instance;
    }


    private static class WrongAPI extends Response {
        private final static WrongAPI instance = new WrongAPI();
        private final String message;

        private WrongAPI() {
            message = "wrong API!!!";
        }
        @Override
        public void visit(ResponseVisitor responseVisitor) {
            responseVisitor.showMessage(message);
        }
    }

}
