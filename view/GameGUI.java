package view;

import java.awt.*;

import javax.swing.*;

import model.Game;

public class GameGUI {

    private final int windowWidth = 800;
    private final int windowHeight = 600;

    private JFrame frame_ = new JFrame("TicTacToe By Faiz");
    private JPanel gameArea = new JPanel(new GridBagLayout());

    private JButton[][] grid_ = new JButton[3][3]; // TicTacToe Buttons

    public void init() {

        initWindow();
        initGrid();
        initGameArea();

        JPanel sidebar = new JPanel(new FlowLayout());
        sidebar.setBackground(Color.RED);
        sidebar.setPreferredSize(new Dimension((30 * windowWidth) / 100, 800));

        frame_.add(sidebar, BorderLayout.WEST);
        frame_.add(gameArea, BorderLayout.CENTER);
        frame_.setVisible(true);

    }

    private void initWindow() {
        frame_.setSize(windowWidth, windowHeight);
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setLayout(new BorderLayout());
    }

    private void initGrid() {
        for (int i = 0; i < grid_.length; i++) {
            for (int j = 0; j < grid_.length; j++) {
                int buttonNumber = (i * 3) + j + 1;

                JButton btn = new JButton(String.valueOf(buttonNumber));
                btn.setFont(new Font("roboto",Font.BOLD, 72));

                btn.setBackground(Color.GREEN);
                btn.setHorizontalAlignment(SwingConstants.CENTER); // Makes button text centered #TEMP
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                grid_[i][j] = btn;
            }
        }
    }

    private void initGameArea() {
        int gameAreaWidth = (windowWidth * 7) / 10; // 70% of Window Width
        int gameFrameSize = (gameAreaWidth * 7) / 10; // 70% of GameArea

        JPanel gameFrame = new JPanel(new GridLayout(3, 3, 8, 8));
        gameFrame.setPreferredSize(new Dimension(gameFrameSize, gameFrameSize));

        for (var row : grid_) {
            for (var btn : row) {
                gameFrame.add(btn);
            }
        }

        gameArea.add(gameFrame);
    }

    public void render(Game game) {
        char characterGrid[][] = game.getGrid();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid_[i][j].setText(String.valueOf(characterGrid[i][j]));
            }
        }
    }
}
