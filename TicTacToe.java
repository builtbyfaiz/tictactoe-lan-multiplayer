import java.util.Scanner;

class TicTacToe1 {

    private char grid[][] = { { '-', '-', '-' },
            { '-', '-', '-' },
            { '-', '-', '-' } };

    int turn = 1;
    char currentMarker = 'X';

    public void printGrid() {
        System.out.print("\033[H\033[2J"); //Clear terminal with escape sequences
        System.out.flush();

        System.out.println();
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void mark(int gridIndexToMark) {
        if (gridIndexToMark >= 1 && gridIndexToMark <= 10) {
            switch (gridIndexToMark) {
                case 1: grid[0][0] = currentMarker; break;
                case 2: grid[0][1] = currentMarker; break;
                case 3: grid[0][2] = currentMarker; break;
                case 4: grid[1][0] = currentMarker; break;
                case 5: grid[1][1] = currentMarker; break;
                case 6: grid[1][2] = currentMarker; break;
                case 7: grid[2][0] = currentMarker; break;
                case 8: grid[2][1] = currentMarker; break;
                case 9: grid[2][2] = currentMarker; break;
                default: break;
            }
            toggleTurn();
        }
        else {
            System.out.println("Invalid Input, Please input 1-9");
        }
    }

    public void play() {
        printGrid();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Input (1-9): ");
        mark(sc.nextInt());
    };

    public void toggleTurn() {
        turn = turn == 1 ? 2 : 1; // Toggles turn from 1 and 2 each time
        currentMarker = currentMarker == 'X' ? 'O' : 'X'; // Toggles the character
    }
}

public class TicTacToe {
    public static void main(String[] args) {
        TicTacToe1 tac = new TicTacToe1();
        tac.printGrid();
    }
}