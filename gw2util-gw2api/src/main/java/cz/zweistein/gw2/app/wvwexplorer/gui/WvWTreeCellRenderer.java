package cz.zweistein.gw2.app.wvwexplorer.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import cz.zweistein.gw2.api.dto.WvWMap;
import cz.zweistein.gw2.api.dto.WvWMatchDetail;
import cz.zweistein.gw2.api.dto.WvWObjective;
import cz.zweistein.gw2.api.dto.enums.WvWSide;
import cz.zweistein.gw2.app.wvwexplorer.WvWExplorerModel;
import cz.zweistein.gw2.app.wvwexplorer.constants.Icons;
import cz.zweistein.gw2.app.wvwexplorer.constants.WvWColors;
import cz.zweistein.gw2.app.wvwexplorer.constants.WvWNames;

public class WvWTreeCellRenderer implements TreeCellRenderer {

	private WvWExplorerModel model;

	public WvWTreeCellRenderer(WvWExplorerModel model) {
		this.model = model;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel defaultTreeCellRenderer = new JLabel();

		if (value instanceof WvWMatchDetail) {
			defaultTreeCellRenderer.setText("WvWvW Match");
			defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.STATS_PIE_CHART)));
		} else if (value instanceof WvWMap) {
			WvWMap wvwMap = (WvWMap) value;

			List<Long> objectiveIds = new ArrayList<Long>(wvwMap.getObjectives().size());
			for (WvWObjective objective : wvwMap.getObjectives()) {
				objectiveIds.add(objective.getObjectiveId());
			}

			if (model.getWatchedObjectives().containsAll(objectiveIds)) {
				defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_OPEN)));
			} else if (Collections.disjoint(objectiveIds, model.getWatchedObjectives())) {
				defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_CLOSED)));
			} else {
				defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_HELP)));
			}

			switch (wvwMap.getType()) {
			case BORDERLANDS_BLUE:
				defaultTreeCellRenderer.setForeground(WvWColors.BLUE);
				defaultTreeCellRenderer.setText(WvWNames.map.get(wvwMap.getType()) + " (" + model.getSides().get(WvWSide.BLUE) + ")");
				break;
			case BORDERLANDS_RED:
				defaultTreeCellRenderer.setForeground(WvWColors.RED);
				defaultTreeCellRenderer.setText(WvWNames.map.get(wvwMap.getType()) + " (" + model.getSides().get(WvWSide.RED) + ")");
				break;
			case BORDERLANDS_GREEN:
				defaultTreeCellRenderer.setForeground(WvWColors.GREEN);
				defaultTreeCellRenderer.setText(WvWNames.map.get(wvwMap.getType()) + " (" + model.getSides().get(WvWSide.GREEN) + ")");
				break;
			case ETERNAL_BATTLEGROUNDS:
			default:
				defaultTreeCellRenderer.setForeground(Color.BLACK);
				defaultTreeCellRenderer.setText(WvWNames.map.get(wvwMap.getType()));
				break;
			}
		} else if (value instanceof WvWObjective) {
			WvWObjective objective = (WvWObjective) value;
			defaultTreeCellRenderer.setText(WvWNames.objective.get(objective.getObjectiveId()) + " (" + model.getObjectiveNames().get(objective.getObjectiveId()) + ")");

			if (model.getWatchedObjectives().contains(objective.getObjectiveId())) {
				defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_OPEN)));
			} else {
				defaultTreeCellRenderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.EYE_CLOSED)));
			}

			switch (objective.getOwner()) {
			case BLUE:
				defaultTreeCellRenderer.setForeground(WvWColors.BLUE);
				break;
			case RED:
				defaultTreeCellRenderer.setForeground(WvWColors.RED);
				break;
			case GREEN:
				defaultTreeCellRenderer.setForeground(WvWColors.GREEN);
				break;
			default:
				defaultTreeCellRenderer.setForeground(Color.BLACK);
				break;
			}

		}

		if (hasFocus) {
			defaultTreeCellRenderer.setBackground(Color.DARK_GRAY);
			defaultTreeCellRenderer.setOpaque(true);
		}
		
//		defaultTreeCellRenderer.setForeground(Color.BLUE);

		return defaultTreeCellRenderer;
	}

}
