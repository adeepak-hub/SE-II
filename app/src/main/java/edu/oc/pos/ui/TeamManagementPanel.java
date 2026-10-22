package edu.oc.pos.ui;

import edu.oc.pos.domain.Team;
import edu.oc.pos.domain.TeamRequest;
import edu.oc.pos.service.TeamService;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class TeamManagementPanel extends JPanel {
    private final TeamService teamService;
    private final JTextField nameField = new JTextField(20);
    private final JTextField homeVenueField = new JTextField(20);
    private final JTextField coachNameField = new JTextField(20);
    private final DefaultListModel<Team> teamListModel = new DefaultListModel<>();
    private final JList<Team> teamList = new JList<>(teamListModel);
    private Long selectedTeamId;
    private Runnable teamsChangedListener = () -> {
    };

    public TeamManagementPanel(TeamService teamService) {
        this.teamService = teamService;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(buildForm(), BorderLayout.NORTH);
        add(buildTeamList(), BorderLayout.CENTER);
        reloadTeams();
    }

    public void setTeamsChangedListener(Runnable teamsChangedListener) {
        this.teamsChangedListener = teamsChangedListener;
    }

    public void reloadTeams() {
        teamListModel.clear();
        teamService.listTeams().forEach(teamListModel::addElement);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Team details"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.anchor = GridBagConstraints.WEST;

        addRow(form, constraints, 0, "Team Name", nameField);
        addRow(form, constraints, 1, "HomeVenue", homeVenueField);
        addRow(form, constraints, 2, "Coach Name", coachNameField);

        JButton saveButton = new JButton("Save Team");
        saveButton.addActionListener(event -> saveTeam());
        constraints.gridx = 1;
        constraints.gridy = 3;
        form.add(saveButton, constraints);
        return form;
    }

    private JPanel buildTeamList() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Active teams"));
        teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                Team selected = teamList.getSelectedValue();
                if (selected != null) {
                    selectedTeamId = selected.getId();
                    nameField.setText(selected.getName());
                    homeVenueField.setText(selected.getHomeVenue());
                    coachNameField.setText(selected.getCoachName());
                }
            }
        });
        panel.add(new JScrollPane(teamList), BorderLayout.CENTER);
        return panel;
    }

    private void saveTeam() {
        TeamRequest request = new TeamRequest(
                nameField.getText().trim(),
                homeVenueField.getText().trim(),
                coachNameField.getText().trim()
        );
        try {
            if (selectedTeamId == null) {
                teamService.createTeam(request);
            } else {
                teamService.updateTeam(selectedTeamId, request);
            }
            selectedTeamId = null;
            nameField.setText("");
            homeVenueField.setText("");
            coachNameField.setText("");
            teamList.clearSelection();
            reloadTeams();
            teamsChangedListener.run();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unable to save team", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRow(JPanel form, GridBagConstraints constraints, int row, String label, JTextField field) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.fill = GridBagConstraints.NONE;
        form.add(new JLabel(label), constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        form.add(field, constraints);
    }
}
