package ir.sharif.math.ap99_2.sea_battle.server.controller.game;

public enum Side {
    PLAYER_ONE{
        @Override
        Side getOther() {
            return PLAYER_TWO;
        }

    },PLAYER_TWO{
        @Override
        Side getOther() {
            return PLAYER_ONE;
        }
    };

    abstract Side getOther();

}
