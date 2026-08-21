package src.model;

/// The underlying console implementation of tictactoe underpinning the GUI
public class Game {

    private char grid[][] = {{ '1', '2', '3' },
                              { '4', '5', '6' },
                              { '7', '8', '9' }};

    char player1Marker = 'X';
    char player2Marker = 'O';
    char currentMarker = player1Marker;

    public  int turn         = 1;
    private int player1Score = 0;
    private int player2Score = 0;
    
    private boolean win  = false;
    private boolean draw = false;
    private boolean over = false;
    
    /**
     * A function to get game's current score tally of both players
     * 
     * @return String in format "Score 1-1"
     */
    public String getScore() {
        return "Score: " + String.valueOf(player1Score) + "-" + String.valueOf(player2Score);
    }

    public char[][] getGrid() { return grid; }
    public int      getTurn() { return turn; }

    public boolean isWon()   { return win;  }
    public boolean isDrawn() { return draw; }
    public boolean isOver()  { return over; }

    /**
     * Executes a full move cycle: input handling, processing and win checking.
     *
     * @param choice player input (1–9 grid position or 0 for reset)
     */
    public void play(int choice) {
        if (!win || choice == 0) {
            handleInput(choice);
        }
    }

    /**
     * Checks all possible win conditions (rows, columns, diagonals)
     * for the current player marker and updates win state if found.
     */
    private void checkWin() {

        for (int i = 0; i < grid.length; i++) {

            // Check Horizontally
            if (grid[i][0] == currentMarker)
                if (grid[i][1] == currentMarker)
                    if (grid[i][2] == currentMarker)
                        win = true;

            // Check Vertically
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

        if (win) {
            if (turn == 1) player1Score++;
            if (turn == 2) player2Score++;
        }
    }

    /**
     * Returns if any cell remains empty.
     * Sets {@link #draw} as true if all cells filled and game has not been won.
     */
    private void checkDraw() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell != player1Marker && cell != player2Marker) {
                    return; // Return if any empty cell remains
                }
            }
        }

        draw = !win; // if no empty cells then !win = Draw state
    }

    private void checkOver() {
        checkWin();
        checkDraw();
        
        if(win||draw) over = true;
    }

    /**
     * Resets the game state including grid, turn, and current marker.
     */
    private void resetGame() {
        currentMarker = 'X';

        turn    = 1;

        win  = false;
        draw = false;
        over = false;

        grid = new char[][] {{ '1', '2', '3' },
                             { '4', '5', '6' },
                             { '7', '8', '9' }};
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

            case 0: resetGame(); return;
        }

        // Mark Spot on the grid with current character
        mark(row, col);
    }

    /**
     * Marks a cell on the grid with the current player's symbol if valid.
     *
     * @param row row index of the cell
     * @param col column index of the cell
     */
    private void mark(int row, int col) {
        if (grid[row][col] != player1Marker && grid[row][col] != player2Marker) {
            grid[row][col] = currentMarker;
            checkOver();
            if (!win) {
                toggleTurn();
            }
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