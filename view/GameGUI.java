package view;

import java.awt.*;

import javax.swing.*;

import model.Game;

public class GameGUI {

    private static void init() {
        int windowWidth = 800;
        int windowHeight = 600;

        JFrame frame = new JFrame("TicTacToe");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(3, 3, 3, 3));
        grid.setPreferredSize(new Dimension(400, 400));

        JPanel gameArea = new JPanel(new GridBagLayout());
        gameArea.add(grid);

        
        JPanel sidebar = new JPanel(new FlowLayout());
        sidebar.setBackground(Color.RED);
        sidebar.setPreferredSize(new Dimension((30*windowWidth)/100, 800));

        for (int i = 1; i <= 9; i++) {

            JButton label = new JButton(String.valueOf(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBackground(Color.GREEN);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            grid.add(label);
        }

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(gameArea, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    public void render(Game game) {
        char grid[][] = game.getGrid();
    }

    public static void main(String[] args) {
        init();
    }
}
