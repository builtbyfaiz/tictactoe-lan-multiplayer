import model.Game;
import view.GameGUI;
import controller.GameController;

class Main {

    public static void main(String[] args) {
        
        Game ticTacToe = new Game();
        GameGUI gameGUI = new GameGUI();
        GameController controller = new GameController(ticTacToe, gameGUI);
    
    }
}