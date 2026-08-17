package src.model.themes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class ForestTheme {

    public static void apply() {

        // Symbols
        Theme.Symbols.X = "🌲";
        Theme.Symbols.O = "🍁";
        
        // Colors
        Theme.Colors.TEXT             = new Color(216, 226, 220);       // rgb(216, 226, 220)
        Theme.Colors.MAIN_BG          = new Color(15, 26, 20);          // rgb(15, 26, 20)
        Theme.Colors.SIDEBAR          = new Color(27, 59, 43);          // rgb(27, 59, 43)
        Theme.Colors.INFO_BLOCK       = new Color(18, 33, 25);          // rgb(18, 33, 25)
        
        Theme.Colors.PRIMARY          = new Color(210, 220, 212);       // rgb(210, 220, 212)
        Theme.Colors.SECONDARY        = new Color(45, 142, 87);         // rgb(45, 142, 87)
        Theme.Colors.PRIMARY_ACCENT   = new Color(216, 226, 220);       // rgb(216, 226, 220)
        Theme.Colors.SECONDARY_ACCENT = new Color(45, 106, 79);         // rgb(45, 106, 79)
        
        Theme.Colors.LOGO             = new Color(216, 226, 220);       // rgb(216, 226, 220)
        Theme.Colors.INACTIVE_ELEMENT = new Color(82, 96, 88);          // rgb(82, 96, 88)

        // Fonts
        Theme.Fonts.TINY   = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        Theme.Fonts.SMALL  = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        Theme.Fonts.MEDIUM = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        Theme.Fonts.LARGE  = new Font(Font.SANS_SERIF, Font.BOLD, 72);

        // Borders
        Theme.Borders.PRIMARY   = BorderFactory.createLineBorder(Theme.Colors.PRIMARY_ACCENT, 2);
        Theme.Borders.SECONDARY = BorderFactory.createLineBorder(Theme.Colors.SECONDARY, 2);
        Theme.Borders.DEFAULT   = BorderFactory.createLineBorder(Theme.Colors.INACTIVE_ELEMENT, 2);

        Theme.Borders.INFO_BLOCK_TOP    = BorderFactory.createMatteBorder(12, 12, 0, 12, Theme.Colors.SIDEBAR);
        Theme.Borders.INFO_BLOCK_BOTTOM = BorderFactory.createMatteBorder(0, 12, 12, 12, Theme.Colors.SIDEBAR);
    }
}