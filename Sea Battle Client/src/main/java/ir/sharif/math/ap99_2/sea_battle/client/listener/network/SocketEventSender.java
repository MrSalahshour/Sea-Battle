package ir.sharif.math.ap99_2.sea_battle.client.listener.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.math.ap99_2.sea_battle.client.listener.EventSender;
import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.gson.Deserializer;
import ir.sharif.math.ap99_2.sea_battle.shared.gson.Serializer;
import ir.sharif.math.ap99_2.sea_battle.shared.response.LoginResponse;
import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketEventSender implements EventSender {
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final Gson gson;
    private String token;

    public SocketEventSender(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Response.class, new Deserializer<>())
                .registerTypeAdapter(Event.class, new Serializer<>())
                .create();
    }

    @Override
    public Response send(Event event) {
        event.setToken(token);
        String eventString = gson.toJson(event, Event.class);
        printStream.println(eventString);
        String responseString = scanner.nextLine();
        Response response = gson.fromJson(responseString, Response.class);
        if (response instanceof LoginResponse && ((LoginResponse) response).isSuccess()){
            token = ((LoginResponse) response).getToken();
        }
        return response;
    }

    @Override
    public void close() {
        try {
            printStream.close();
            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
