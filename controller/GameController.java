package controller;

import model.Game;
import view.GameGUI;

public class GameController {
    public GameController(Game game, GameGUI view) {
        var grid = view.getGrid();

        for (var row : grid) {
            for (var btn : row) {
                btn.addActionListener(e->{
                    game.play(Integer.parseInt(btn.getText()));
                    view.render(game);
                });
            }
        }
    }
}
