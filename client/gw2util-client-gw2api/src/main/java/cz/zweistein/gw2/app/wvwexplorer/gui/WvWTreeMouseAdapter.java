package cz.zweistein.gw2.app.wvwexplorer.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import cz.zweistein.gw2.api.dto.WvWMap;
import cz.zweistein.gw2.api.dto.WvWObjective;
import cz.zweistein.gw2.app.wvwexplorer.WvWExplorerModel;
import cz.zweistein.gw2.app.wvwexplorer.constants.Icons;
import cz.zweistein.gw2.app.wvwexplorer.constants.WvWNames;

public class WvWTreeMouseAdapter extends MouseAdapter {

	WvWExplorerModel model;

	public WvWTreeMouseAdapter(WvWExplorerModel model) {
		super();
		this.model = model;
	}

	private void myPopupEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		JTree tree = (JTree) e.getSource();
		TreePath path = tree.getPathForLocation(x, y);
		if (path == null)
			return;

		tree.setSelectionPath(path);

		Object obj = (Object) path.getLastPathComponent();

		JPopupMenu popup = new JPopupMenu();
		if (obj instanceof WvWObjective) {
			final WvWObjective objective = (WvWObjective) obj;
			if (model.getWatchedObjectives().contains(objective.getObjectiveId())) {

				JMenuItem enable = new JMenuItem("Disable watching " + model.getObjectiveNames().get(objective.getObjectiveId()) + " (" + WvWNames.objective.get(objective.getObjectiveId()) + ")", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_OPEN)));

				popup.add(enable);

				enable.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						model.getWatchedObjectives().remove(objective.getObjectiveId());
					}
				});
			} else {

				JMenuItem disable = new JMenuItem("Enable watching " + model.getObjectiveNames().get(objective.getObjectiveId()) + " (" + WvWNames.objective.get(objective.getObjectiveId()) + ")", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_CLOSED)));
				popup.add(disable);

				disable.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						model.getWatchedObjectives().add(objective.getObjectiveId());
					}
				});

			}

		} else if (obj instanceof WvWMap) {
			final WvWMap map = (WvWMap) obj;

			final JMenuItem enable = new JMenuItem("Enable watching " + WvWNames.map.get(map.getType()), new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_OPEN)));
			popup.add(enable);
			final JMenuItem disable = new JMenuItem("Disable watching " + WvWNames.map.get(map.getType()), new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_CLOSED)));
			popup.add(disable);

			enable.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					List<Long> objectiveIds = new ArrayList<Long>(map.getObjectives().size());
					for (WvWObjective objective : map.getObjectives()) {
						objectiveIds.add(objective.getObjectiveId());
					}

					model.getWatchedObjectives().addAll(objectiveIds);
				}
			});

			disable.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					List<Long> objectiveIds = new ArrayList<Long>(map.getObjectives().size());
					for (WvWObjective objective : map.getObjectives()) {
						objectiveIds.add(objective.getObjectiveId());
					}

					model.getWatchedObjectives().removeAll(objectiveIds);
				}
			});

		}
		popup.show(tree, x, y);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			myPopupEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger())
			myPopupEvent(e);
	}

}
