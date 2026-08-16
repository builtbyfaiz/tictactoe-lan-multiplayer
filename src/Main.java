package src;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import src.controller.GameController;
import src.model.Game;
import src.model.themes.DarkTheme;
import src.model.themes.LightTheme;
import src.model.themes.Theme;
import src.view.GameGUI;
import java.awt.Color;

class Main {

    public static void main(String[] args) {

        initLookAndFeel();
        
        Game ticTacToe = new Game();
        GameGUI gameGUI = new GameGUI(ticTacToe);
        
        // Creating the controller binds it and starts the game
        GameController controller = new GameController(ticTacToe, gameGUI);
    }

    // Setsup GUI helpers, applies base theme
    private static void initLookAndFeel() {

        DarkTheme.apply();
        
        // Flat laf provides a modern look to java swing.
        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Component.accentColor", Theme.Colors.PRIMARY_ACCENT);
    }
}
