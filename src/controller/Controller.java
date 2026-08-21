package src.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import src.model.Game;
import src.model.NetworkState;

// Themes
import src.model.themes.DarkTheme;
import src.model.themes.ForestTheme;
import src.model.themes.LavaTheme;
import src.model.themes.LightTheme;

import src.network.Client;
import src.network.Peer;
import src.network.Server;
import src.view.GUI;

/**
 * Manager class acting as middleman between GUI, Main, and Network.
 * <p>
 * Keeps {@link Game} (state/rules) and {@link GUI} (rendering) decoupled from
 * each other and from the network layer, this class acts as an orchestrator
 */
public class Controller {

    private static final String INVALID_MOVE_MSG = "<html><center>Already Marked!</center></html>";

    // Centralized theme list - add/remove entries here to change the cycle order
    private static final Runnable[] THEMES = {
        DarkTheme::apply,
        LightTheme::apply,
        ForestTheme::apply,
        LavaTheme::apply
    };

    private final Game game;  // Console implementation
    private final GUI view;   // GUI render of the game state

    private Peer network;             // Active connection, null when not in multiplayer
    private boolean multiplayerMode;  // State of multiplayer
    private boolean isServer;         // Server is always X and moves first
    private boolean myTurn;           // If it is turn of current instance
    private int themeIndex;           // Index into THEMES for the currently applied theme
    
