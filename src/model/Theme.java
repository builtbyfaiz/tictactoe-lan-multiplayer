package src.model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Theme {

    // Fonts
    public static class Fonts {
        private static final String FONT = Font.SANS_SERIF;

        public static final Font TINY   = new Font(FONT, Font.BOLD, 16);
        public static final Font SMALL  = new Font(FONT, Font.BOLD, 24);
        public static final Font MEDIUM = new Font(FONT, Font.BOLD, 32);
        public static final Font LARGE  = new Font(FONT, Font.BOLD, 72);
    }

    // Color Pallete
    public static class Colors {
        public static final Color TEXT       = new Color(240, 240, 245);
        public static final Color MAIN_BG    = new Color(6, 6, 19);
        public static final Color SIDEBAR    = new Color(24, 18, 36);
        public static final Color INFO_BLOCK = new Color(14, 10, 26);

        public static final Color PRIMARY   = new Color(0, 229, 255);
        public static final Color SECONDARY = new Color(234, 0, 154);

        public static final Color PRIMARY_ACCENT   = new Color(0, 114, 128);
        public static final Color SECONDARY_ACCENT = new Color(104, 21, 65);
    }

    // Borders
    public static class Borders {
        public static final Border CYAN    = BorderFactory.createLineBorder(Colors.PRIMARY_ACCENT  , 2);
        public static final Border PINK    = BorderFactory.createLineBorder(Colors.SECONDARY_ACCENT, 2);
        public static final Border DEFAULT = BorderFactory.createLineBorder(Color.DARK_GRAY       , 2);
    }
}