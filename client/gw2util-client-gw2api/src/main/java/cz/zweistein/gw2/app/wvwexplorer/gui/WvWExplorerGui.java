package cz.zweistein.gw2.app.wvwexplorer.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import cz.zweistein.gw2.app.wvwexplorer.WvWExplorerModel;
import cz.zweistein.gw2.app.wvwexplorer.componentmodel.WorldNamesComboBoxModel;
import cz.zweistein.gw2.app.wvwexplorer.componentmodel.WvWTreeModel;
import cz.zweistein.gw2.app.wvwexplorer.constants.Icons;
import cz.zweistein.gw2.app.wvwexplorer.constants.WvWColors;

public class WvWExplorerGui extends JFrame {
	private static final long serialVersionUID = 9074400434362358559L;

	private WvWExplorerModel model;
	private JLabel status;
	private JList logList;
	private DefaultListModel logListModel;

	private JTree wvwTree;

	public WvWExplorerGui(WvWExplorerModel model) {
		super("WvW Explorer");

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(Icons.EYE_HELP));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.model = model;

		this.add(getMainPanel());

		setAlwaysOnTop(true);
		pack();
		setVisible(true);

		model.setGui(this);

		try {
			model.init();
		} catch (RemoteException e) {
			setStatusError(e);
		}

	}

	private Component getMainPanel() {
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());

		this.status = new JLabel();
		this.status.setOpaque(true);
		content.add(this.status, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		JComboBox worldSelection = new JComboBox(new WorldNamesComboBoxModel(model));
		content.add(worldSelection, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		WvWTreeModel wvwTreeModel = new WvWTreeModel(model);
		this.wvwTree = new JTree(wvwTreeModel);
		this.wvwTree.setCellRenderer(new WvWTreeCellRenderer(model));
		this.wvwTree.addMouseListener(new WvWTreeMouseAdapter(model));

		this.logListModel = new DefaultListModel();
		this.logList = new JList(logListModel);

		content.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(this.wvwTree), new JScrollPane(logList)), new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		return content;
	}

	public void setStatusUpdating() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				status.setText("Updating");
				status.setForeground(WvWColors.BLUE);
				status.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.STATS_PIE_CHART_REFRESH)));
			}
		});

	}

	public void setStatusInitializing() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				status.setText("Initializing");
				status.setForeground(WvWColors.GREEN);
				status.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.GEAR_FREFRESH)));
			}
		});
	}

	public void setStatusIdle() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				status.setText("Idle");
				status.setForeground(Color.BLACK);
				status.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.CHECKMARK_REFRESH)));
			}
		});
	}

	public void setStatusError(final Exception e) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				status.setText("<html><b>Error</b>: " + e.getMessage() + "</html>");
				status.setForeground(WvWColors.RED);
				status.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Icons.SMILEY_SAD_INFO)));
			}
		});
	}

	public void tickStatus() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				status.setText(status.getText());
			}
		});
	}

	public void updateLog(final String text) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				logListModel.addElement(text);
				logList.setSelectedIndex(logList.getModel().getSize() - 1);
				logList.ensureIndexIsVisible(logList.getModel().getSize() - 1);
			}
		});
	}

	public void updateTitle() {
		setTitle("WvW Explorer " + model.getSides().get(model.getHomeSide()) + " (" + model.getHomeSide().toString().toLowerCase() + ")");
	}

}
