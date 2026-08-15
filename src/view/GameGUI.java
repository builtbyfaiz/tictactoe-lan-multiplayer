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
    private final int sidebarWidth = (3 * windowWidth) / 10; // 30% of window width

    // Fonts
    private final String FONT = Font.SANS_SERIF;

    private final Font TINY_FONT   = new Font(FONT, Font.BOLD, 16);
    private final Font SMALL_FONT  = new Font(FONT, Font.BOLD, 24);
    private final Font SMALL_MEDIUM_FONT  = new Font(FONT, Font.BOLD, 28);
    private final Font MEDIUM_FONT = new Font(FONT, Font.BOLD, 32);
    private final Font LARGE_FONT  = new Font(FONT, Font.BOLD, 72);
    
    // Color Pallete
    Color mainBg      = new Color(6, 6, 19);
    Color sidebarBg   = new Color(24, 18, 36);
    Color siderbarCenterBg =  new Color(14, 10, 26);

    Color primary        = new Color(0, 229, 255);
    Color primaryAccent   = new Color(0, 114, 128);
    
    Color secondary      = new Color(234, 0, 154);
    Color secondaryAccent = new Color(104, 21, 65);
    
    Color text = new Color(240, 240, 245);
    
    // Borders
    Border cyanBorder    = BorderFactory.createLineBorder(primaryAccent, 2);
    Border pinkBorder    = BorderFactory.createLineBorder(secondaryAccent, 2);

    Border defaultBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    // Game Regions
    private Game game;
    
    private JFrame frame     = new JFrame("TicTacToe By Faiz");
    private JPanel sidebar   = new JPanel(new GridLayout(6, 1, 10, 0));
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
    private JPanel infoLabelBlock      = new JPanel(new GridBagLayout());
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
                button.setFont(LARGE_FONT);

                button.setForeground(Color.DARK_GRAY);
                button.setBackground(mainBg);
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
        
        gameArea.setBackground(mainBg);
        gameFrame.setBackground(mainBg);

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
        sidebar.setPreferredSize(new Dimension(sidebarWidth, windowHeight));
        sidebar.setBackground(sidebarBg);

        // Logo
        JLabel logo = new JLabel("");
        // logo.setFont(new Font(Font.SERIF, Font.BOLD, 40));
    
        // logo.setForeground(secondary);
        // logo.setHorizontalAlignment(SwingConstants.CENTER);

        initInfoLabel(); 
        initAlertLabel();
        initResetbutton();
        initNetworkbuttons();
        initMultiplayerToggle();
        
        sidebar.add(logo);
        sidebar.add(resetButton);
        sidebar.add(infoLabelBlock);
        sidebar.add(alertLabelBlock);
        sidebar.add(multiplayerCheckbox);
        sidebar.add(networkButtonsBlock);
    }
    
    private void initNetworkbuttons() {
        networkButtonsBlock.setBackground(sidebarBg);
        networkButtonsBlock.add(serverButton);
        networkButtonsBlock.add(clientButton);

        for (Component button : networkButtonsBlock.getComponents()) {
            button.setFont(TINY_FONT);
            button.setBackground(mainBg);
            button.setForeground(text);
            button.setPreferredSize(new Dimension(100,50));
        }
        serverButton.setBorder(pinkBorder);
        serverButton.setFocusPainted(false);

        clientButton.setBorder(cyanBorder);
        clientButton.setFocusPainted(false);
    }
    
    private void initMultiplayerToggle() {
        multiplayerCheckbox.setText(" L.A.N ");
        multiplayerCheckbox.setFont(MEDIUM_FONT);
        multiplayerCheckbox.setBackground(secondaryAccent);
        multiplayerCheckbox.setForeground(text);
        multiplayerCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        multiplayerCheckbox.setFocusPainted(false);
        multiplayerCheckbox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Toggle text color when checked/unchecked
        multiplayerCheckbox.addItemListener(e -> {
            if (multiplayerCheckbox.isSelected()) {
                multiplayerCheckbox.setBackground(secondary);
            } else {
                multiplayerCheckbox.setBackground(secondaryAccent);
            }
        });        
    }

    private void initResetbutton() {
        resetButton.setFont(MEDIUM_FONT);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(secondaryAccent);
        resetButton.setForeground(text);
        resetButton.setBorder(pinkBorder);
    }

    // Score + Turn Label
    private void initInfoLabel() {
        
        infoLabel = new JLabel("<html><center>" + scoreLabel.getText() +
                                         "<br>" +  turnLabel.getText() + "</center></html>");
        
                                         
        infoLabel.setFont(SMALL_FONT);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(primary);

        Border border = BorderFactory.createMatteBorder(2, 2, 0, 2, primaryAccent);
        infoLabelBlock.setBorder(border);

        infoLabelBlock.add(infoLabel);
    }

    // Win/Invalid Condition Label
    private void initAlertLabel() {

        alertLabel.setFont(SMALL_FONT);
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alertLabel.setForeground(Color.white); 

        Border border = BorderFactory.createMatteBorder(0, 2, 2, 2, primaryAccent);
        alertLabelBlock.setBorder(border);
        
        alertLabelBlock.setBackground(new Color(14, 10, 26));
        infoLabelBlock.setBackground(new Color(14, 10, 26));

        // alertLabelBlock.setBackground(appBgSidebar);
        // infoLabelBlock.setBackground(appBgSidebar);
        
        alertLabelBlock.add(alertLabel);
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
