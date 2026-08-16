package src.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import src.model.Game;
import src.model.NetworkState;
import src.model.Theme;

/// Renders game state in GUI window
public class GameGUI {
    
    // --- Variable Declarations ---
    private final int windowWidth  = 800;
    private final int windowHeight = 600;
    private final int sidebarWidth = (3 * windowWidth) / 10; // 30% of window width

    // Game Regions
    private Game game;
    
    private JFrame frame     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar   = new JPanel(new GridLayout(6, 1, 0, 0));
    private JPanel gameArea  = new JPanel(new GridBagLayout());
    private JButton[][] grid = new JButton[3][3];
    
    // --- Side Bar Components ---

    // Labels
    private JLabel infoLabel  = new JLabel(); // Combination label of score and turn
    private JLabel alertLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score: 0-0");
    private JLabel turnLabel  = new JLabel("Turn: Player-1");

    // Buttons
    private JButton resetButton   = new JButton("RESET");
    private JButton clientButton  = new JButton("Client");
    private JButton serverButton  = new JButton("Server");

    private JCheckBox multiplayerCheckbox = new JCheckBox("Multiplayer");

    // Wrappers
    private JPanel infoLabelBlock      = new JPanel(new GridBagLayout()); // GridBag auto centers children
    private JPanel alertLabelBlock     = new JPanel(new GridBagLayout());
    private JPanel networkButtonsBlock = new JPanel(new GridBagLayout());

    // Contructor
    public GameGUI(Game game) {
        this.game = game;
        initWindow();
        initGrid();
        initGameArea();
        initSidebar();

        frame.add(sidebar,  BorderLayout.WEST);
        frame.add(gameArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // --- Getter/Setters ---
    public JFrame    getWindow() { return frame; }
    public JButton[][] getGrid() { return grid;  }

    public JButton getResetButton()  { return resetButton;  }
    public JButton getServerButton() { return serverButton; }
    public JButton getClientButton() { return clientButton; }
    public JToggleButton getToggleMultiplayerButton() { return multiplayerCheckbox; }

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
                    grid[i][j].setForeground(Theme.PRIMARY_COLOR);
                    grid[i][j].setBorder(Theme.CYAN_BORDER);
                }

                if (characterGrid[i][j] == 'O') {
                    grid[i][j].setForeground(Theme.SECONDARY_COLOR);
                    grid[i][j].setBorder(Theme.PINK_BORDER);
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
                button.setFont(Theme.LARGE_FONT);

                button.setForeground(Color.DARK_GRAY);
                button.setBackground(Theme.MAIN_BG_COLOR);
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
        
        gameArea .setBackground(Theme.MAIN_BG_COLOR);
        gameFrame.setBackground(Theme.MAIN_BG_COLOR);

        gameArea .setPreferredSize(new Dimension(gameAreaWidth, windowHeight));
        gameFrame.setPreferredSize(new Dimension(gameFrameSize, gameFrameSize));

        for (var row : grid) {
            for (var button : row) {
                gameFrame.add(button);
            }
        }

        gameArea.add(gameFrame);
    }
            
    private void initSidebar() {
        sidebar.setPreferredSize(new Dimension(sidebarWidth, windowHeight));
        sidebar.setBackground(Theme.SIDEBAR_COLOR);

        initInfoLabel(); 
        initAlertLabel();
        initResetbutton();
        initNetworkbuttons();
        initMultiplayerToggle();

        String cyanHex = "#00E5FF"; 

        JLabel emptySpace = new JLabel("<html>TicTac<span style='color: " + cyanHex + ";'>X</span></html>");
        emptySpace.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42)); // Sans-serif drops the outdated serif hooks
        emptySpace.setForeground(Color.WHITE);
        emptySpace.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar.add(emptySpace);
        sidebar.add(resetButton);
        sidebar.add(infoLabelBlock);
        sidebar.add(alertLabelBlock);
        sidebar.add(multiplayerCheckbox);
        sidebar.add(networkButtonsBlock);
    }
    
    private void initNetworkbuttons() {
        networkButtonsBlock.setBackground(Theme.SIDEBAR_COLOR);
        networkButtonsBlock.add(serverButton);
        networkButtonsBlock.add(clientButton);

        for (Component button : networkButtonsBlock.getComponents()) {
            button.setFont(Theme.TINY_FONT);
            button.setBackground(Theme.MAIN_BG_COLOR);
            button.setForeground(Theme.TEXT_COLOR);
            button.setPreferredSize(new Dimension(100,50));
        }
        serverButton.setBorder(Theme.PINK_BORDER);
        serverButton.setFocusPainted(false);

        clientButton.setBorder(Theme.CYAN_BORDER);
        clientButton.setFocusPainted(false);
    }
    
    private void initMultiplayerToggle() {
        multiplayerCheckbox.setText(" L.A.N ");
        multiplayerCheckbox.setFont(Theme.MEDIUM_FONT);
        multiplayerCheckbox.setBackground(Theme.SECONDARY_ACCENT_COLOR);
        multiplayerCheckbox.setForeground(Theme.TEXT_COLOR);
        multiplayerCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        multiplayerCheckbox.setFocusPainted(false);
        multiplayerCheckbox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Toggle text color when checked/unchecked
        multiplayerCheckbox.addItemListener(e -> {
            if (multiplayerCheckbox.isSelected()) {
                multiplayerCheckbox.setForeground(Theme.PRIMARY_COLOR);
            } else {
                multiplayerCheckbox.setForeground(Theme.TEXT_COLOR);
                multiplayerCheckbox.setBackground(Theme.SECONDARY_ACCENT_COLOR);
            }
        });        
    }

    private void initResetbutton() {
        resetButton.setFont(Theme.MEDIUM_FONT);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Theme.SECONDARY_ACCENT_COLOR);
        resetButton.setForeground(Theme.TEXT_COLOR);
        resetButton.setBorder(Theme.PINK_BORDER);
    }

    // Score + Turn Label
    private void initInfoLabel() {
        
        infoLabel = new JLabel("<html><center>" + scoreLabel.getText() +
                                         "<br>" +  turnLabel.getText() + "</center></html>");        
                                         
        infoLabel.setFont(Theme.SMALL_FONT);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(Theme.PRIMARY_COLOR);
        
        Border border = BorderFactory.createMatteBorder(6, 6, 0, 6, Theme.SIDEBAR_COLOR);
        infoLabelBlock.setBorder(border);
        infoLabelBlock.setBackground(Theme.INFO_BLOCK_COLOR);
        infoLabelBlock.add(infoLabel);
    }

    // Win/Invalid Condition Label
    private void initAlertLabel() {

        alertLabel.setFont(Theme.SMALL_FONT);
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alertLabel.setForeground(Theme.PRIMARY_COLOR); 

        Border border = BorderFactory.createMatteBorder(0, 6, 6, 6, Theme.SIDEBAR_COLOR);
        alertLabelBlock.setBorder(border);
        alertLabelBlock.setBackground(Theme.INFO_BLOCK_COLOR);

        alertLabelBlock.add(alertLabel);
    }
    
    // GUI Utils
    public void resetGridColors() {
        for (var row : grid) {
            for (var button : row) {
                button.setBorder(Theme.DEFAULT_BORDER);
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
                setAlertLabel("<html><center>Client Initialized.<br>Enter Server IP.</center></html>");
                break;

            case CONNECTED:
                setAlertLabel("<html><center>Connected Successfully.</center></html>");
                break;

            default:
                break;
        }
    }
}
