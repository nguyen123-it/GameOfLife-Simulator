package life.Model;


import java.util.Random;

// class that handles the logic of the game
public class GameLogic {
    // instance variables
    private int size;
    private int generations;

    private String [][] array;
    private String [][] evolvedUniverse;

    public GameLogic() {}

    // set size of gameBoard
    public void setInput(int size) {
        this.size = size;
        this.generations = 1;
        array = new String[size][size];
        evolvedUniverse = new String[size][size];
    }

    // init game board
    public void initGameBoard(){
        evolvedUniverse = initializeUniverse();

        initState();
    }

    /*play the game. This method calls other methods
    that are responsible for updating the board and calculating
    alive cells and generations
    */
    public void play() {
        System.out.println("Generation #" + generations);
        generateCell(array, evolvedUniverse);
        copy2DArr(array, evolvedUniverse);
        System.out.println("alive: " + countAliveCell(array));
        generations += 1;
    }

    // copy the updated game board over to the main game board
    public static void copy2DArr(String [][] array, String [][] evolvedUniverse){
        for (int i = 0; i<evolvedUniverse.length;i++){
            for (int j = 0; j<evolvedUniverse.length;j++){
                array[i][j] = evolvedUniverse[i][j];
            }
        }
    }

    // count alive cells
    public int countAliveCell(String [][] array){
        int counter = 0;
        for (int i = 0; i<array.length;i++){
            for (int j = 0; j<array.length;j++){
                if (array[i][j].equals("0")){
                    counter+=1;
                }
            }
        }
        return counter;
    }

    // getter to get generations as integer
    public int getGenerations(){
        return generations;
    }

    /*initialize the temp array that updates the state
    of the universe
     */
    public String [][] initializeUniverse() {
        String [] [] emptyArr = new String[size][size];
        for (int i = 0; i<emptyArr.length;i++){
            for (int j = 0; j<emptyArr.length;j++){
                emptyArr[i][j] = " ";
            }
        }
        return emptyArr;
    }

    // display the universe
    public void printUniverse(String[][] evolvedUniverse) {
        for (int i = 0; i<evolvedUniverse.length;i++) {
            for (int j = 0; j<evolvedUniverse.length;j++) {
                System.out.print(evolvedUniverse[i][j]);
            }
            System.out.println();
        }
    }

    // display the first gen of the universe
    public void initState() {
        Random random = new Random();
        boolean randomBool = random.nextBoolean();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (randomBool) {
                    array[i][j] = "0";
                }
                else {
                    array[i][j] = " ";
                }
                randomBool = random.nextBoolean();
            }
        }
    }

    // algorithm for generating cell
    public void generateCell(String[][] array, String[][] evolveUniverse) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                int liveCell = countLiveCell(array, i, j);
                if (array[i][j].equals(" ")){
                    if (liveCell == 3){
                        evolveUniverse[i][j] = "0";
                    }
                }
                else if (array[i][j].trim().equals("0")){
                    if (liveCell<2){
                        evolveUniverse[i][j] = " ";
                    }
                    else if (liveCell>=2 && liveCell<=3){
                        evolveUniverse[i][j] = "0";
                    }
                    else if (liveCell>3){
                        evolveUniverse[i][j] = " ";
                    }
                }
            }
        }
    }

    // count how many neighbors of a cell are alive
    public int countLiveCell(String[][] array, int row, int col) {
        int countRow = 0;
        int countCol = 0;
        int countDiagonal = 0;

        int[] positions = new int[2];

        positions = horizontalNeighbors(array, col);
        for (int i = 0; i < positions.length; i++) {
            if (array[row][positions[i]].equals("0")) {
                countCol+=1;
            }
        }

        positions = verticalNeighbors(array, row);
        for (int i = 0; i < positions.length; i++) {
            if (array[positions[i]][col].equals("0")) {
                countRow+=1;
            }
        }


        positions = upperDiagonalNeighbors(array, row, col);
        if (array[positions[0]][positions[1]].equals("0")){
            countDiagonal+=1;
        }
        if (array[positions[0]][positions[2]].equals("0")){
            countDiagonal+=1;
        }

        positions = lowerDiagonalNeighbors(array, row, col);
        if (array[positions[0]][positions[1]].equals("0")){
            countDiagonal+=1;
        }
        if (array[positions[0]][positions[2]].equals("0")){
            countDiagonal+=1;
        }

        return countCol+countRow+countDiagonal;

    }

    /* count the number of live cells that are positioned
    on the right side and the left side of the current cell
     */
    public int[] horizontalNeighbors(String[][] array, int col) {
        int largerCol = col + 1;
        int smallerCol = col - 1;
        if (col + 1 > array.length - 1) {
            largerCol = array.length - (col + 1);
        }
        if (col - 1 < 0) {
            smallerCol = array.length + (col - 1);
        }

        int[] horizontalPos = new int[2];
        horizontalPos[0] = largerCol;
        horizontalPos[1] = smallerCol;

        return horizontalPos;
    }

    /*
    count the number of live cells that are positioned above
    and below the current cell
     */
    public int[] verticalNeighbors(String[][] array, int row) {
        int largerRow = row + 1;
        int smallerRow = row - 1;
        if (row + 1 > array.length - 1) {
            largerRow = array.length - (row + 1);
        }
        if (row - 1 < 0) {
            smallerRow = array.length + (row - 1);
        }

        int[] verticalPos = new int[2];
        verticalPos[0] = largerRow;
        verticalPos[1] = smallerRow;

        return verticalPos;
    }

    /* count the number of live cells that are diagonally
    above the current cell
     */
    public int[] upperDiagonalNeighbors(String[][] array, int row, int col) {
        int upperRow = row-1;
        int rightCol = col+1;
        int leftCol = col-1;
        if (upperRow<0){
            upperRow = array.length + (upperRow);
        }
        if (rightCol>array.length-1){
            rightCol = 0;
        }
        if (leftCol<0){
            leftCol = array.length +(leftCol);
        }

        int [] upperDiagonalPos = {upperRow,rightCol,leftCol};
        return upperDiagonalPos;
    }

    /* count the number of live celss that are diagonally
    below the current cell
    */
    public int [] lowerDiagonalNeighbors(String [][] array, int row, int col){
        int lowerRow = row+1;
        int rightCol = col+1;
        int leftCol = col-1;
        if (lowerRow>array.length-1){
            lowerRow = 0;
        }
        if (rightCol>array.length-1){
            rightCol = 0;
        }
        if (leftCol<0){
            leftCol = array.length +(leftCol);
        }

        int [] lowerDiagonalPos = {lowerRow,rightCol,leftCol};
        return lowerDiagonalPos;
    }

    // getter to get the main game board.
    public String [][] getBoard(){
        return array;
    }

}
