package life.View;

import life.Model.GameLogic;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Random;

public class GameOfLife implements Runnable{ // UI class of the application
    //create instance variables
    private JFrame jFrame;

    private JPanel controllerPanel;

    private JLabel generationLabel;
    private JLabel aliveLabel;

    private JToggleButton pauseResumeBtn;
    private JButton resetBtn;

    private GameLogic gameLogic;
    private GameField gameField;

    private JSlider slider;

    private JComboBox comboBox;

    private Color [] colorArray;

    private volatile boolean running = false;

    public void initWindow(){// initialize the window. The main window consists the game panel
        jFrame = new JFrame();// instantiate frame of class JFrame
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800, 800);// set size of frame to 800,800
        displayComponents();// add other components
        jFrame.setVisible(true);// make frame visible
    }

    public void initGameField() {// create the labels
        generationLabel = new JLabel("Generation:#" + 1);
        aliveLabel = new JLabel("Alive: " + gameLogic.countAliveCell(gameLogic.getBoard()));
        generationLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        Box box = Box.createVerticalBox();
        box.add(generationLabel);
        box.add(aliveLabel);
        jFrame.add(box, BorderLayout.NORTH);
    }

    public void initGameLogic() {// instantiate gameLogic and GameField
        // gameBoard will have random size
        Random random = new Random();
        int size = random.nextInt(100) + 1;
        gameLogic = new GameLogic();
        gameLogic.setInput(size);
        gameLogic.initGameBoard();
        gameField = new GameField(gameLogic);
        // put gameBoard at the center of the window
        jFrame.add(gameField, BorderLayout.CENTER);
    }

    // create buttons that are used to control the game
    public void createButtons() {
        pauseResumeBtn = new JToggleButton("PlayToggleButton");// joggle button allows user to stop/resume game
        pauseResumeBtn.setName("PlayToggleButton");
        resetBtn = new JButton("ResetButton");// reset button allows user to restart the game with the new game board
        resetBtn.setName("ResetButton");
        // buttons will be added to a panel in a vertical order
        controllerPanel.add(pauseResumeBtn);
        controllerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        controllerPanel.add(resetBtn);
        controllerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

    }

    public void createSlider() {// create slider that controls the game speed
        // instantiate slider object of class JSlider with max, min and initial value
        slider = new JSlider(0, 2000, 1000);
        // maximum tick spacing is 500. minimum tick spacing is 100
        slider.setMajorTickSpacing(500);
        slider.setMinorTickSpacing(100);
        // draw the slider
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        // add slider to controllerPanel
        controllerPanel.add(slider);
    }

    public void createColorArray(){// create an array that contains different types of color
        // array size is 5
        colorArray = new Color[5];
        // add elements to array. Elements are of type Color
        colorArray[0] = Color.ORANGE;
        colorArray[1] = Color.RED;
        colorArray[2] = Color.BLUE;
        colorArray[3] = Color.CYAN;
        colorArray[4] = Color.MAGENTA;
    }

    public void createColorBox(){// create a combo box that has different options for color
        // combo box has colorArray as argument
        comboBox = new JComboBox(colorArray);
        // add combo box to controllerPanel
        controllerPanel.add(Box.createRigidArea(new Dimension(0,50)));
        controllerPanel.add(comboBox);
        controllerPanel.add(Box.createRigidArea(new Dimension(0,800)));

    }

    public void setColor(){// set color of rectangulars on the game panel
        gameField.updateColor((Color) comboBox.getSelectedItem());
    }

    // add listener classes to component to make them responsive.
    public void initController(ActionListener toggleController, ActionListener buttonController, ChangeListener sliderController,
            ItemListener itemListener){
        pauseResumeBtn.addActionListener(toggleController);
        resetBtn.addActionListener(buttonController);
        slider.addChangeListener(sliderController);
        comboBox.addItemListener(itemListener);
    }

    // call other methods that are responsible for creating buttons, slider and game panel
    public void displayComponents(){
        controllerPanel = new JPanel();
        //controllerPanel.setPreferredSize(new Dimension(200,100));
        controllerPanel.setLayout(new BoxLayout(controllerPanel,BoxLayout.Y_AXIS));
        initGameLogic();
        initGameField();
        createButtons();
        createSlider();
        createColorArray();
        createColorBox();
        // add controllerPanel to the main window.
        jFrame.add(controllerPanel,BorderLayout.EAST);
    }

    // remove all components from the main window and re-add them
    public void reset(ActionListener toggleControl, ActionListener buttonController, ChangeListener sliderController,ItemListener itemListener) {
        jFrame.getContentPane().removeAll();// remove components
        displayComponents();
        initController(toggleControl,buttonController,sliderController,itemListener);
        jFrame.validate();// revalidate components. Make them valid and they will reappear
    }

    // check if the volatile variable running is true or false
    public boolean isRunning(){
        if (running){
            return true;
        }
        return false;
    }

    // set volatile boolean running
    public void setBoolean(boolean val){
        running = val;
    }

    // start the game thread
    public void beginThread(){
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    // game thread. All actions take place inside this thread
    @Override
    public void run() {
        while (running) {
            gameField.startSimulate();
            generationLabel.setText("Generation #" + gameLogic.getGenerations());
            aliveLabel.setText("Alive: " + gameLogic.countAliveCell(gameLogic.getBoard()));
            gameField.updateColor((Color)comboBox.getSelectedItem());
            try {
                Thread.sleep(slider.getValue());

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

