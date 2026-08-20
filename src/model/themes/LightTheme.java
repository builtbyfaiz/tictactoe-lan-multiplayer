package src.model.themes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class LightTheme {

    public static void apply() {

        // Symbols
        Theme.Symbols.X = "X";
        Theme.Symbols.O = "O";

        // Colors
        Theme.Colors.TEXT             = new Color(58, 42, 28);          //rgb(58, 42, 28)
        Theme.Colors.MAIN_BG          = new Color(250, 238, 215);       //rgb(250, 238, 215)
        Theme.Colors.INFO_BLOCK       = new Color(236, 219, 193);       //rgb(236, 219, 193)
        Theme.Colors.SIDEBAR          = new Color(224, 198, 155);       //rgb(224, 198, 155)

        Theme.Colors.PRIMARY          = new Color(70, 130, 90);         //rgb(70, 130, 90)
        Theme.Colors.SECONDARY        = new Color(210, 100, 50);        //rgb(210, 100, 50)
        Theme.Colors.PRIMARY_ACCENT   = new Color(180, 210, 180);       //rgb(180, 210, 180)
        Theme.Colors.SECONDARY_ACCENT = new Color(236, 178, 128);       //rgb(236, 178, 128)
        
        Theme.Colors.LOGO             = new Color(48, 86, 58);          //rgb(48, 86, 58)
        Theme.Colors.INACTIVE_ELEMENT = new Color(158, 136, 100);       //rgb(158, 136, 100)

        // Fonts
        Theme.Fonts.TINY   = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Theme.Fonts.SMALL  = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        Theme.Fonts.MEDIUM = new Font(Font.SANS_SERIF, Font.BOLD, 34);
        Theme.Fonts.LARGE  = new Font(Font.SANS_SERIF, Font.BOLD, 72);

        // Borders
        Theme.Borders.PRIMARY   = BorderFactory.createLineBorder(Theme.Colors.PRIMARY,     4);
        Theme.Borders.SECONDARY = BorderFactory.createLineBorder(Theme.Colors.SECONDARY,   4);
        Theme.Borders.DEFAULT   = BorderFactory.createLineBorder(new Color(200, 178, 140), 4);

        Theme.Borders.INFO_BLOCK_TOP    = BorderFactory.createMatteBorder(12, 12, 0, 12, Theme.Colors.SIDEBAR);
        Theme.Borders.INFO_BLOCK_BOTTOM = BorderFactory.createMatteBorder(0, 12, 12, 12, Theme.Colors.SIDEBAR);
    }
}