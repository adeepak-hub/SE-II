package edu.oc.pos.ui;

import edu.oc.pos.AppContext;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

public class MainFrame extends JFrame {
    public MainFrame(AppContext context) {
        super("Soccer League System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));

        TeamManagementPanel teamPanel = new TeamManagementPanel(context.teamService());
        MatchSchedulePanel matchPanel = new MatchSchedulePanel(context.teamService(), context.matchService());
        teamPanel.setTeamsChangedListener(() -> {
            matchPanel.reloadTeams();
            matchPanel.reloadMatches();
        });

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Team Management", teamPanel);
        tabs.addTab("Match Schedule", matchPanel);
        setContentPane(tabs);
        pack();
        setLocationRelativeTo(null);
    }
}
