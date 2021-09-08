package cz.zweistein.gw2.app.eventwatch.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cz.zweistein.gw2.app.eventwatch.EventWatcherModel;

public class EventDialog extends JFrame implements ActionListener {
	private static final long serialVersionUID = -5505049052341677804L;

	private JLabel eventStatusLabel;

	JComboBox eventNamesComboBox;
	JComboBox worldNamesComboBox;

	private EventWatcherModel model;

	public EventDialog(EventWatcherModel model) {

		this.model = model;
		model.setGui(this);

		JPanel content = new JPanel();

		content.setLayout(new GridBagLayout());

		add(content);

		eventStatusLabel = new JLabel();
		content.add(eventStatusLabel,
				new GridBagConstraints(0, 0, 1, 1, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		eventStatusLabel.setOpaque(true);
		updateEventState();

		List<CodeTableItem> eventNamesEntries = new ArrayList<CodeTableItem>();
		for (Entry<String, String> item : model.getEventNames().entrySet()) {
			eventNamesEntries.add(new CodeTableItem(item));
		}
		Collections.sort(eventNamesEntries, new CodeTableEntryComparator());

		eventNamesComboBox = new JComboBox(eventNamesEntries.toArray(new CodeTableItem[eventNamesEntries.size()]));
		eventNamesComboBox.addActionListener(this);
		content.add(eventNamesComboBox, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		List<CodeTableItem> worldNamesEntries = new ArrayList<CodeTableItem>();
		for (Entry<Long, String> item : model.getWorldNames().entrySet()) {
			worldNamesEntries.add(new CodeTableItem(item));
		}
		Collections.sort(worldNamesEntries, new CodeTableEntryComparator());

		worldNamesComboBox = new JComboBox(worldNamesEntries.toArray(new CodeTableItem[worldNamesEntries.size()]));
		worldNamesComboBox.addActionListener(this);
		content.add(worldNamesComboBox, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
				0), 0, 0));

		JButton toggleButton = new JButton("-");
		content.add(toggleButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		toggleButton.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		updateModel();
		updateTitle();

		setAlwaysOnTop(true);
		pack();
		setVisible(true);

	}

	public void updateEventState() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				eventStatusLabel.setText(model.getEventState().toString());

				switch (model.getEventState()) {
				case INVALID:
					eventStatusLabel.setBackground(Color.PINK);
					break;
				case ACTIVE:
					eventStatusLabel.setBackground(Color.GREEN);
					break;
				case INACTIVE:
					eventStatusLabel.setBackground(Color.CYAN);
					break;
				case PREPARATION:
					eventStatusLabel.setBackground(Color.BLUE);
					break;
				case WARMUP:
					eventStatusLabel.setBackground(Color.YELLOW);
					break;
				case SUCCESS:
					eventStatusLabel.setBackground(Color.LIGHT_GRAY);
					break;
				case FAIL:
					eventStatusLabel.setBackground(Color.RED);
					break;
				default:
					break;
				}
			}
		});
	}

	private void updateTitle() {
		setTitle(model.getEventNames().get(model.getSelectedEvent()) + " @ " + model.getWorldNames().get(model.getSelectedWorld()));
	}

	public void updateModel() {
		model.setSelectedEvent((String) ((CodeTableItem) eventNamesComboBox.getSelectedItem()).getItem().getKey());
		model.setSelectedWorld((Long) ((CodeTableItem) worldNamesComboBox.getSelectedItem()).getItem().getKey());
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == eventNamesComboBox) {
			model.setSelectedEvent((String) ((CodeTableItem) eventNamesComboBox.getSelectedItem()).getItem().getKey());
		} else if (ae.getSource() == worldNamesComboBox) {
			model.setSelectedWorld((Long) ((CodeTableItem) worldNamesComboBox.getSelectedItem()).getItem().getKey());
		} else {
			eventNamesComboBox.setVisible(!eventNamesComboBox.isVisible());
			worldNamesComboBox.setVisible(!worldNamesComboBox.isVisible());
		}
		updateEventState();
		updateTitle();
	}

}
