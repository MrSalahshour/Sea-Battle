package ir.sharif.math.ap99_2.sea_battle.server.model;


import com.google.gson.annotations.Expose;

public class Player implements Comparable<Player> {
    @Expose
    private String username;
    private String password;
    @Expose
    private int score;
    @Expose
    private int wins;
    @Expose
    private int loses;
    @Expose
    private boolean online;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.wins = 0;
        this.loses = 0;
        this.online = true;
    }

    public Player() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public boolean isOnline() {
        return online;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void applyOnScore(int score){
        this.setScore(this.getScore()+score);
    }

    public void increaseWins(){
        this.setWins(this.getWins()+1);
        applyOnScore(1);
    }

    public void increaseLoses(){
        this.setLoses(this.getLoses()+1);
        applyOnScore(-1);
    }



    @Override
    public int compareTo(Player player) {
        int compareScore = player.getScore();

        if (compareScore == this.getScore())
            return player.getWins()-this.getWins();
        else
            return compareScore - this.getScore();
    }
}
