package ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters;

import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public interface ResponseSender {
    Event getEvent();

    void sendResponse(Response response);

    void close();
}
