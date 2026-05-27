import model.Game;
import view.GameGUI;


import controller.GameController;

class Main {
    static Game ticTacToe  = new Game();
    static GameGUI gameGUI = new GameGUI(ticTacToe);
    
    public static void main(String[] args) {
        GameController controller = new GameController(ticTacToe, gameGUI);
    }
}