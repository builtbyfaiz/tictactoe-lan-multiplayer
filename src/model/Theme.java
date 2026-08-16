package src.model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Theme {
    
    // Fonts
    private static final String FONT = Font.SANS_SERIF;

    public static final Font TINY_FONT   = new Font(FONT, Font.BOLD, 16);
    public static final Font SMALL_FONT  = new Font(FONT, Font.BOLD, 24);
    public static final Font MEDIUM_FONT = new Font(FONT, Font.BOLD, 32);
    public static final Font LARGE_FONT  = new Font(FONT, Font.BOLD, 72);
    
    // Color Pallete
    public static final Color TEXT_COLOR       = new Color(240, 240, 245);
    public static final Color MAIN_BG_COLOR    = new Color(6, 6, 19);
    public static final Color SIDEBAR_COLOR    = new Color(24, 18, 36);
    public static final Color INFO_BLOCK_COLOR = new Color(14, 10, 26);

    public static final Color PRIMARY_COLOR   = new Color(0, 229, 255);
    public static final Color SECONDARY_COLOR = new Color(234, 0, 154);

    public static final Color PRIMARY_ACCENT_COLOR   = new Color(0, 114, 128);
    public static final Color SECONDARY_ACCENT_COLOR = new Color(104, 21, 65);
    

    // Borders
    public static final Border CYAN_BORDER = BorderFactory.createLineBorder(PRIMARY_ACCENT_COLOR, 2);
    public static final Border PINK_BORDER = BorderFactory.createLineBorder(SECONDARY_ACCENT_COLOR, 2);

    public static final Border DEFAULT_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
}
