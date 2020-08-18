package life.Controller;

import life.View.GameOfLife;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*class GameController handles the interaction between
GameLogic and Game Field
 */
public class GameController {
    //instance variables
    private GameOfLife gameOfLife;

    // init GameOfLife
    public GameController(GameOfLife gameOfLife){
        this.gameOfLife = gameOfLife;
    }

    // build the UI for main window
    public void buildView(){
        /* EventQueue allows main window to update game
        panel from a different thread.
        */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameOfLife.initWindow();
                gameOfLife.initController(new ToggleAction(), new ButtonReplay(), new ChangeSlider(), new ColorPicker());
            }
        });
    }

    // make the button resetBtn responsive
    class ButtonReplay implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gameOfLife.reset(new ToggleAction(),this,new ChangeSlider(),new ColorPicker());
            gameOfLife.setBoolean(false);
        }
    }

    // make the button pauseResumeButton responsive
    class ToggleAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOfLife.isRunning()){
                gameOfLife.setBoolean(false);
            }
            else{
                gameOfLife.setBoolean(true);
                gameOfLife.beginThread();
            }
        }
    }

    // make the slider responsive
    class ChangeSlider implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {

        }
    }

    // make the combo box responsive
    class ColorPicker implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            gameOfLife.setColor();
        }
    }

}
