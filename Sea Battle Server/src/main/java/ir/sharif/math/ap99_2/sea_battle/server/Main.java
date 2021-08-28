package ir.sharif.math.ap99_2.sea_battle.server;

import ir.sharif.math.ap99_2.sea_battle.server.controller.SocketManager;

public class Main {
    public static void main(String[] args) {
        SocketManager socketManager = new SocketManager();
        socketManager.start();
    }
}
