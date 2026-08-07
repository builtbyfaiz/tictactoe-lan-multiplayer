package controller;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

/// Manager class acting as middleman between GUI, Main, and Network
public class GameController {
    private Game     game;     // Console implementation
    private GameGUI  view;     // Gui render of the game state
    private GamePeer network;  // Responsible for connectivity

    private TurnState turnState;
    private NetworkState networkState;

    private Scanner sc = new Scanner(System.in);

    public GameController(Game game, GameGUI view) {
        this.game = game;
        this.view = view;

        view.getClientButton().setVisible(false);
        view.getServerButton().setVisible(false);

        // Close network connection to free the port for next time
        view.getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (network != null) {
                    network.disconnect();
                }
            }
        });

        bindEvents();
    }

    // --- Binder Functions: Connect GUI buttons to their actions ---

    private void bindEvents() {
        bindGrid();
        bindResetButton();
        bindNetworkButtons();  
        bindMultiplayerToggleButton();
    }

    private void bindMultiplayerEvents() {
        bindMultiplayerGrid();
        bindMultiplayerResetButton();
        bindNetworkButtons();  
        bindMultiplayerToggleButton();
    }

    private void unbindEvents() {
        unbindGrid();
        unbindNetworkButtons();
        unbindResetButton();
        unbindToggleMultiplayerButton();
    }

    // Bind Single Player
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

                    view.updateInfoLabel(); // Internally update itself to align with latest score and turn
                    view.updateGameGrid();
                });
            }
        }
    }
    
    private void bindResetButton() {
        view.getResetButton().addActionListener(e -> {resetGame();});}

    private void resetGame() {
        unbindEvents();
        bindEvents();

        game.play(0);
        view.resetGridColors();
        view.setAlertLabel(null);
        view.updateInfoLabel();
        view.updateGameGrid();
    }

    private void bindMultiplayerResetButton() {
        view.getResetButton().addActionListener(e -> {
            resetMultiplayerGame();
        });
    }

    private void resetMultiplayerGame() {
        unbindEvents();
        bindMultiplayerEvents();

        game.play(0);

        // if(network!=null)
        //     network.send(0);

        view.resetGridColors();
        view.setAlertLabel(null);
        view.updateInfoLabel();
        view.updateGameGrid();
    }

    // Bind Multi Player
    private void bindMultiplayerGrid() {
        for (var row : view.getGrid()) {
            for (var button : row) {
                button.addActionListener(e -> {
                    view.setAlertLabel(null);

                    try {
                        if (turnState == TurnState.MY_TURN) {
                            int choice = Integer.parseInt(button.getText());
                            if (network.send(choice)) {
                                game.play(choice);
                                view.update();
                                turnState = TurnState.MOVE_SENT;
                            }
                        }
                        if (turnState == TurnState.MOVE_SENT) {
                            new Thread(() -> {
                                int move = network.receive();
                                if (move != -1) {
                                    SwingUtilities.invokeLater(() -> {
                                        game.play(move);
                                        view.update();
                                        turnState = TurnState.MY_TURN;
                                    });
                                }
                            }).start();
                            turnState = TurnState.WAITING_FOR_OPPONENT;
                        }
                    } catch (Exception exception) {
                        view.setAlertLabel("<html><center>Invalid Input!<br>Box already marked</center></html>");
                    }

                    if (game.win)
                        view.setAlertLabel("<html><center>Player " + game.getTurn()
                                + " Won!<br>Please Reset Game</center></html>");

                    view.updateInfoLabel(); // Internally update itself to align with latest score and turn
                    view.updateGameGrid();
                });
            }
        }
    }
    
    private void bindNetworkButtons() {
        view.getServerButton().addActionListener(e -> {
            initServer();
        });

        view.getClientButton().addActionListener(e -> {
            initClient();
        });
    }

    private void bindMultiplayerToggleButton() {
        JToggleButton btn = view.getToggleMultiplayerButton();
        btn.addActionListener(e -> {
            toggleMultiplayer(btn.isSelected());
        });
    }
    
    // Multiplayer Utils
    private void toggleMultiplayer(boolean isEnabled) {
        view.getClientButton().setVisible(isEnabled);
        view.getServerButton().setVisible(isEnabled);

        if (isEnabled) {
            networkState = NetworkState.MULTIPLAYER_INIT;
            resetMultiplayerGame();
        } else {
            networkState = NetworkState.DISCONNECTED;
            resetGame();
        }
    }
    
    private void initClient() {
        if (network != null)
            network.disconnect();
        network = new GameClient();
        setNetworkState(NetworkState.CLIENT_INIT);
        turnState = TurnState.WAITING_FOR_OPPONENT;

        new Thread(() -> { // New thread to prevent System.in blocking program, (#TODO use GUI input)
            String IP = "";

            boolean connected = false;
            while (!connected) {
                System.out.print("Enter Server IP to Connect to: ");
                IP = sc.next();

                connected = network.connect(IP);
                if (connected) {
                    SwingUtilities.invokeLater(() -> {
                        setNetworkState(NetworkState.CONNECTED);
                        toggleMultiplayer(true);
                        if (turnState == TurnState.WAITING_FOR_OPPONENT) {
                            new Thread(() -> {
                                int move = network.receive();
                                if (move != -1) {
                                    SwingUtilities.invokeLater(() -> {
                                        game.play(move);
                                        view.update();
                                        turnState = TurnState.MY_TURN;
                                    });
                                }
                            }).start();
                        }
                    });
                }
            }
            // sc.close();
        }).start();
    }

    private void initServer() {
        if (network != null)
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
                    turnState = TurnState.MY_TURN;
                }
            });
        }).start();
    }
 
    private void setNetworkState(NetworkState state) {
        this.networkState = state;
        view.updateNetworkState(state, network.getIP());
    }
    
    // Unbind
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
        JButton button = view.getResetButton();
        for (ActionListener al : button.getActionListeners())
            button.removeActionListener(al);
    }

    private void unbindToggleMultiplayerButton() {
        JToggleButton button = view.getToggleMultiplayerButton();
        for (ActionListener al : button.getActionListeners())
            button.removeActionListener(al);
    }
}
