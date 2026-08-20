package src.model.themes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class LavaTheme {

    public static void apply() {

        // Symbols
        Theme.Symbols.X = "X";
        Theme.Symbols.O = "O";

        // Colors
        Theme.Colors.TEXT             = new Color(245, 225, 210);       //rgb(245, 225, 210) ash-warm white
        Theme.Colors.MAIN_BG          = new Color(12, 6, 5);            //rgb(12, 6, 5) charred black
        Theme.Colors.SIDEBAR          = new Color(26, 12, 8);           //rgb(26, 12, 8) obsidian
        Theme.Colors.INFO_BLOCK       = new Color(18, 8, 6);            //rgb(18, 8, 6) cooled rock

        Theme.Colors.PRIMARY          = new Color(255, 87, 34);         //rgb(255, 87, 34) molten orange
        Theme.Colors.SECONDARY        = new Color(255, 23, 23);         //rgb(255, 23, 23) lava red
        Theme.Colors.PRIMARY_ACCENT   = new Color(140, 45, 15);         //rgb(140, 45, 15) burnt ember
        Theme.Colors.SECONDARY_ACCENT = new Color(120, 15, 15);         //rgb(120, 15, 15) dried blood red

        Theme.Colors.LOGO             = new Color(255, 140, 0);         //rgb(255, 140, 0) glowing amber
        Theme.Colors.INACTIVE_ELEMENT = new Color(70, 40, 30);          //rgb(70, 40, 30) cold ash

        // Fonts
        Theme.Fonts.TINY   = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        Theme.Fonts.SMALL  = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        Theme.Fonts.MEDIUM = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        Theme.Fonts.LARGE  = new Font(Font.SANS_SERIF, Font.BOLD, 72);

        // Borders
        Theme.Borders.PRIMARY   = BorderFactory.createLineBorder(Theme.Colors.PRIMARY_ACCENT  , 2);
        Theme.Borders.SECONDARY = BorderFactory.createLineBorder(Theme.Colors.SECONDARY_ACCENT, 2);
        Theme.Borders.DEFAULT   = BorderFactory.createLineBorder(Theme.Colors.INACTIVE_ELEMENT, 2);

        Theme.Borders.INFO_BLOCK_TOP    = BorderFactory.createMatteBorder(11, 11, 0, 11, Theme.Colors.SIDEBAR);
        Theme.Borders.INFO_BLOCK_BOTTOM = BorderFactory.createMatteBorder(0, 11, 11, 11, Theme.Colors.SIDEBAR);
    }
}