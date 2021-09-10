package de.morrigan.dev.swing.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.constants.ColorConstants;
import de.morrigan.dev.swing.factories.ComponentFactory;
import de.morrigan.dev.swing.interfaces.IChoosableDTO;
import de.morrigan.dev.swing.models.dialogs.ZweiListenAuswahlModel;

public class ZweiListenDialog<T extends IChoosableDTO<T>> extends ADialog {

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ZweiListenDialog.class);

	private static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(600, 500);

	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(50, 35);

	// Labels
	private JLabel lblAvailableElements;
	private JLabel lblSelectedElements;
	// Buttons
	private JButton btnMoveAllLeft;
	private JButton btnMoveLeft;
	private JButton btnMoveAllRight;
	private JButton btnMoveRight;
	private JButton btnCancel;
	private JButton btnOk;
	// Lists
	private JTable lstAvailableElements;
	private JTable lstSelectedElements;

	// Panels
	private JPanel pnlMoveButtons;
	private JPanel pnlDialogButtons;

	/** Model für diesen Dialog */
	private ZweiListenAuswahlModel<T> dialogModel;

	public ZweiListenDialog(final Window window, final ZweiListenAuswahlModel<T> dialogModel) {
		super(window, RESOURCE_MANAGER.getLabel("choose"), dialogModel, null);
	}

	public void ok() {
		this.dialogModel.notifyCallBackModel();
		dispose();
	}

	@Override
	public void update(final IObservable obs, final long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);

		if (obs instanceof ResourceManager) {
			updateLanguage();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configureGUI(final IObservable model) {
		initializeListeners();
		this.dialogModel = (ZweiListenAuswahlModel<T>) model;
		this.dialogModel.addObserver(this);
		this.setSize(DEFAULT_DIALOG_SIZE);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent arg0) {
				cancel();
			}

		});
		this.lstAvailableElements.setModel(this.dialogModel.getAvailableElementsModel());
		this.lstSelectedElements.setModel(this.dialogModel.getSelectedElementsModel());

		this.btnMoveAllLeft.setPreferredSize(DEFAULT_BUTTON_SIZE);
		this.btnMoveLeft.setPreferredSize(DEFAULT_BUTTON_SIZE);
		this.btnMoveRight.setPreferredSize(DEFAULT_BUTTON_SIZE);
		this.btnMoveAllRight.setPreferredSize(DEFAULT_BUTTON_SIZE);
		updateLanguage();
	}

	@Override
	protected void configureListener(final Object listener) {
		// es werden keine Listener benötigt
	}

	@Override
	protected void createGUI(final JPanel contentPanel) {
		this.lblAvailableElements = new JLabel();
		this.lblSelectedElements = new JLabel();

		this.pnlMoveButtons = new JPanel();
		this.pnlMoveButtons.setLayout(new GridBagLayout());
		this.pnlDialogButtons = new JPanel();
		this.pnlDialogButtons.setLayout(new GridLayout(0, 4));

		// Movebuttons
		this.btnMoveAllLeft = new JButton();
		this.btnMoveLeft = new JButton();
		this.btnMoveRight = new JButton();
		this.btnMoveAllRight = new JButton();

		// Dialog
		this.pnlDialogButtons.add(new JLabel());
		this.pnlDialogButtons.add(new JLabel());
		this.btnOk = new JButton();
		this.pnlDialogButtons.add(this.btnOk);
		this.btnCancel = new JButton();
		this.pnlDialogButtons.add(this.btnCancel);

		this.lstAvailableElements = ComponentFactory.getDefaultTable();
		this.lstSelectedElements = ComponentFactory.getDefaultTable();

	}

	@Override
	protected void layoutGUI(final JPanel contentPanel) {
		contentPanel.setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();

		// Movebuttons
		GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.VERT, 0.0, 0.5, 1, 1, InsetConstants.NO_INSETS);
		this.pnlMoveButtons.add(new JLabel(), gbc);
		GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		this.pnlMoveButtons.add(this.btnMoveAllLeft, gbc);
		GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		this.pnlMoveButtons.add(this.btnMoveLeft, gbc);
		GCUtil.configGC(gbc, 0, 3, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		this.pnlMoveButtons.add(this.btnMoveRight, gbc);
		GCUtil.configGC(gbc, 0, 4, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		this.pnlMoveButtons.add(this.btnMoveAllRight, gbc);
		GCUtil.configGC(gbc, 0, 5, GCUtil.WEST, GCUtil.VERT, 0.0, 0.5, 1, 1, InsetConstants.TOP_INSETS);
		this.pnlMoveButtons.add(new JLabel(), gbc);

		// Dialog
		final JPanel lineSeparator = new JPanel();
		lineSeparator.setBackground(ColorConstants.BORDER_COLOR);
		lineSeparator.setPreferredSize(new Dimension(1, 1));

		final JScrollPane scAvailable = new JScrollPane();
		scAvailable.setViewportView(this.lstAvailableElements);
		scAvailable.setPreferredSize(new Dimension(10, 10));

		final JScrollPane scSelected = new JScrollPane();
		scSelected.setViewportView(this.lstSelectedElements);
		scSelected.setPreferredSize(new Dimension(10, 10));

		// Verfügbar
		GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 1.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		contentPanel.add(this.lblAvailableElements, gbc);
		GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.BOTH, 0.0, 1.0, 1, 1, InsetConstants.LT_INSETS);
		contentPanel.add(scAvailable, gbc);
		// Movebuttons
		GCUtil.configGC(gbc, 1, 2, GCUtil.WEST, GCUtil.VERT, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		contentPanel.add(this.pnlMoveButtons, gbc);
		// Selektiert
		GCUtil.configGC(gbc, 2, 1, GCUtil.WEST, GCUtil.NONE, 1.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		contentPanel.add(this.lblSelectedElements, gbc);
		GCUtil.configGC(gbc, 2, 2, GCUtil.WEST, GCUtil.BOTH, 0.0, 1.0, 1, 1, InsetConstants.LTR_INSETS);
		contentPanel.add(scSelected, gbc);

		// Linie
		GCUtil.configGC(gbc, 0, 3, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 3, 1, InsetConstants.TOP_INSETS);
		contentPanel.add(lineSeparator, gbc);
		// Buttonpanel
		GCUtil.configGC(gbc, 0, 4, GCUtil.EAST, GCUtil.HORI, 1.0, 0.0, 3, 1, InsetConstants.ALL_INSETS);
		contentPanel.add(this.pnlDialogButtons, gbc);
	}

	private void cancel() {
		this.dialogModel.revertEntries();
		this.dialogModel.notifyCallBackModel();
		dispose();
	}

	private void initializeListeners() {
		this.lstAvailableElements.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					final int[] indices = ZweiListenDialog.this.lstAvailableElements.getSelectedRows();
					ZweiListenDialog.this.dialogModel.addIndices(indices);
				}
			}
		});
		this.lstSelectedElements.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					final int[] indices = ZweiListenDialog.this.lstSelectedElements.getSelectedRows();
					ZweiListenDialog.this.dialogModel.removeIndices(indices);
				}
			}
		});
		this.btnMoveRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final int[] indices = ZweiListenDialog.this.lstAvailableElements.getSelectedRows();
				ZweiListenDialog.this.dialogModel.addIndices(indices);
			}
		});
		this.btnMoveAllRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				ZweiListenDialog.this.dialogModel.addAll();
			}
		});

		this.btnMoveLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final int[] indices = ZweiListenDialog.this.lstSelectedElements.getSelectedRows();
				ZweiListenDialog.this.dialogModel.removeIndices(indices);
			}
		});

		this.btnMoveAllLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				ZweiListenDialog.this.dialogModel.removeAll();
			}
		});

		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				cancel();
			}
		});

		this.btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				ok();
			}
		});
	}

	private void updateLanguage() {
		this.lblAvailableElements.setText(RESOURCE_MANAGER.getLabel("availableElements"));
		this.lblSelectedElements.setText(RESOURCE_MANAGER.getLabel("selectedElements"));

		this.btnOk.setText(RESOURCE_MANAGER.getButtonLabel("ok"));
		this.btnCancel.setText(RESOURCE_MANAGER.getButtonLabel("cancel"));
		this.btnMoveAllLeft.setText(RESOURCE_MANAGER.getButtonLabel("moveAllLeft"));
		this.btnMoveLeft.setText(RESOURCE_MANAGER.getButtonLabel("moveLeft"));
		this.btnMoveAllRight.setText(RESOURCE_MANAGER.getButtonLabel("moveAllRight"));
		this.btnMoveRight.setText(RESOURCE_MANAGER.getButtonLabel("moveRight"));

		this.setTitle(RESOURCE_MANAGER.getLabel("choose"));
		this.setHeaderText(RESOURCE_MANAGER.getLabel("rightChoice"));
		this.setDescriptionText(RESOURCE_MANAGER.getMessage("pleaseChooseRights"));
	}
}
