package ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters;

public class WrongApiException extends Exception {

    public WrongApiException(ClassCastException cause) {
        super(cause);
    }

}
