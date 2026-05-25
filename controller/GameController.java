package controller;

import model.Game;
import view.GameGUI;

public class GameController {
    public GameController(Game game, GameGUI view) {
        var grid = view.getGrid();

        for (var row : grid) {
            for (var btn : row) {
                btn.addActionListener(e -> {

                    view.alert(null);
                    
                    try {
                        int choice = Integer.parseInt(btn.getText());
                        game.play(choice);
                    } catch (Exception exception) {
                        view.alert("Invalid Input, Box is already marked");
                    }
                    
                    if (game.win)
                        view.alert("Player" + game.turn + " Won, Please reset Game");

                    view.render(game);
                });
            }
        }
    }
}
