package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import model.Game;

public class GameGUI {

    // Variable Declarations
    private final int windowWidth  = 800;
    private final int windowHeight = 600;
    
    private Game game;
    
    private JFrame frame_     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar    = new JPanel(new GridLayout(6, 1, 2, 2));
    private JPanel gameArea   = new JPanel(new GridBagLayout());
    private JButton[][] grid_ = new JButton[3][3];

    //Side Bar Components
    private JLabel infoLabel  = new JLabel();
    private JLabel alertLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score: 0-0");
    private JLabel turnLabel  = new JLabel("Turn: Player-1");

    private JButton resetbutton = new JButton("RESET");

    private JButton clientbutton  = new JButton("Client");
    private JButton serverbutton  = new JButton("Server");
    private JPanel networkbuttons = new JPanel(new GridBagLayout());

    // Color Pallete
    Color appBgMain      = new Color(6, 6, 19);
    Color appBgSidebar   = new Color(24, 18, 36);

    Color primary        = new Color(0, 229, 255);
    Color secondary      = new Color(234, 0, 154);

    Color buttonResetBg     = new Color(104, 21, 65);
    Color buttonResetText   = new Color(240, 240, 245);

    Color primaryLight   = new Color(0, 114, 128);
    Color secondaryLight = new Color(117, 0, 77);
    
    Border cyanBorder    = BorderFactory.createLineBorder(primaryLight, 2);
    Border pinkBorder    = BorderFactory.createLineBorder(secondaryLight, 2);

    Border defaultBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    // Contructor
    public GameGUI(Game game) {
        this.game = game;
        initWindow();
        initGrid();
        initGameArea();
        initSidebar();

        frame_.add(sidebar, BorderLayout.WEST);
        frame_.add(gameArea, BorderLayout.CENTER);
        frame_.setVisible(true);
    }

    // Getter/Seters
    public JFrame    getWindow() { return frame_; }
    public JButton[][] getGrid() { return grid_;  }

    // public JButton getResebutton()   { return resetbutton;  }
    public JButton getReseButton()   { return resetbutton;  }
    public JButton getServerButton() { return serverbutton; }
    public JButton getClientButton() { return clientbutton; }

    public void setTurnLabel(String text)  { turnLabel.setText(text);  }
    public void setScoreLabel(String text) { scoreLabel.setText(text); }
    public void setAlertLabel(String text) { alertLabel.setText(text); }

    public void updateInfoLabel() {
        infoLabel.setText("<html><center>"
                          + scoreLabel.getText() + "<br>"
                          + turnLabel.getText()  +
                          "</center></html>");
    }

    public void renderGameGrid() {
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

                JButton button = new JButton(String.valueOf(buttonNumber));
                button.setFont(new Font("Roboto", Font.BOLD, 72));

                button.setBackground(appBgMain);
                button.setHorizontalAlignment(SwingConstants.CENTER); // Makes button text centered #TEMP
                button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                button.setFocusPainted(false);
                
                grid_[i][j] = button;
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
            for (var button : row) {
                gameFrame.add(button);
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

        initInfoLabel(); 
        initAlertLabel();
        initResetbutton();
        initNetworkbuttons();

        // JToggleButton multiplayerToggleBtton;
        
        sidebar.add(logo);
        sidebar.add(new JLabel(""));
        sidebar.add(infoLabel);
        sidebar.add(alertLabel);
        sidebar.add(resetbutton);
        sidebar.add(networkbuttons);
    }

    private void initNetworkbuttons() {
        networkbuttons.setBackground(appBgSidebar);
        networkbuttons.add(serverbutton);
        networkbuttons.add(clientbutton);

        for (Component button : networkbuttons.getComponents()) {
            button.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
            button.setBackground(appBgMain);
            button.setForeground(buttonResetText);
            button.setPreferredSize(new Dimension(100,50));
        }
        serverbutton.setBorder(pinkBorder);
        serverbutton.setFocusPainted(false);

        clientbutton.setBorder(cyanBorder);
        clientbutton.setFocusPainted(false);
    }

    private void initInfoLabel() {
        // Score + Turn Label
        infoLabel = new JLabel("<html><center>"
                               + scoreLabel.getText() + "<br>"
                               + turnLabel.getText()  +
                               "</center></html>");

        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(primary);
    }

    private void initResetbutton() {
        // Reset Button
        resetbutton.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        resetbutton.setBackground(buttonResetBg);
        resetbutton.setForeground(buttonResetText);
        resetbutton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resetbutton.setFocusPainted(false);
    }

    private void initAlertLabel() {
        // Win/Invalid Condition Label
        alertLabel.setForeground(Color.white); 
        alertLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    public void resetGridColors() {
        for (var row : grid_) {
            for (var button : row) {
                button.setBorder(defaultBorder);
                button.setForeground(Color.DARK_GRAY);
            }
        }
    }
}
