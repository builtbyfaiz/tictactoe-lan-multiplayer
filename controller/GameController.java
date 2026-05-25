package controller;

import model.Game;
import view.GameGUI;

public class GameController {
    public GameController(Game game, GameGUI view) {
        var grid = view.getGrid();

        for (var row : grid) {
            for (var btn : row) {
                btn.addActionListener(e -> {

                    view.setAlertLabel(null);
                    
                    try {
                        int choice = Integer.parseInt(btn.getText());
                        game.play(choice);
                    } catch (Exception exception) {
                        view.setAlertLabel("Invalid Input, Box is already marked");
                    }

                    if (game.win)
                        view.setAlertLabel("<html><center>Player " + game.getTurn()
                    + " Won!<br>Please reset Game</center></html>");
                    
                    view.setTurnLabel("Turn: Player-" + game.getTurn());
                    view.setScoreLabel(game.getScore());
                    view.updateInfoLabel(); // Internally update itself to align with latest score and turn

                    view.render(game);
                });
            }
        }

        view.resetBtn.addActionListener(e -> {
            game.play(0);
            view.render(game);
        });
    }
}
