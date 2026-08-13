package src.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import src.model.Game;
import src.model.NetworkState;

/// Renders game state in GUI window
public class GameGUI {
    
    // --- Variable Declarations ---
    private final int windowWidth  = 800;
    private final int windowHeight = 600;
    
    // Fonts
    private final String FONT = Font.SANS_SERIF;

    private final Font TINY_FONT   = new Font(FONT, Font.BOLD, 16);
    private final Font SMALL_FONT  = new Font(FONT, Font.BOLD, 24);
    private final Font MEDIUM_FONT = new Font(FONT, Font.BOLD, 32);
    private final Font LARGE_FONT  = new Font(FONT, Font.BOLD, 48);
    private final Font XLARGE_FONT = new Font(FONT, Font.BOLD, 72);
    
        // Color Pallete
    Color appBgMain      = new Color(6, 6, 19);
    Color appBgSidebar   = new Color(24, 18, 36);

    Color primary        = new Color(0, 229, 255);
    Color secondary      = new Color(234, 0, 154);
    
    Color buttonResetBg   = new Color(104, 21, 65);
    Color buttonResetText = new Color(240, 240, 245);

    Color primaryLight   = new Color(0, 114, 128);
    Color secondaryLight = new Color(117, 0, 77);
    
    // Borders
    Border cyanBorder    = BorderFactory.createLineBorder(primaryLight, 2);
    Border pinkBorder    = BorderFactory.createLineBorder(secondaryLight, 2);

    Border defaultBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    // Game Regions
    private Game game;
    
    private JFrame frame     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar   = new JPanel(new GridLayout(6, 1, 2, 2));
    private JPanel gameArea  = new JPanel(new GridBagLayout());
    private JButton[][] grid = new JButton[3][3];
    
    // --- Side Bar Components ---

    // Labels
    private JLabel infoLabel  = new JLabel();
    private JLabel alertLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score: 0-0");
    private JLabel turnLabel  = new JLabel("Turn: Player-1");

    // Buttons
    private JButton resetbutton   = new JButton("RESET");
    private JButton clientbutton  = new JButton("Client");
    private JButton serverbutton  = new JButton("Server");
    private JPanel networkbuttons = new JPanel(new GridBagLayout());
    
    private JCheckBox multiplayerToggleButton = new JCheckBox("Multiplayer");

    // Contructor
    public GameGUI(Game game) {
        this.game = game;
        initWindow();
        initGrid();
        initGameArea();
        initSidebar();

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(gameArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // --- Getter/Setters ---
    public JFrame    getWindow() { return frame; }
    public JButton[][] getGrid() { return grid;  }

    public JButton getResetButton()  { return resetbutton;  }
    public JButton getServerButton() { return serverbutton; }
    public JButton getClientButton() { return clientbutton; }
    public JToggleButton getToggleMultiplayerButton() { return multiplayerToggleButton; }

    public void setTurnLabel (String text) { turnLabel .setText(text); }
    public void setScoreLabel(String text) { scoreLabel.setText(text); }
    public void setAlertLabel(String text) { alertLabel.setText(text); }

    // --- Update Funcs: Refresh Gui 
    public void updateInfoLabel() {

        setTurnLabel("Turn: Player-" + game.getTurn());
        setScoreLabel(game.getScore());

        infoLabel.setText("<html><center>"
                          + scoreLabel.getText() + "<br>"
                          + turnLabel .getText() +
                          "</center></html>");

        infoLabel.repaint();
    }

    public void updateGameGrid() {
        char characterGrid[][] = game.getGrid();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setText(String.valueOf(characterGrid[i][j]));
                
                if (characterGrid[i][j] == 'X') {
                    grid[i][j].setForeground(primary);
                    grid[i][j].setBorder(cyanBorder);
                }

                if (characterGrid[i][j] == 'O') {
                    grid[i][j].setForeground(secondary);
                    grid[i][j].setBorder(pinkBorder);
                }
            }
        }
    }

    public void update() {
        updateInfoLabel();
        updateGameGrid();
    }

    // --- Initializers ---
    private void initWindow() {
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void initGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                int buttonNumber = (i * 3) + j + 1;

                JButton button = new JButton(String.valueOf(buttonNumber));
                button.setFont(XLARGE_FONT);

                button.setForeground(Color.DARK_GRAY);
                button.setBackground(appBgMain);
                button.setHorizontalAlignment(SwingConstants.CENTER); // Makes button text centered #TEMP
                button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                button.setFocusPainted(false);
                
                grid[i][j] = button;
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

        for (var row : grid) {
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
        logo.setFont(LARGE_FONT);
        logo.setForeground(secondary);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        initInfoLabel(); 
        initAlertLabel();
        initResetbutton();
        initNetworkbuttons();
        initMultiplayerToggle();
        
        sidebar.add(logo);
        sidebar.add(multiplayerToggleButton);
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
            button.setFont(TINY_FONT);
            button.setBackground(appBgMain);
            button.setForeground(buttonResetText);
            button.setPreferredSize(new Dimension(100,50));
        }
        serverbutton.setBorder(pinkBorder);
        serverbutton.setFocusPainted(false);

        clientbutton.setBorder(cyanBorder);
        clientbutton.setFocusPainted(false);
    }
    
    private void initMultiplayerToggle() {
        multiplayerToggleButton.setText("Multiplayer");
        multiplayerToggleButton.setFont(SMALL_FONT);
        multiplayerToggleButton.setBackground(appBgSidebar);
        multiplayerToggleButton.setForeground(buttonResetBg);
        multiplayerToggleButton.setHorizontalAlignment(SwingConstants.CENTER);
        multiplayerToggleButton.setFocusPainted(false);
        multiplayerToggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void initResetbutton() {
        // Reset Button
        resetbutton.setFont(MEDIUM_FONT);
        resetbutton.setBackground(buttonResetBg);
        resetbutton.setForeground(buttonResetText);
        // resetbutton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resetbutton.setFocusPainted(false);
    }

    private void initInfoLabel() {
        // Score + Turn Label
        infoLabel = new JLabel("<html><center>"
                               + scoreLabel.getText() + "<br>"
                               + turnLabel.getText()  +
                               "</center></html>");

        infoLabel.setFont(MEDIUM_FONT);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(primary);
    }

    private void initAlertLabel() {
        // Win/Invalid Condition Label
        alertLabel.setForeground(Color.white); 
        alertLabel.setFont(SMALL_FONT);
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    // GUI Utils
    public void resetGridColors() {
        for (var row : grid) {
            for (var button : row) {
                button.setBorder(defaultBorder);
                button.setForeground(Color.DARK_GRAY);
            }
        }
    }

    public void updateNetworkState(NetworkState state, String IP) {
        switch (state) {
            case SERVER_INIT:
                setAlertLabel("<html><center>Server Initialized.<br>IP: " + IP + "</center></html>");
                break;

            case CLIENT_INIT:
                setAlertLabel("<html><center>Client Initialized.<br>Enter IP in terminal.</center></html>");
                break;

            case CONNECTED:
                setAlertLabel("<html><center>Connected Successfully.</center></html>");
                break;

            default:
                break;
        }
    }
}
