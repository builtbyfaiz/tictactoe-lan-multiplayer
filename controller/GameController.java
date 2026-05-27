package controller;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import model.Game;
import model.NetworkState;
import model.TurnState;
import view.GameGUI;
import network.GameClient;
import network.GamePeer;
import network.GameServer;

public class GameController {
    private Game game;
    private GameGUI view;
    private GamePeer network;

    NetworkState networkState;
    TurnState turnState;

    private boolean isMyTurn = false;

    public GameController(Game game, GameGUI view) {
        this.game = game;
        this.view = view;

        view.getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (network != null) {
                    network.disconnect();
                }
            }
        });

        bindEvents();
    }

    private void bindEvents() {
        bindGrid();
        bindResetButton();
        bindMultiplayerToggleButton();
    }

    private void bindMultiplayerEvents() {
        bindMultiplayerGrid();
        bindNetworkButtons(); // TBA, WIP
        bindResetButton(); // TBA, WIP
    }

    private void unbindEvents() {
        unbindGrid();
        unbindNetworkButtons();
        unbindResetButton();
    }

    // Util
    private void setNetworkState(NetworkState state) {
        this.networkState = state;
        view.updateNetworkState(state, network.getIP());
    }

    private void unbindGrid() {
        // Remove all listeners from grid
        for (var row : view.getGrid()) {
            for (var button : row) {
                for (ActionListener al : button.getActionListeners())
                    button.removeActionListener(al);
            }
        }
    }

    private void unbindNetworkButtons() {
        JButton clientButton = view.getClientButton();
        JButton serverButton = view.getServerButton();

        for (ActionListener al : clientButton.getActionListeners())
            clientButton.removeActionListener(al);

        for (ActionListener al : serverButton.getActionListeners())
            serverButton.removeActionListener(al);
    }

    private void unbindResetButton() {
        JButton button = view.getReseButton();
        for (ActionListener al : button.getActionListeners())
            button.removeActionListener(al);
    }

    private void bindResetButton() {
        view.getReseButton().addActionListener(e -> {

            unbindEvents();
            bindEvents();

            game.play(0);
            view.resetGridColors();
            view.setAlertLabel(null);
            view.updateGameGrid();
        });
    }

    private void bindMultiplayerResetButton() {

        network.disconnect();

        unbindEvents();
        bindMultiplayerEvents();

        game.play(0);
        view.setAlertLabel(null);
        view.resetGridColors();
        view.update();
    }

    private void bindNetworkButtons() {
        view.getServerButton().addActionListener(e -> {
            initServer();
        });

        view.getClientButton().addActionListener(e -> {
            initClient();
        });
    }

    private void initClient() {
        network.disconnect();
        network = new GameClient();
        setNetworkState(NetworkState.CLIENT_INIT);

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            String IP = "";

            boolean connected = false;
            while (!connected) {
                System.out.print("Enter Server IP to Connect to: ");
                IP = sc.next();

                connected = network.connect(IP);
                if (connected) {
                    SwingUtilities.invokeLater(() -> {
                        setNetworkState(NetworkState.CONNECTED);
                    });
                }
            }
            sc.close();
        }).start();

    }

    private void bindMultiplayerToggleButton() {
        JToggleButton btn = view.getMultiplayerToggleButton();
        btn.addActionListener(e -> {
            toggleMultiplayer(btn.isSelected());
        });
    }

    private void toggleMultiplayer(boolean isEnabled) {
        view.getClientButton().setVisible(isEnabled);
        view.getServerButton().setVisible(isEnabled);

        if (isEnabled) {
            networkState = NetworkState.MULTIPLAYER_INIT;
            unbindEvents();
            bindMultiplayerEvents();
        } else {
            networkState = NetworkState.DISCONNECTED;
            unbindEvents();
            bindEvents();
            if (network != null) {
                network.disconnect();
            }
        }
    }

    private void initServer() {
        network.disconnect();
        network = new GameServer();
        setNetworkState(NetworkState.SERVER_INIT);

        new Thread(() -> {
            boolean connected = network.connect(null);

            // Invoke later makes use of EDT and prevents UI glitching
            SwingUtilities.invokeLater(() -> {
                if (connected) {
                    setNetworkState(NetworkState.CONNECTED);
                    toggleMultiplayer(true);
                    isMyTurn = true;
                } else {
                    setNetworkState(NetworkState.FAILED);
                }
            });
        }).start();
    }

    private void bindGrid() {
        for (var row : view.getGrid()) {
            for (var button : row) {
                button.addActionListener(e -> {

                    view.setAlertLabel(null);

                    try {
                        int choice = Integer.parseInt(button.getText());
                        game.play(choice);
                    } catch (Exception exception) {
                        view.setAlertLabel("<html><center>Invalid Input!<br>Box already marked</center></html>");
                    }

                    if (game.win)
                        view.setAlertLabel("<html><center>Player " + game.getTurn()
                                + " Won!<br>Please Reset Game</center></html>");

                    view.setTurnLabel("Turn: Player-" + game.getTurn());
                    view.setScoreLabel(game.getScore());

                    view.updateInfoLabel(); // Internally update itself to align with latest score and turn

                    view.updateGameGrid();
                });
            }
        }
    }

    private void bindMultiplayerGrid() {
        for (var row : view.getGrid()) {
            for (var button : row) {
                button.addActionListener(e -> {
                    view.setAlertLabel(null);

                    try {
                        if (isMyTurn) {
                            int choice = Integer.parseInt(button.getText());
                            boolean moveSent = network.send(choice);
                            if (moveSent) {
                                game.play(choice);
                                isMyTurn = false;
                            }
                        }
                        if (!isMyTurn) {
                            int player2Choice = network.receive();
                            if (player2Choice != -1) {
                                game.play(player2Choice);
                                isMyTurn = true;
                            }
                        }
                    } catch (Exception exception) {
                        view.setAlertLabel("<html><center>Invalid Input!<br>Box already marked</center></html>");
                    }

                    if (game.win)
                        view.setAlertLabel("<html><center>Player " + game.getTurn()
                                + " Won!<br>Please Reset Game</center></html>");

                    view.setTurnLabel("Turn: Player-" + game.getTurn());
                    view.setScoreLabel(game.getScore());
                    view.updateInfoLabel(); // Internally update itself to align with latest score and turn

                    view.updateGameGrid();
                });
            }
        }
    }
}
