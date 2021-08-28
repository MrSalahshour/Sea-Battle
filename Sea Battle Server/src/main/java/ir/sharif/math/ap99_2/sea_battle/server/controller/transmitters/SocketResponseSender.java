package ir.sharif.math.ap99_2.sea_battle.server.controller.transmitters;

import com.google.gson.*;
import ir.sharif.math.ap99_2.sea_battle.shared.events.Event;
import ir.sharif.math.ap99_2.sea_battle.shared.events.LogOutEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.events.LoginEvent;
import ir.sharif.math.ap99_2.sea_battle.shared.gson.Deserializer;
import ir.sharif.math.ap99_2.sea_battle.shared.gson.Serializer;
import ir.sharif.math.ap99_2.sea_battle.shared.response.LoginResponse;
import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.*;

public class SocketResponseSender implements ResponseSender{
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final Scanner scanner;
    private final PrintStream printStream;
    private String token = null;
    private final Gson gson;
    private final Socket socket;

    public SocketResponseSender(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(socket.getInputStream());
        printStream = new PrintStream(socket.getOutputStream(),true);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new Deserializer<>())
                .registerTypeAdapter(Response.class, new Serializer<>())
                .create();
    }


    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public Event getEvent() {
        String eventString ="";
        try {
            eventString = scanner.nextLine();
        }
        catch (NoSuchElementException e){
            return new LogOutEvent();
        }
        Event event = gson.fromJson(eventString, Event.class);
        if (event instanceof LoginEvent){
        }
        else {
            if (event.getToken()==null || !event.getToken().equals(token)){
                sendResponse(Response.getWrongApi());
            }
        }
        return event;
    }

    @Override
    public void sendResponse(Response response) {
        if (response instanceof LoginResponse && ((LoginResponse) response).isSuccess()){
            token = generateNewToken();
            ((LoginResponse) response).setToken(token);
        }
        printStream.println(gson.toJson(response, Response.class));
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
