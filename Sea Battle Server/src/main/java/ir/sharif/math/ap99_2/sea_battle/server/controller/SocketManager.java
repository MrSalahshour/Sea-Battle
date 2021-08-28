package ir.sharif.math.ap99_2.sea_battle.server.controller;

import ir.sharif.math.ap99_2.sea_battle.server.controller.database.PlayerDB;
import ir.sharif.math.ap99_2.sea_battle.server.controller.game.GameLobby;
import ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters.SocketResponseSender;
import ir.sharif.math.ap99_2.sea_battle.server.util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager extends Thread{
    private ServerSocket serverSocket;
    private volatile boolean running;
    private PlayerDB playerDB;
    private GameLobby gameLobby;

    public SocketManager() {
        try {
            Integer port;
            Config config = Config.getConfig("serverDetail");
            port = config.getProperty(Integer.class,"port");
            if (port == null)
                port = 8000;
            serverSocket = new ServerSocket(port);
            gameLobby = new GameLobby();
            playerDB = new PlayerDB();
            running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(new SocketResponseSender(socket),playerDB,gameLobby);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
