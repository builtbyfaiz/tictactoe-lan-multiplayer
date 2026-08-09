package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Game;
import model.NetworkState;
import view.GameGUI;
import network.GameClient;
import network.GamePeer;
import network.GameServer;

/// Manager class acting as middleman between GUI, Main, and Network
public class GameController {

    private static final String INVALID_MOVE_MSG = "<html><center>Invalid Input!<br>Box already marked</center></html>";

    private final Game game;     // Console implementation
    private final GameGUI view;  // GUI render of the game state

    private GamePeer network;         // Active connection, null when not in multiplayer
    private boolean multiplayerMode;  // State of multiplayer
    private boolean isServer;         // Server is always X and moves first
    private boolean myTurn;           // If it is turn of current instance

    public GameController(Game game, GameGUI view) {
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

    // --- Gameplay ---
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

    private void onResetClicked() {
        if (!multiplayerMode) {
            resetGame();
            return;
        }

        // Multiplayer: Only if game end + instance is server.
        if (isServer && game.win) {
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

    private void initServer() {
        if (network != null)
            network.disconnect();
        network = new GameServer();
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

    private void initClient() {
        if (network != null) network.disconnect();
        network  = new GameClient();
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

    private void listenForOpponentMove() {
        new Thread(() -> {
            int move = network.receive();

            SwingUtilities.invokeLater(() -> {
                if (move == -1) handleDisconnect();
                else            applyRemoteMove(move);
            });

        }).start();
    }

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
                + " Won!<br>Please Reset Game</center></html>");
    }
}