package view;

import java.awt.*;
import javax.swing.*;
import model.Game;

public class GameGUI {

    private final int windowWidth  = 800;
    private final int windowHeight = 600;

    private JFrame frame_     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar    = new JPanel(new GridLayout(6, 1, 2, 2));
    private JPanel gameArea   = new JPanel(new GridBagLayout());
    private JButton[][] grid_ = new JButton[3][3];

    private JLabel infoLabel  = new JLabel();
    private JLabel alertLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score 0-0");
    private JLabel turnLabel  = new JLabel("Turn: Player-1");

    public JButton resetBtn = new JButton("RESET");

    public GameGUI() {
        initWindow();
        initGrid();
        initGameArea();
        initSidebar();

        frame_.add(sidebar, BorderLayout.WEST);
        frame_.add(gameArea, BorderLayout.CENTER);
        frame_.setVisible(true);
    }

    private void initSidebar() {

        int sidebarWidth = (3 * windowWidth) / 10; // 30% of window width
        sidebar.setPreferredSize(new Dimension(sidebarWidth, windowHeight));
        sidebar.setBackground(Color.WHITE);

        // Logo
        JLabel logo = new JLabel("BY-FAIZ");
        logo.setFont(new Font("Roboto", Font.BOLD, 48));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        alertLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        alertLabel.setForeground(new Color(180, 40, 40)); // Red alert text color theme
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);

        resetBtn.setFont(new Font("Roboto", Font.BOLD, 22));
        resetBtn.setBackground(new Color(70, 120, 170));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        infoLabel = new JLabel("<html><center>" 
                                    + scoreLabel.getText() + "<br>" 
                                    + turnLabel.getText() + 
                                "</center></html>");



        infoLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar.add(logo);
        sidebar.add(new JLabel(""));
        sidebar.add(infoLabel);
        sidebar.add(alertLabel);
        sidebar.add(resetBtn);
    }

    public JButton[][] getGrid() {
        return grid_;
    }

    public void setTurnLabel(String text) {
        turnLabel.setText(text);
    }

    public void setScoreLabel(String text) {
        scoreLabel.setText(text);
    }

    public void updateInfoLabel() {
        infoLabel.setText("<html><center>" 
                                    + scoreLabel.getText() + "<br>" 
                                    + turnLabel.getText() + 
                          "</center></html>");
    }

    public void setAlertLabel(String text) {
        alertLabel.setText(text);
    };

    public void render(Game game) {
        char characterGrid[][] = game.getGrid();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid_[i][j].setText(String.valueOf(characterGrid[i][j]));
            }
        }
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
                btn.setFont(new Font("roboto", Font.BOLD, 72));

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

}
