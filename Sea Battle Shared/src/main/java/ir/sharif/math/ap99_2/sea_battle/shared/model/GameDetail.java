package ir.sharif.math.ap99_2.sea_battle.shared.model;

public class GameDetail {
    private final String player1Name,player2Name;
    private final int player1Moves,player2Moves;
    private final int player1HitShips,player2HitShips;
    private final int player1SuccessfulBombs,player2SuccessfulBombs;

    public GameDetail(String player1Name, String player2Name, int player1Moves,
                      int player2Moves, int player1HitShips, int player2HitShips,
                      int player1SuccessfulBombs, int player2SuccessfulBombs) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Moves = player1Moves;
        this.player2Moves = player2Moves;
        this.player1HitShips = player1HitShips;
        this.player2HitShips = player2HitShips;
        this.player1SuccessfulBombs = player1SuccessfulBombs;
        this.player2SuccessfulBombs = player2SuccessfulBombs;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getPlayer1Moves() {
        return player1Moves;
    }

    public int getPlayer2Moves() {
        return player2Moves;
    }

    public int getPlayer1HitShips() {
        return player1HitShips;
    }

    public int getPlayer2HitShips() {
        return player2HitShips;
    }

    public int getPlayer1SuccessfulBombs() {
        return player1SuccessfulBombs;
    }

    public int getPlayer2SuccessfulBombs() {
        return player2SuccessfulBombs;
    }
}
