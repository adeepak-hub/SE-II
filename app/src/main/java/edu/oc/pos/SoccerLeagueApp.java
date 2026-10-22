package edu.oc.pos;

import edu.oc.pos.db.Database;
import edu.oc.pos.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class SoccerLeagueApp {
    private SoccerLeagueApp() {
    }

    public static void main(String[] args) {
        AppContext context = new AppContext(Database.fileBacked());
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Keep the default look and feel if the system theme is unavailable.
            }
            MainFrame frame = new MainFrame(context);
            frame.setVisible(true);
        });
    }
}
