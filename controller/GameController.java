package controller;

import model.Game;
import view.GameGUI;

public class GameController {
    private Game game;
    private GameGUI view;
    
    public GameController(Game game, GameGUI view) {
        this.game = game;
        this.view = view;    

        bindEvents();
    }

    private void bindEvents() {
        bindGridEvents();
        bindResetBtnEvents();
    }

    private void bindResetBtnEvents() {
        view.resetBtn.addActionListener(e -> {
            game.play(0);
            view.resetGridColors();
            view.setAlertLabel(null);
            view.render(game);
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

                    view.render(game);
                });
            }
        }
    }
}
