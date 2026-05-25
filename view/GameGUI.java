package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import model.Game;

public class GameGUI {

    // Variable Declarations
    private final int windowWidth  = 800;
    private final int windowHeight = 600;

    private JFrame frame_     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar    = new JPanel(new GridLayout(6, 1, 2, 2));
    private JPanel gameArea   = new JPanel(new GridBagLayout());
    private JButton[][] grid_ = new JButton[3][3];

    private JLabel infoLabel  = new JLabel();
    private JLabel alertLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score: 0-0");
    private JLabel turnLabel  = new JLabel("Turn: Player-1");

    public JButton resetBtn = new JButton("RESET");

    // Color Pallete
    Color appBgMain      = new Color(6, 6, 19);
    Color appBgSidebar   = new Color(24, 18, 36);

    Color primary        = new Color(0, 229, 255);
    Color secondary      = new Color(234, 0, 154);

    Color btnResetBg     = new Color(104, 21, 65);
    Color btnResetText   = new Color(240, 240, 245);

    Color primaryLight   = new Color(0, 114, 128);
    Color secondaryLight = new Color(117, 0, 77);
    
    Border cyanBorder    = BorderFactory.createLineBorder(primaryLight, 2);
    Border pinkBorder    = BorderFactory.createLineBorder(secondaryLight, 2);

    Border defaultBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    // Contructor
    public GameGUI() {
        initWindow();
        initGrid();
        initGameArea();
        initSidebar();

        frame_.add(sidebar, BorderLayout.WEST);
        frame_.add(gameArea, BorderLayout.CENTER);
        frame_.setVisible(true);
    }

    // Getter/Seters
    public JButton[][] getGrid() { return grid_; }

    public void setTurnLabel(String text)  { turnLabel.setText(text); }
    public void setScoreLabel(String text) { scoreLabel.setText(text); }
    public void setAlertLabel(String text) { alertLabel.setText(text); }

    public void updateInfoLabel() {
        infoLabel.setText("<html><center>"
                          + scoreLabel.getText() + "<br>"
                          + turnLabel.getText()  +
                          "</center></html>");
    }

    public void render(Game game) {
        char characterGrid[][] = game.getGrid();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid_[i][j].setText(String.valueOf(characterGrid[i][j]));
                
                if (characterGrid[i][j] == 'X') {
                    grid_[i][j].setForeground(primary);
                    grid_[i][j].setBorder(cyanBorder);
                }

                if (characterGrid[i][j] == 'O') {
                    grid_[i][j].setForeground(secondary);
                    grid_[i][j].setBorder(pinkBorder);
                }
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
                btn.setFont(new Font("Roboto", Font.BOLD, 72));

                btn.setBackground(appBgMain);
                btn.setHorizontalAlignment(SwingConstants.CENTER); // Makes button text centered #TEMP
                btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                btn.setFocusPainted(false);
                
                grid_[i][j] = btn;
            }
        }
    }

    private void initGameArea() {
        int gameAreaWidth = (windowWidth   * 7) / 10;  // 70% of Window Width
        int gameFrameSize = (gameAreaWidth * 7) / 10;  // 70% of GameArea

        JPanel gameFrame = new JPanel(new GridLayout(3, 3, 8, 8));
        
        gameArea.setBackground(appBgMain);
        gameFrame.setBackground(appBgMain);

        gameArea.setPreferredSize(new Dimension(gameAreaWidth, windowHeight));
        gameFrame.setPreferredSize(new Dimension(gameFrameSize, gameFrameSize));

        for (var row : grid_) {
            for (var btn : row) {
                gameFrame.add(btn);
            }
        }

        gameArea.add(gameFrame);
    }
    
    private void initSidebar() {

        int sidebarWidth = (3 * windowWidth) / 10; // 30% of window width
        sidebar.setPreferredSize(new Dimension(sidebarWidth, windowHeight));
        sidebar.setBackground(appBgSidebar);

        // Logo
        JLabel logo = new JLabel("BY-FAIZ");
        logo.setFont(new Font(Font.SERIF, Font.BOLD, 48));
        logo.setForeground(secondary);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        // Reset Button
        resetBtn.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        resetBtn.setBackground(btnResetBg);
        resetBtn.setForeground(btnResetText);
        resetBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resetBtn.setFocusPainted(false);

        // Win/Invalid Condition Label
        alertLabel.setForeground(Color.white); 
        alertLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Score + Turn Label
        infoLabel = new JLabel("<html><center>"
                               + scoreLabel.getText() + "<br>"
                               + turnLabel.getText()  +
                               "</center></html>");

        infoLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(primary); 

        sidebar.add(logo);
        sidebar.add(new JLabel(""));
        sidebar.add(infoLabel);
        sidebar.add(alertLabel);
        sidebar.add(resetBtn);
    }
    
    public void resetGridColors() {
        for (var row : grid_) {
            for (var btn : row) {
                btn.setBorder(defaultBorder);
                btn.setForeground(Color.DARK_GRAY);
            }
        }
    }
}
