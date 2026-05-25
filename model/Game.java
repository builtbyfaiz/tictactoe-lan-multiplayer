package model;
public class Game {

    private char grid[][] = new char[3][3];

    char voidMarker = '-';
    char player1Marker = 'X';
    char player2Marker = 'O';
    char currentMarker = player1Marker;

    public int turn = 1;
    public boolean win = false;

    /**
     * Executes a full move cycle: input handling, rendering, and win checking.
     *
     * @param choice player input (1–9 grid position or 0 for reset)
     */
    public void play(int choice) {
        handleInput(choice);
        render();
        checkWin();
    }

    public char[][] getGrid() {
        return grid;
    }

    /**
     * Initializes the game grid and prints the initial board state.
     */
    public Game() {
        initGrid();
        printGrid();
    }

    /**
     * Initializes all cells in the grid with the void marker.
     */
    private void initGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = voidMarker;
            }
        }
    }

    /**
     * Prints the current state of the grid to the console.
     */
    private void printGrid() {
        System.out.println();
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks all possible win conditions (rows, columns, diagonals)
     * for the current player marker and updates win state if found.
     */
    private void checkWin() {

        for (int i = 0; i < grid.length; i++) {

            // Check Vertically
            if (grid[i][0] == currentMarker)
                if (grid[i][1] == currentMarker)
                    if (grid[i][2] == currentMarker)
                        win = true;

            // Check Horizontally
            if (grid[0][i] == currentMarker)
                if (grid[1][i] == currentMarker)
                    if (grid[2][i] == currentMarker)
                        win = true;
        }

        // Check Primary Diagonal -> \
        if (grid[0][0] == currentMarker)
            if (grid[1][1] == currentMarker)
                if (grid[2][2] == currentMarker)
                    win = true;

        // Check Secondary Diagonal -> /
        if (grid[0][2] == currentMarker)
            if (grid[1][1] == currentMarker)
                if (grid[2][0] == currentMarker)
                    win = true;

        if (win)
            System.out.println("Player" + turn + " Won, Press 0 to Reset Game");
    }

    /**
     * Resets the game state including grid, turn, and current marker.
     */
    private void resetGame() {
        currentMarker = 'X';
        turn = 1;
        win = false;

        initGrid();
    }

    /**
     * Handles user input by mapping a numeric choice (1–9) to grid coordinates
     * and triggering the corresponding game action.
     *
     * @param choice the input position selected by the player
     */
    private void handleInput(int choice) {
        // Parse Input
        int row = 0;
        int col = 0;

        switch (choice) {
            case 1: row = 0; col = 0; break;
            case 2: row = 0; col = 1; break;
            case 3: row = 0; col = 2; break;
            case 4: row = 1; col = 0; break;
            case 5: row = 1; col = 1; break;
            case 6: row = 1; col = 2; break; 
            case 7: row = 2; col = 0; break; 
            case 8: row = 2; col = 1; break; 
            case 9: row = 2; col = 2; break; 
            case 0: resetGame();

            default:
                System.out.print("Invalid Input, Enter (1-9): ");
                break;
        }

        // Take Actions
        mark(row, col);
    };

    /**
     * Renders the current board state.
     */
    private void render() {
        printGrid();
    };

    /**
     * Marks a cell on the grid with the current player's symbol if valid.
     *
     * @param row row index of the cell
     * @param col column index of the cell
     */
    private void mark(int row, int col) {
        if (grid[row][col] == voidMarker) {
            grid[row][col] = currentMarker;
            toggleTurn();
        } else {
            System.out.println("Invalid Input, Already Marked");
        }
    }

    /**
     * Toggles the current turn and switches player markers.
     */
    private void toggleTurn() {
        turn = turn == 1 ? 2 : 1; // Toggles turn from 1 and 2 each time
        currentMarker = currentMarker == 'X' ? 'O' : 'X'; // Toggles the character
    }
}