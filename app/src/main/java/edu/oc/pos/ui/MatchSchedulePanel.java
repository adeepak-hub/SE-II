package edu.oc.pos.ui;

import edu.oc.pos.domain.Match;
import edu.oc.pos.domain.MatchRequest;
import edu.oc.pos.domain.Team;
import edu.oc.pos.service.MatchService;
import edu.oc.pos.service.TeamService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MatchSchedulePanel extends JPanel {
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final TeamService teamService;
    private final MatchService matchService;
    private final JComboBox<Team> homeTeamCombo = new JComboBox<>();
    private final JComboBox<Team> awayTeamCombo = new JComboBox<>();
    private final JSpinner matchDateSpinner = new JSpinner(new SpinnerDateModel());
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Match ID", "Home team", "Away team", "Date/time", "Status"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public MatchSchedulePanel(TeamService teamService, MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        matchDateSpinner.setEditor(new JSpinner.DateEditor(matchDateSpinner, "yyyy-MM-dd HH:mm"));
        add(buildScheduleForm(), BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        reloadTeams();
        reloadMatches();
    }

    public void reloadTeams() {
        Team selectedHome = (Team) homeTeamCombo.getSelectedItem();
        Team selectedAway = (Team) awayTeamCombo.getSelectedItem();
        homeTeamCombo.removeAllItems();
        awayTeamCombo.removeAllItems();
        List<Team> teams = teamService.listTeams();
        teams.forEach(homeTeamCombo::addItem);
        teams.forEach(awayTeamCombo::addItem);
        if (selectedHome != null) {
            homeTeamCombo.setSelectedItem(findTeam(teams, selectedHome.getId()));
        }
        if (selectedAway != null) {
            awayTeamCombo.setSelectedItem(findTeam(teams, selectedAway.getId()));
        }
    }

    public void reloadMatches() {
        tableModel.setRowCount(0);
        List<Team> teams = teamService.listTeams();
        for (Match match : matchService.listMatches()) {
            tableModel.addRow(new Object[]{
                    match.getId(),
                    teamName(teams, match.getHomeTeamId()),
                    teamName(teams, match.getAwayTeamId()),
                    DATE_TIME.format(match.getMatchDate()),
                    match.getStatus()
            });
        }
    }

    private JPanel buildScheduleForm() {
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBorder(BorderFactory.createTitledBorder("Schedule match"));
        form.add(new JLabel("Home team"));
        form.add(homeTeamCombo);
        form.add(new JLabel("Away team"));
        form.add(awayTeamCombo);
        form.add(new JLabel("Date/time"));
        form.add(matchDateSpinner);
        JButton scheduleButton = new JButton("Schedule Match");
        scheduleButton.addActionListener(event -> scheduleMatch());
        form.add(scheduleButton);
        return form;
    }

    private void scheduleMatch() {
        Team home = (Team) homeTeamCombo.getSelectedItem();
        Team away = (Team) awayTeamCombo.getSelectedItem();
        Date spinnerDate = (Date) matchDateSpinner.getValue();
        LocalDateTime matchDate = LocalDateTime.ofInstant(spinnerDate.toInstant(), ZoneId.systemDefault());
        try {
            Long homeId = home == null ? null : home.getId();
            Long awayId = away == null ? null : away.getId();
            matchService.scheduleMatch(new MatchRequest(homeId, awayId, matchDate));
            reloadMatches();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unable to schedule match", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Team findTeam(List<Team> teams, Long id) {
        return teams.stream().filter(team -> team.getId().equals(id)).findFirst().orElse(null);
    }

    private String teamName(List<Team> teams, Long id) {
        Team team = findTeam(teams, id);
        return team == null ? String.valueOf(id) : team.getName();
    }
}
