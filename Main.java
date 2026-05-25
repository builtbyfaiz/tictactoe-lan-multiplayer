import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        TicTacToe ticTacToe = new TicTacToe();

        while (true) {
            System.out.print("Enter Input (1-9): ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.close();
            ticTacToe.play(choice);
        }

        // JFrame frame = new JFrame("TicTacToe");
        // frame.setSize(400, 300);
        // frame.setLayout(new FlowLayout());
        // frame.setVisible(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel displayGrid;
    }
}