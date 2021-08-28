package ir.sharif.math.ap99_2.sea_battle.server.controller.game;

import ir.sharif.math.ap99_2.sea_battle.shared.model.Board;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Cell;
import ir.sharif.math.ap99_2.sea_battle.shared.model.Ship;

import java.util.LinkedList;
import java.util.Random;

public class BoardBuilder {
    private static final Random random = new Random();

    public static Board buildNewBoard() {
        LinkedList<Cell> cells = new LinkedList<>();
        LinkedList<Ship> ships = new LinkedList<>();

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                Cell cell = new Cell(i, j);
                cells.add(cell);
            }
        }

        addBattleShip(cells, ships);
        addCruisers(cells, ships);
        addDestroyers(cells, ships);
        addFrigates(cells, ships);
        return new Board(cells, ships);
    }

    private static Cell getCell(int x, int y, LinkedList<Cell> cells) {
        int index = (x - 1) * 10 + (y - 1);
        return cells.get(index);
    }

    private static void addBattleShip(LinkedList<Cell> cells, LinkedList<Ship> ships) {
        int randX = random.nextInt(3) + 2;
        int randY = random.nextInt(2) + 1;
        LinkedList<Cell> adjacentCell = new LinkedList<>();
        LinkedList<Cell> locations = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            locations.add(getCell(randX, randY + i, cells));
            if (i == 0 && randY != 1) {
                adjacentCell.add(getCell(randX, randY - 1, cells));
                adjacentCell.add(getCell(randX + 1, randY - 1, cells));
                adjacentCell.add(getCell(randX - 1, randY - 1, cells));
            }
            if (i == 3) {
                adjacentCell.add(getCell(randX, randY + 4, cells));
                adjacentCell.add(getCell(randX - 1, randY + 4, cells));
                adjacentCell.add(getCell(randX + 1, randY + 4, cells));

            }
            adjacentCell.add(getCell(randX + 1, randY + i, cells));
            adjacentCell.add(getCell(randX - 1, randY + i, cells));
        }
        Ship ship = new Ship(locations, adjacentCell);
        ships.add(ship);
        for (int i = 0; i < 4; i++) {
            getCell(randX, randY + i, cells).setHasShip(true);
        }
    }

    private static void addCruisers(LinkedList<Cell> cells, LinkedList<Ship> ships) {
        int randX = 6;
        int randY = random.nextInt(2) + 1;
        LinkedList<Cell> adjacentCell1 = new LinkedList<>();
        LinkedList<Cell> locations1 = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            locations1.add(getCell(randX, randY + i, cells));
            if (i == 0 && randY != 1){
                adjacentCell1.add(getCell(randX, randY - 1, cells));
                adjacentCell1.add(getCell(randX - 1, randY - 1, cells));
                adjacentCell1.add(getCell(randX + 1, randY - 1, cells));
            }
            if (i == 2){
                adjacentCell1.add(getCell(randX, randY + 3, cells));
                adjacentCell1.add(getCell(randX + 1, randY + 3, cells));
                adjacentCell1.add(getCell(randX - 1, randY + 3, cells));
            }
            adjacentCell1.add(getCell(randX + 1, randY + i, cells));
            adjacentCell1.add(getCell(randX - 1, randY + i, cells));
        }
        Ship ship1 = new Ship(locations1, adjacentCell1);
        ships.add(ship1);
        for (int i = 0; i < 3; i++) {
            getCell(randX, randY + i, cells).setHasShip(true);
        }

        randX = 8;
        randY = random.nextInt(4) + 1;
        LinkedList<Cell> adjacentCell2 = new LinkedList<>();
        LinkedList<Cell> locations2 = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            locations2.add(getCell(randX + i, randY, cells));
            if (randY != 1)
                adjacentCell2.add(getCell(randX + i, randY - 1, cells));
            if (i == 0){
                adjacentCell2.add(getCell(randX - 1, randY, cells));
                adjacentCell2.add(getCell(randX - 1, randY + 1, cells));
                if (randY>1)
                    adjacentCell2.add(getCell(randX - 1, randY - 1, cells));
            }
            adjacentCell2.add(getCell(randX + i, randY + 1, cells));
        }
        Ship ship2 = new Ship(locations2, adjacentCell2);
        ships.add(ship2);
        for (int i = 0; i < 3; i++) {
            getCell(randX + i, randY, cells).setHasShip(true);
        }
    }

    private static void addDestroyers(LinkedList<Cell> cells, LinkedList<Ship> ships) {
        int randX = 6;
        int randY = random.nextInt(2) + 6;
        LinkedList<Cell> adjacentCell1 = new LinkedList<>();
        LinkedList<Cell> locations1 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            locations1.add(getCell(randX + i, randY, cells));
            adjacentCell1.add(getCell(randX + i, randY - 1, cells));
            if (i == 0){
                adjacentCell1.add(getCell(randX - 1, randY, cells));
                adjacentCell1.add(getCell(randX - 1, randY + 1, cells));
                adjacentCell1.add(getCell(randX - 1, randY - 1, cells));

            }
            else{
                adjacentCell1.add(getCell(randX + 2, randY, cells));
                adjacentCell1.add(getCell(randX + 2, randY + 1, cells));
                adjacentCell1.add(getCell(randX + 2, randY - 1, cells));

            }
            adjacentCell1.add(getCell(randX + i, randY + 1, cells));
            adjacentCell1.add(getCell(randX + i, randY - 1, cells));
        }
        Ship ship1 = new Ship(locations1, adjacentCell1);
        ships.add(ship1);
        for (int i = 0; i < 2; i++) {
            getCell(randX + i, randY, cells).setHasShip(true);
        }

        randY = 9;
        randX = random.nextInt(2) + 6;
        LinkedList<Cell> adjacentCell2 = new LinkedList<>();
        LinkedList<Cell> locations2 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            locations2.add(getCell(randX, randY + i, cells));
            if (i == 0){
                adjacentCell2.add(getCell(randX, randY - 1, cells));
                adjacentCell2.add(getCell(randX + 1, randY - 1, cells));
                adjacentCell2.add(getCell(randX - 1, randY - 1, cells));

            }
            adjacentCell2.add(getCell(randX - 1, randY + i, cells));
            adjacentCell2.add(getCell(randX + 1, randY + i, cells));
        }
        Ship ship2 = new Ship(locations2, adjacentCell2);
        ships.add(ship2);
        for (int i = 0; i < 2; i++) {
            getCell(randX, randY + i, cells).setHasShip(true);
        }

        randY = random.nextInt(4) + 6;
        randX = random.nextInt(2) + 9;
        LinkedList<Cell> adjacentCell3 = new LinkedList<>();
        LinkedList<Cell> locations3 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            locations3.add(getCell(randX, randY + i, cells));
            if (i == 0){
                adjacentCell3.add(getCell(randX, randY - 1, cells));
                adjacentCell3.add(getCell(randX - 1, randY - 1, cells));
                if (randX<10)
                    adjacentCell3.add(getCell(randX + 1, randY - 1, cells));
            }
            adjacentCell3.add(getCell(randX - 1, randY + i, cells));
            if (randX != 10)
                adjacentCell3.add(getCell(randX + 1, randY + i, cells));
            if (i == 1 && randY + i < 10){
                adjacentCell3.add(getCell(randX, randY + 2, cells));
                if (randX<10)
                    adjacentCell3.add(getCell(randX + 1, randY + 2, cells));
                adjacentCell3.add(getCell(randX - 1, randY + 2, cells));
            }

        }
        Ship ship3 = new Ship(locations3, adjacentCell3);
        ships.add(ship3);
        for (int i = 0; i < 2; i++) {
            getCell(randX, randY + i, cells).setHasShip(true);
        }
    }

    private static void addFrigates(LinkedList<Cell> cells, LinkedList<Ship> ships) {

        for (int k = 0; k < 4; k++) {
            int rX = 10;
            int rY = 10;
            First:
            for (int j = 1; j < 11; j++) {
                for (int i = 1; i < 11; i++) {
                    if (getCell(i, j, cells).isHasShip())
                        continue;
                    if (i > 1) {
                        if (getCell(i - 1, j, cells).isHasShip())
                            continue;
                    }
                    if (i < 10) {
                        if (getCell(i + 1, j, cells).isHasShip())
                            continue;
                    }
                    if (j > 1) {
                        if (getCell(i, j - 1, cells).isHasShip())
                            continue;
                    }
                    if (j < 10) {
                        if (getCell(i, j + 1, cells).isHasShip())
                            continue;
                    }
                    if (i > 1 && j > 1) {
                        if (getCell(i - 1, j - 1, cells).isHasShip())
                            continue;
                    }
                    if (i > 1 && j < 10) {
                        if (getCell(i - 1, j + 1, cells).isHasShip())
                            continue;
                    }
                    if (i < 10 && j > 1) {
                        if (getCell(i + 1, j - 1, cells).isHasShip())
                            continue;
                    }
                    if (i < 10 && j < 10) {
                        if (getCell(i + 1, j + 1, cells).isHasShip())
                            continue;
                    }
                    rX = i;
                    rY = j;
                    break First;
                }
            }

            handleFrigates(rX, rY, cells, ships);
        }

    }

    private static void handleFrigates(int randX, int randY, LinkedList<Cell> cells, LinkedList<Ship> ships) {
        LinkedList<Cell> adjacentCell = new LinkedList<>();
        LinkedList<Cell> locations = new LinkedList<>();
        locations.add(getCell(randX, randY, cells));
        if (randX < 10)
            adjacentCell.add(getCell(randX + 1, randY, cells));
        if (randX > 1)
            adjacentCell.add(getCell(randX - 1, randY, cells));
        if (randY < 10)
            adjacentCell.add(getCell(randX, randY + 1, cells));
        if (randY > 1)
            adjacentCell.add(getCell(randX, randY - 1, cells));
        if (randX < 10 && randY<10)
            adjacentCell.add(getCell(randX + 1, randY + 1, cells));
        if (randX < 10 && randY > 1)
            adjacentCell.add(getCell(randX + 1, randY - 1, cells));
        if (randX > 1 && randY < 10)
            adjacentCell.add(getCell(randX - 1, randY + 1, cells));
        if (randX > 1 && randY > 1)
            adjacentCell.add(getCell(randX - 1, randY - 1, cells));

        Ship ship = new Ship(locations, adjacentCell);
        ships.add(ship);
        getCell(randX, randY, cells).setHasShip(true);
    }

}
