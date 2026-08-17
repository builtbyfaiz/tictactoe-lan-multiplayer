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
        Theme.Colors.TEXT             = new Color(216, 226, 220); // Soft Mist / Muted Sage (#D8E2DC)
        Theme.Colors.MAIN_BG          = new Color(15, 26, 20);    // Deep Shadow Green (#0F1A14)
        Theme.Colors.SIDEBAR          = new Color(27, 59, 43);    // Deep Pine Green (#1B3B2B)
        Theme.Colors.INFO_BLOCK       = new Color(18, 33, 25);    // Dark Undergrowth Container (#122119)
        
        Theme.Colors.PRIMARY          = new Color(210, 220, 212); // Muted Soft White/Sage (Leaf Text & Borders)
        Theme.Colors.SECONDARY        = new Color(45, 142, 87);   // Dark Pine/Forest Green (Tree Text & Accent)
        
        Theme.Colors.PRIMARY_ACCENT   = new Color(216, 226, 220); // Soft Sage Border Accent
        Theme.Colors.SECONDARY_ACCENT = new Color(45, 106, 79);   // Forest Canopy Green (Button Backgrounds)
        
        Theme.Colors.LOGO             = new Color(216, 226, 220); // Softened Logo Text
        Theme.Colors.INACTIVE_ELEMENT = new Color(82, 96, 88);    // Moss Gray (#526058)

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