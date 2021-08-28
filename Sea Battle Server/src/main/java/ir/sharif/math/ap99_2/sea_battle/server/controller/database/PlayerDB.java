package ir.sharif.math.ap99_2.sea_battle.server.controller.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ir.sharif.math.ap99_2.sea_battle.server.model.Player;
import ir.sharif.math.ap99_2.sea_battle.server.util.Config;
import ir.sharif.math.ap99_2.sea_battle.shared.util.Loop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PlayerDB {
    private final static Object staticLock = new Object();
    private final File playersDirectory;
    private final ObjectMapper objectMapper;
    private final Set<Player> save, tempSave;
    private final Loop worker;
    private final Object lock;

    public PlayerDB() {
        Config config = Config.getConfig("mainConfig");
        this.playersDirectory = config.getProperty(File.class,"playersDirectory");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        save = new HashSet<>();
        tempSave = new HashSet<>();
        this.worker = new Loop(10,this::persist);
        this.lock = new Object();
        worker.start();
    }

    private void persist() {
        synchronized (lock) {
            tempSave.addAll(save);
            this.save.clear();
        }
        if (tempSave.size() > 0)
            synchronized (staticLock) {
                for (Player player : tempSave) {
                    add(player);
                }
                tempSave.clear();
            }
    }


    public void savePlayer(Player player){
        synchronized (lock) {
            save.add(player);
        }
    }


    private void add(Player player) {
        try {
            File file = getUserFile(player.getUsername());
            if (file == null) {
                file = new File(playersDirectory.getPath() + "\\" +player.getUsername() + ".txt");
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, false);
            objectMapper.writeValue(fileWriter, player);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LinkedList<Player> all() {
        synchronized (staticLock){
            LinkedList<Player> allPlayers = new LinkedList<>();
            File[] filesList = playersDirectory.listFiles();
            if (filesList == null)
                return allPlayers;
            for (File file : filesList) {
                Player player = loadUser(file);
                allPlayers.add(player);
            }
            return allPlayers;
        }
    }

    public Player getByUserName(String userName){
        synchronized (staticLock){
            File[] filesList = playersDirectory.listFiles();
            if (filesList == null)
                return null;
            for (File file : filesList) {
                Player player = loadUser(file);
                if (player==null)
                    continue;

                if (player.getUsername().equals(userName)){
                    return player;
                }
            }
            return null;
        }
    }

    private File getUserFile(String username) {
        File file = new File(playersDirectory.getPath() + "\\" + username + ".txt");
        if (!file.exists()) {
            return null;
        } else {
            return file;
        }
    }

    private Player loadUser(File file){
        try {
            return objectMapper.readValue(file, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeDB(){
        worker.stop();
    }
}
