package ir.sharif.math.ap99_2.sea_battle.shared.model;

import java.util.LinkedList;

public class Ship {
    private boolean alive;
    // if value is false it means that part of ship is hit by bomb.
    private LinkedList<Cell> locations;
    private LinkedList<Cell> adjacentCells;

    public Ship(LinkedList<Cell> locations, LinkedList<Cell> adjacentCells) {
        this.locations = locations;
        this.adjacentCells = adjacentCells;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public LinkedList<Cell> getLocations() {
        return locations;
    }

    public LinkedList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public void setLocations(LinkedList<Cell> locations) {
        this.locations = locations;
    }

    public void setAdjacentCells(LinkedList<Cell> adjacentCells) {
        this.adjacentCells = adjacentCells;
    }
    //    public void checkHealth(){
//        for (Map.Entry<Cell, Boolean> entry : locations.entrySet()) {
//            if (entry.getValue())
//                return;
//        }
//        setAlive(false);
//    }
//
//    public void hitPart(Cell cell){
//        for (Map.Entry<Cell, Boolean> entry : locations.entrySet()) {
//            if (entry.getKey().equals(cell)){
//                locations.replace(cell,false);
//            }
//        }
//    }
}
