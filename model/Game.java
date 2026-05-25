package model;

public class Game {

    private char grid_[][] = {{ '1', '2', '3' },
                              { '4', '5', '6' },
                              { '7', '8', '9' }};
    
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
        if(!win) {
            handleInput(choice);
        }
    }

    public char[][] getGrid() {
        return grid_;
    }

    /**
     * Checks all possible win conditions (rows, columns, diagonals)
     * for the current player marker and updates win state if found.
     */
    private void checkWin() {

        for (int i = 0; i < grid_.length; i++) {

            // Check Horizontally
            if (grid_[i][0] == currentMarker)
                if (grid_[i][1] == currentMarker)
                    if (grid_[i][2] == currentMarker)
                        win = true;

            // Check Vertically
            if (grid_[0][i] == currentMarker)
                if (grid_[1][i] == currentMarker)
                    if (grid_[2][i] == currentMarker)
                        win = true;
        }

        // Check Primary Diagonal -> \
        if (grid_[0][0] == currentMarker)
            if (grid_[1][1] == currentMarker)
                if (grid_[2][2] == currentMarker)
                    win = true;

        // Check Secondary Diagonal -> /
        if (grid_[0][2] == currentMarker)
            if (grid_[1][1] == currentMarker)
                if (grid_[2][0] == currentMarker)
                    win = true;
    }

    /**
     * Resets the game state including grid, turn, and current marker.
     */
    private void resetGame() {
        currentMarker = 'X';
        turn = 1;
        win = false;

        grid_ = new char[][] {{ '1', '2', '3' },
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

        // Take Actions
        mark(row, col);
    }

    /**
     * Marks a cell on the grid with the current player's symbol if valid.
     *
     * @param row row index of the cell
     * @param col column index of the cell
     */
    private void mark(int row, int col) {
        if (grid_[row][col] != player1Marker && grid_[row][col] != player2Marker) {
            grid_[row][col] = currentMarker;
            checkWin();
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