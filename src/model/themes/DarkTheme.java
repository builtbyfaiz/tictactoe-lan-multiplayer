package src.model.themes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class DarkTheme {

    public static void apply() {
        
        // Colors
        Theme.Colors.TEXT             = new Color(240, 240, 245);
        Theme.Colors.LOGO             = Color.WHITE;
        Theme.Colors.MAIN_BG          = new Color(6, 6, 19);
        Theme.Colors.SIDEBAR          = new Color(24, 18, 36);
        Theme.Colors.INFO_BLOCK       = new Color(14, 10, 26);
        Theme.Colors.PRIMARY          = new Color(0, 229, 255);
        Theme.Colors.SECONDARY        = new Color(234, 0, 154);
        Theme.Colors.PRIMARY_ACCENT   = new Color(0, 114, 128);
        Theme.Colors.SECONDARY_ACCENT = new Color(104, 21, 65);
        Theme.Colors.INACTIVE_ELEMENT = Color.DARK_GRAY;

        // Fonts
        Theme.Fonts.TINY   = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        Theme.Fonts.SMALL  = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        Theme.Fonts.MEDIUM = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        Theme.Fonts.LARGE  = new Font(Font.SANS_SERIF, Font.BOLD, 72);

        // Borders
        Theme.Borders.CYAN    = BorderFactory.createLineBorder(Theme.Colors.PRIMARY_ACCENT  , 2);
        Theme.Borders.PINK    = BorderFactory.createLineBorder(Theme.Colors.SECONDARY_ACCENT, 2);
        Theme.Borders.DEFAULT = BorderFactory.createLineBorder(Theme.Colors.INACTIVE_ELEMENT, 2);
    }
}