    /**
     * Wires the GUI to this controller and sets initial state.
     * <p>
     * Network buttons start hidden as multiplayer is off by default.
     * The window close listener exists to disconnect sockets afterward if present
     */
    public Controller(Game game, GUI view) {
        this.game = game;
        this.view = view;

        view.getClientButton().setVisible(false);
        view.getServerButton().setVisible(false);

        // Close network connection to free the port for next time
        view.getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (network != null)
                    network.disconnect();
            }
        });

        bindGrid();
        bindResetButton();
        bindNetworkButtons();
        bindToggleMultiplayerButton();
        bindThemeButton();
    }

    // --- Binding Functions ---

    private void bindGrid() {
        for (var row : view.getGrid()) {
            for (var button : row) {
                button.addActionListener(e -> onCellClicked(button));
            }
        }
    }

    private void bindResetButton() {
        view.getResetButton().addActionListener(e -> onResetClicked());
    }

    private void bindNetworkButtons() {
        view.getServerButton().addActionListener(e -> initServer());
        view.getClientButton().addActionListener(e -> initClient());
    }

    private void bindToggleMultiplayerButton() {
        view.getToggleMultiplayerButton()
                .addActionListener(e -> setMultiplayerMode(view.getToggleMultiplayerButton().isSelected()));
    }

    private void bindThemeButton() {
        view.getThemeButton().addActionListener(e -> onThemeClicked());
    }

    // --- Gameplay ---

    /** 
     * Handles a grid cell click.
     * <p>
     * Prevents processing if not {@code myTurn} or {@code game.win}
     * After game completion, the client calls {@link #listenForOpponentMove()} as it still
     * needs to receive the server's reset (0) or disconnect (-1) signal.
     */
    private void onCellClicked(JButton button) {
        if (multiplayerMode && (!myTurn || game.win))
            return;

        view.setAlertLabel(null);
        int choice;
        try {
            choice = Integer.parseInt(button.getText());
            game.play(choice);
        } catch (Exception e) {
            view.setAlertLabel(INVALID_MOVE_MSG);
            return;
        }

        updateView();
        if (game.win) showWinMessage();

        if (multiplayerMode) {
            if (!network.send(choice)) {
                handleDisconnect();
                return;
            }
            myTurn = false;

            if (!game.win) {
                listenForOpponentMove();
            } 
            if (game.win && !isServer) {
                // Client spawns last thread to listen for 0/-1 reset signal
                listenForOpponentMove();
            }
        }
    }

    /** 
     * Handles the reset button.
     * <p>
     * In singleplayer, it can be called in any move.
     * In multiplayer, only server resets, and only after game completion.
     */
    private void onResetClicked() {
        if (!multiplayerMode) {
            resetGame();
            return;
        }

        // Multiplayer: Only if game end + instance is server.
        if (isServer && (game.win || game.isDraw())) {
            resetGame();
            network.send(0); // Send 0 to client to reset it's game as well
            myTurn = true;
        }
    }

    private void resetGame() {
        game.play(0);
        view.resetGridColors();
        view.setAlertLabel(null);
        updateView();
    }

    /**
     * Cycles to the next theme in {@link #THEMES} and applies it to the view.
     */
    private void onThemeClicked() {
        themeIndex = (themeIndex + 1) % THEMES.length;
        view.setTheme(THEMES[themeIndex]);
    }

    /**
     * Starts hosting a game.
     * <p>
     * Connects on a background thread.
     * The server is hardcoded to move first once connected.
     */
    private void initServer() {
        if (network != null)
            network.disconnect();
        network = new Server();
        isServer = true;
        view.updateNetworkState(NetworkState.SERVER_INIT, network.getIP());

        new Thread(() -> {
            boolean connected = network.connect(null);
            if (connected) {
                SwingUtilities.invokeLater(() -> {
                    view.updateNetworkState(NetworkState.CONNECTED, network.getIP());
                    resetGame();
                    myTurn = true; // server moves first
                });
            }
        }).start();
    }

    /**
     * Starts joining a game as a client.
     * <p>
     * Prompts for an IP and connects without blocking.
     * Invokes {@link #listenForOpponentMove()} as server always moves first.
     */
    private void initClient() {
        if (network != null) network.disconnect();
        network  = new Client();
        isServer = false;
        view.updateNetworkState(NetworkState.CLIENT_INIT, null);

        new Thread(() -> {
            String ip = JOptionPane.showInputDialog(view.getWindow(), "Enter Server IP to connect to:");
            boolean connected = network.connect(ip);

            if (!connected) {
                SwingUtilities.invokeLater(() -> view.setAlertLabel("Connection failed"));
                return;
            }

            SwingUtilities.invokeLater(() -> {
                view.updateNetworkState(NetworkState.CONNECTED, network.getIP());
                resetGame();
                myTurn = false; // wait for server's first move
            });
            listenForOpponentMove();
        }).start();
    }

    // --- Multiplayer Utils ---

    /**
     * Toggles multiplayer on/off.
     * <p>
     * Disconnectes network each time to prevent misconfiguration
     */
    private void setMultiplayerMode(boolean enabled) {
        multiplayerMode = enabled;
        myTurn = false;

        view.getClientButton().setVisible(enabled);
        view.getServerButton().setVisible(enabled);

        // When enabling or disabling, flush the network
        if (network != null) {
            network.disconnect();
            network = null;
        }

        resetGame();
    }

    /**
     * Spawns a persistent blocking receive thread for the opponent's next move.
     * <p>
     * One time use by design, re-invoke the method when needed
     */
    private void listenForOpponentMove() {
        new Thread(() -> {
            int move = network.receive();

            SwingUtilities.invokeLater(() -> {
                if (move == -1) handleDisconnect();
                else            applyRemoteMove(move);
            });

        }).start();
    }

    /**
     * Applies a move received from the opponent.
     * <p>
     * 0 is the reset signal
     * After a LAN win, The client re-listens for server to send reset signal.
     */
    private void applyRemoteMove(int move) {
        if (move == 0) { // reset signal from server
            resetGame();
            myTurn = false;
            listenForOpponentMove();
            return;
        }

        game.play(move);
        updateView();

        if (game.win) { 
            showWinMessage();
            if (!isServer) listenForOpponentMove(); // wait for server's eventual 0/-1

        } else 
            myTurn = true;
    }

    /**
     * Handles opponent disconnect (signalled by {@code -1} from {@link Peer#receive()}).
     * <p>
     * Routes through {@link #setMultiplayerMode(boolean)}, so
     * UI can update as multiplayer is no longer active.
     */
    private void handleDisconnect() {
        view.setAlertLabel("Opponent disconnected");
        view.getToggleMultiplayerButton().setSelected(false);
        setMultiplayerMode(false);
    }

    // --- View Utils ---
    private void updateView() {
        view.updateInfoLabel();
        view.updateGameGrid();
    }

    private void showWinMessage() {
        view.setAlertLabel("<html><center>Player " + game.getTurn()
                + " Won!</center></html>");
    }
}