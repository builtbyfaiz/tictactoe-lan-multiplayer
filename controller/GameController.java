package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import model.Game;
import view.GameGUI;
import network.GameClient;
import network.GamePeer;
import network.GameServer;

public class GameController {
    private Game game;
    private GameGUI view;
    private boolean multiplayerMode = false;

    private GamePeer network;

    public GameController(Game game, GameGUI view) {
        this.game = game;
        this.view = view;

        view.getWindow().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                network.disconnect();
            }
        });

        bindEvents();
    }

    private void bindEvents() {
        bindGridEvents();
        bindResetBtnEvents();
        bindNetworkButtons();
    }

    private void bindResetBtnEvents() {
        view.getReseBtn().addActionListener(e -> {
            game.play(0);
            view.resetGridColors();
            view.setAlertLabel(null);
            view.renderGameGrid(game);
        });
    }

    private void bindNetworkButtons() {
        view.getServerBtn().addActionListener(e -> {
            network = new GameServer();
            String IP = "";
            try {
                IP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
            }
            view.setAlertLabel("<html>Waiting for client.<br>IP: " + IP + "</html>");
            view.renderGameGrid(game);
            boolean conneced = network.connect(null);
            if(conneced)
                view.setAlertLabel("<html>Successfully Connected.<br>Make first move.</html>");
        });

        view.getClientBtn().addActionListener(e -> {
            network = new GameClient();

            System.out.println("Enter Server IP to Connect to: ");
            Scanner sc = new Scanner(System.in);
            String IP = sc.next();
            sc.close();

            boolean connected = network.connect(IP);
            if(connected)
                view.setAlertLabel("Successfully connected to server with IP: " + IP);
        });
    }

    private void bindGridEvents() {
        for (var row : view.getGrid()) {
            for (var btn : row) {
                btn.addActionListener(e -> {

                    view.setAlertLabel(null);

                    try {
                        int choice = Integer.parseInt(btn.getText());
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

                    view.renderGameGrid(game);
                });
            }
        }
    }
}
