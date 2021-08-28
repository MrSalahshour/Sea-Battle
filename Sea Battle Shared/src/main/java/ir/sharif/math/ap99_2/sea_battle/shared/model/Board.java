package ir.sharif.math.ap99_2.sea_battle.shared.model;


import java.util.LinkedList;

public class Board {
    private LinkedList<Cell> cells;
    private LinkedList<Ship> ships;

    public Board(LinkedList<Cell> cells, LinkedList<Ship> ships) {
        this.cells = cells;
        this.ships = ships;
    }

    public LinkedList<Cell> getCells() {
        return cells;
    }

    public LinkedList<Ship> getShips() {
        return ships;
    }

    public Cell getCell(int x, int y){
        int index = (x - 1) * 10 + (y - 1);
        return cells.get(index);
    }

    public void setCells(LinkedList<Cell> cells) {
        this.cells = cells;
    }

    public void setShips(LinkedList<Ship> ships) {
        this.ships = ships;
    }
}
