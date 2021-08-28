package ir.sharif.math.ap99_2.sea_battle.client;

import ir.sharif.math.ap99_2.sea_battle.client.controller.MainController;
import ir.sharif.math.ap99_2.sea_battle.client.listener.network.SocketEventSender;
import ir.sharif.math.ap99_2.sea_battle.client.util.Config;

import java.io.IOException;
import java.net.Socket;

public class Main {
    static Integer port;
    static String host;
    public static void main(String[] args) {
        try {
            Config config = Config.getConfig("serverDetail");
            port = config.getProperty(Integer.class,"port");
            if (port == null)
                port = 8000;
            host = config.getProperty(String.class,"host");
            if (host == null)
                host = "";
            Socket socket = new Socket(host, port);
            MainController controller = new MainController(new SocketEventSender(socket));
            controller.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
