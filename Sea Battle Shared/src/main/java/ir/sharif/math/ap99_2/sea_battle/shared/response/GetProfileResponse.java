package ir.sharif.math.ap99_2.sea_battle.shared.response;

public class GetProfileResponse extends Response {
    private final String username;
    private final String score;
    private final String wins;
    private final String loses;

    public GetProfileResponse(String username, String score, String wins, String loses) {
        this.username = username;
        this.score = score;
        this.wins = wins;
        this.loses = loses;
    }

    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

    public String getWins() {
        return wins;
    }

    public String getLoses() {
        return loses;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.getProfile(username,score,wins,loses);
    }
}
