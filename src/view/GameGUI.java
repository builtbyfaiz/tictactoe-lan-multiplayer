package src.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import src.model.Game;
import src.model.NetworkState;
import src.model.themes.Theme;


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
    private JLabel logoLabel  = new JLabel(); // "TicTacX" sidebar logo

    // Buttons
    private JButton resetButton   = new JButton("RESET");
    private JButton clientButton  = new JButton("Client");
    private JButton serverButton  = new JButton("Server");

    private JCheckBox multiplayerCheckbox = new JCheckBox("Multiplayer");

    // Wrappers
    private JPanel infoLabelBlock      = new JPanel(new GridBagLayout()); // GridBag auto centers children
    private JPanel alertLabelBlock     = new JPanel(new GridBagLayout());
    private JPanel networkButtonsBlock = new JPanel(new GridBagLayout());

    private JPanel multiplayerCheckboxBlock = new JPanel(new GridLayout(1,1,0,0));

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

                if (characterGrid[i][j] == 'X') {
                    grid[i][j].setText(Theme.Symbols.X);
                    grid[i][j].setForeground(Theme.Colors.PRIMARY);
                    grid[i][j].setBorder(Theme.Borders.PRIMARY);
                }
                else if (characterGrid[i][j] == 'O') {
                    grid[i][j].setText(Theme.Symbols.O);
                    grid[i][j].setForeground(Theme.Colors.SECONDARY);
                    grid[i][j].setBorder(Theme.Borders.SECONDARY);
                }
                else {
                    grid[i][j].setText(String.valueOf(characterGrid[i][j])); 
                }
            }
        }
    }

    public void update() {
        updateInfoLabel();
        updateGameGrid();
    }

    /**
     * Applies a new theme to the running GUI in-place.
     * <p>
     * Runs the theme's {@code apply()} (sets new static Theme.* token values),
     * then re-styles every existing component directly - no components are
     * recreated, so no listener rebinding is needed on the controller side.
     */
    public void setTheme(Runnable themeApply) {
        themeApply.run();

        frame.getContentPane().setBackground(Theme.Colors.MAIN_BG);
        sidebar.setBackground(Theme.Colors.SIDEBAR);
        gameArea.setBackground(Theme.Colors.MAIN_BG);

        for (var row : grid) {
            for (var button : row) {
                styleGridButton(button);
            }
        }

        styleLogoLabel();
        styleInfoLabelBlock();
        styleAlertLabelBlock();
        styleResetButton();
        styleNetworkButtons();
        styleMultiplayerToggle();

        frame.repaint();
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
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setFocusPainted(false);
                styleGridButton(button);

                grid[i][j] = button;
            }
        }
    }

    private void initGameArea() {
        int gameAreaWidth = (windowWidth   * 7) / 10;  // 70% of Window Width
        int gameFrameSize = (gameAreaWidth * 7) / 10;  // 70% of GameArea

        JPanel gameFrame = new JPanel(new GridLayout(3, 3, 8, 8));
        
        gameArea .setBackground(Theme.Colors.MAIN_BG);
        gameFrame.setBackground(Theme.Colors.MAIN_BG);

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
        sidebar.setBackground(Theme.Colors.SIDEBAR);

        initInfoLabel(); 
        initAlertLabel();
        initResetbutton();
        initNetworkbuttons();
        initMultiplayerToggle();
        initLogoLabel();

        sidebar.add(logoLabel);
        sidebar.add(resetButton);
        sidebar.add(infoLabelBlock);
        sidebar.add(alertLabelBlock);
        sidebar.add(multiplayerCheckboxBlock);
        sidebar.add(networkButtonsBlock);
    }

    // "TicTacX" sidebar logo
    private void initLogoLabel() {
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42)); // Sans-serif drops the outdated serif hooks
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        styleLogoLabel();
    }
    
    private void initNetworkbuttons() {
        networkButtonsBlock.add(serverButton);
        networkButtonsBlock.add(clientButton);

        for (Component button : networkButtonsBlock.getComponents()) {
            ((JButton) button).setPreferredSize(new Dimension(100,50));
            ((JButton) button).setFocusPainted(false);
        }

        styleNetworkButtons();
    }
    
    private void initMultiplayerToggle() {
        multiplayerCheckbox.setText(" L.A.N ");
        multiplayerCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        multiplayerCheckbox.setFocusPainted(false);
        multiplayerCheckbox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Toggle text color when checked/unchecked
        multiplayerCheckbox.addItemListener(e -> styleMultiplayerToggle());

        styleMultiplayerToggle();

        multiplayerCheckboxBlock.add(multiplayerCheckbox);
    }

    private void initResetbutton() {
        resetButton.setFocusPainted(false);
        styleResetButton();
    }

    // Score + Turn Label
    private void initInfoLabel() {
        
        infoLabel = new JLabel("<html><center>" + scoreLabel.getText() +
                                         "<br>" +  turnLabel.getText() + "</center></html>");        
                                         
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        styleInfoLabelBlock();

        infoLabelBlock.add(infoLabel);
    }

    // Win/Invalid Condition Label
    private void initAlertLabel() {

        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);

        styleAlertLabelBlock();

        alertLabelBlock.add(alertLabel);
    }

    // --- Styling Functions ---

    private void styleGridButton(JButton button) {
        button.setFont(Theme.Fonts.LARGE);
        button.setBackground(Theme.Colors.MAIN_BG);

        if (button.getText().equals(Theme.Symbols.X)) {
            button.setForeground(Theme.Colors.PRIMARY);
            button.setBorder(Theme.Borders.PRIMARY);
        }
        else if (button.getText().equals(Theme.Symbols.O)) {
            button.setForeground(Theme.Colors.SECONDARY);
            button.setBorder(Theme.Borders.SECONDARY);
        }
        else {
            button.setForeground(Theme.Colors.INACTIVE_ELEMENT);
            button.setBorder(Theme.Borders.DEFAULT);
        }
    }

    private void styleLogoLabel() {
        logoLabel.setText("<html>TicTac<span style='color: rgb("
                          + Theme.Colors.PRIMARY.getRed()   + ","
                          + Theme.Colors.PRIMARY.getGreen() + ","
                          + Theme.Colors.PRIMARY.getBlue()  + ");'>X</span></html>");
        logoLabel.setForeground(Theme.Colors.LOGO);
    }

    private void styleInfoLabelBlock() {
        infoLabel.setFont(Theme.Fonts.SMALL);
        infoLabel.setForeground(Theme.Colors.PRIMARY);

        infoLabelBlock.setBorder(Theme.Borders.INFO_BLOCK_TOP);
        infoLabelBlock.setBackground(Theme.Colors.INFO_BLOCK);
    }

    private void styleAlertLabelBlock() {
        alertLabel.setFont(Theme.Fonts.SMALL);
        alertLabel.setForeground(Theme.Colors.PRIMARY);

        alertLabelBlock.setBorder(Theme.Borders.INFO_BLOCK_BOTTOM);
        alertLabelBlock.setBackground(Theme.Colors.INFO_BLOCK);
    }

    private void styleResetButton() {
        resetButton.setFont(Theme.Fonts.MEDIUM);
        resetButton.setBackground(Theme.Colors.SECONDARY_ACCENT);
        resetButton.setForeground(Theme.Colors.TEXT);
        resetButton.setBorder(Theme.Borders.SECONDARY);
    }

    private void styleNetworkButtons() {
        networkButtonsBlock.setBackground(Theme.Colors.SIDEBAR);

        for (Component button : networkButtonsBlock.getComponents()) {
            button.setFont(Theme.Fonts.TINY);
            button.setBackground(Theme.Colors.MAIN_BG);
            button.setForeground(Theme.Colors.TEXT);
        }

        serverButton.setBorder(Theme.Borders.SECONDARY);
        clientButton.setBorder(Theme.Borders.PRIMARY);
    }

    private void styleMultiplayerToggle() {
        multiplayerCheckbox.setFont(Theme.Fonts.MEDIUM);
        multiplayerCheckbox.setBackground(Theme.Colors.SECONDARY_ACCENT);

        if (multiplayerCheckbox.isSelected()) {
            multiplayerCheckbox.setForeground(Theme.Colors.PRIMARY);
        } else {
            multiplayerCheckbox.setForeground(Theme.Colors.TEXT);
        }

        multiplayerCheckboxBlock.setBorder(Theme.Borders.SECONDARY);
    }
    
    // GUI Utils
    public void resetGridColors() {
        for (var row : grid) {
            for (var button : row) {
                button.setBorder(Theme.Borders.DEFAULT);
                button.setForeground(Theme.Colors.INACTIVE_ELEMENT);
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