package controller;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;

import model.Game;
import view.GameGUI;
import network.GameClient;
import network.GamePeer;
import network.GameServer;

enum ConnectionStatus {
    SERVER_INIT,
    CLIENT_INIT,
    CONNECTED,
    DISCONNECTED,
    FAILED
}

enum TurnState {
    YOUR_TURN,
    WAITING_FOR_OPPONENT,
    SENDING_MOVE,
    PROCESSING_MOVE
}

public class GameController {
    private Game game;
    private GameGUI view;
    private GamePeer network;

    ConnectionStatus networkState;
    TurnState turnState;

    private boolean multiplayerMode = false;
    private boolean isMyTurn = false;

    public GameController(Game game, GameGUI view) {
        this.game = game;
        this.view = view;

        view.getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (multiplayerMode)
                    network.disconnect();
            }
        });

        bindEvents();
    }

    private void bindEvents() {
        // bindGridEvents();
        bindNetworkButtons();
        bindResetButtonEvents();
    }

    private void unbindGridEvents() {
        // Remove all listeners from grid
        for (var row : view.getGrid()) {
            for (var button : row) {
                for (ActionListener al : button.getActionListeners())
                    button.removeActionListener(al);
            }
        }
    }

    private void bindResetButtonEvents() {
        view.getReseButton().addActionListener(e -> {

            System.out.println("Single Player[1] | Multiplayer [2]: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.close();

            if (choice == 1) {
                if (multiplayerMode) {
                    network.disconnect();
                    multiplayerMode = false;
                }
                unbindGridEvents();
                bindGridEvents();
            }

            if (choice == 2) {
                unbindGridEvents();
                bindMultiplayerGridEvents();
            }

            game.play(0);
            view.resetGridColors();
            view.setAlertLabel(null);
            view.renderGameGrid();
        });
    }

    private void bindNetworkButtons() {

        view.getServerButton().addActionListener(e -> {
            network.disconnect();
            network = new GameServer();

            view.setAlertLabel("<html><center>Waiting for client.<br>IP: " + network.getIP() + "</center></html>");

            boolean connected = network.connect(null);
            if (connected) {
                view.setAlertLabel("<html><center>Connected...<br>Make first move.</center></html>");
                multiplayerMode = true;
                isMyTurn = true;
                bindMultiplayerGridEvents();
            }
        });

        view.getClientButton().addActionListener(e -> {

            network = new GameClient();

            Scanner sc = new Scanner(System.in);

            boolean connected = false;
            String IP = "";

            while (!connected) {
                System.out.print("Enter Server IP to Connect to: ");
                IP = sc.next();

                connected = network.connect(IP);
                if (connected) {
                    view.setAlertLabel(
                            "<html><center>Successfully connected to.<br>Server IP: " + IP +
                                    "</center></html>");
                    multiplayerMode = true;
                    bindMultiplayerGridEvents();
                }
            }
            sc.close();
        });
    }

    private void bindGridEvents() {
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

                    view.renderGameGrid();
                });
            }
        }
    }

    private void bindMultiplayerGridEvents() {
        if (!multiplayerMode) {
            System.out.println("Cannot Bind Grid Events, as game is not in multiplayer mode");
            return;
        }

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

                    view.renderGameGrid();
                });
            }
        }
    }
}
