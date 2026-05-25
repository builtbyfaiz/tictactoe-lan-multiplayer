import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("TicTacToe");
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(3, 3, 5, 5));

        List<JButton> labels = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {

            JButton label = new JButton(String.valueOf(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            labels.add(label);
            grid.add(label);
        }

        frame.add(grid, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}