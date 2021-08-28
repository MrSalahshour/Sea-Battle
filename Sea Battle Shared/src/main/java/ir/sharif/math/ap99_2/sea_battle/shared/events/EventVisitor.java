package ir.sharif.math.ap99_2.sea_battle.shared.events;

import ir.sharif.math.ap99_2.sea_battle.shared.response.Response;

public interface EventVisitor {
    Response getProfile();

    Response login(String username,String password,int mode);

    Response getScoreBoard();

    Response clickOnCell(int x , int y);

    Response getBoard();

    Response gameDetail();

    Response clickOnReady();

    Response clickOnReset();

    Response newGame();

    Response logout();

    Response getLiveGamesList();

    Response watchGame(String player1Name,String player2Name);






}
