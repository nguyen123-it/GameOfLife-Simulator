package life.Application;

import life.Controller.GameController;
import life.View.GameOfLife;

public class Main{
    public static void main(String[] args) {// main method to run java app
        GameOfLife gameOfLife = new GameOfLife();// init GameOfLife
        GameController gameController = new GameController(gameOfLife);// GameOfLife will be composed with GameController
        gameController.buildView();// GameController displays the UI
    }
}