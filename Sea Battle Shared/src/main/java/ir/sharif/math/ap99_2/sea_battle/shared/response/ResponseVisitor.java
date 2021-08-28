package ir.sharif.math.ap99_2.sea_battle.shared.response;

import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.GameDetail;

import java.util.LinkedList;

public interface ResponseVisitor {

    void getProfile(String username,String score,String wins,String loses);

    void login(boolean success,String message);

    void getScoreBoard(String scoreBoard);

    void showMessage(String message);

    void visitBoards(Board playerBoard, Board opponentBoard);

    void setGameDetail(String playerTimer);

    void BackToMainMenu(String message);

    void setLiveGamesList(LinkedList<GameDetail> gameDetailsList);

    void voidAction();

    void watchGame(String player1Name,String player2Name,Board player1,Board player2);
}
