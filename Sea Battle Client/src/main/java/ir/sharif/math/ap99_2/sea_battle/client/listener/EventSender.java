package ir.sharif.math.ap99_2.sea_battle.client.listener;

import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public interface EventSender {
    Response send(Event event);

    void close();
}
