package life.View;

import life.Model.GameLogic;

import javax.swing.*;
import java.awt.*;

// class GameField draws the game board using 2-d shapes
public class GameField extends JPanel {

    // instance variables
    private GameLogic gameLogic;
    private Color color;

    public GameField(GameLogic gameLogic){
        this.gameLogic = gameLogic;// init GameLogic
        color = Color.GREEN; // default color is green
    }

    //update color of rectangles
    public void updateColor(Color color){
        this.color = color;
        repaint();
    }

    // start the simulation of algorithm
    public void startSimulate() {
        gameLogic.play();
        repaint();
    }

    // draw 2-d rectangles that represent the alive cells
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0,500,500);

        for (int i = 0; i<gameLogic.getBoard().length;i++){
            for (int j = 0; j<gameLogic.getBoard().length;j++){
                if (gameLogic.getBoard()[i][j].equals("0")){
                    graphics2D.setColor(color);
                    graphics2D.fillRect(j*(500/gameLogic.getBoard().length),i*(500/gameLogic.getBoard().length),500/gameLogic.getBoard().length,500/gameLogic.getBoard().length);
                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawRect(j*(500/gameLogic.getBoard().length),i*(500/gameLogic.getBoard().length),500/gameLogic.getBoard().length,500/gameLogic.getBoard().length);
                }
            }
        }
    }

}
