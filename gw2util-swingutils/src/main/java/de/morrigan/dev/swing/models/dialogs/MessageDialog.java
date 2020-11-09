package de.morrigan.dev.swing.models.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.constants.ColorConstants;
import de.morrigan.dev.swing.enums.DialogMessage;
import de.morrigan.dev.swing.wrapper.ListenerWrapper;

public abstract class MessageDialog extends ADialog<Object> {

	private static final long serialVersionUID = 1L;
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(MessageDialog.class);
	
	private static final int WIDTH_ADDITION = 150;
	private static final int HEIGHT_ADDITION = 200;
	private static final Dimension LINE_SEPARATOR_SIZE = new Dimension(1, 1);
	private static final Dimension DETAIL_MESSAGE_MINIMUM_SIZE = new Dimension(200, 50);
	private static final Dimension DETAIL_MESSAGE_MAXIMUM_SIZE = new Dimension(500, 200);

	/** Insets mit oberem Abstand */
	public static final Insets ICON_INSETS = new Insets(15, 15, 15, 15);

	private JLabel lblIcon;
	private JLabel lblErrorMsg;
	private JLabel taDetailMessage;
	private JScrollPane scDetailMessage;
	private JPanel pnlButtons;
	protected JButton btnOk;
	protected JButton btnYes;
	protected JButton btnNo;
	protected JButton btnCancel;

	/** Model des Dialogs */
	private MessageDialogModel dialogModel;

	public MessageDialog(final Window window, final MessageDialogModel dialogModel,
			final ListenerWrapper<Object> listenerWrapper) {
		super(window, "", dialogModel, listenerWrapper);
	}

	@Override
	public void update(final IObservable obs, final long updateFlag) {}

	@Override
	protected void configureGUI(final IObservable model) {
		this.dialogModel = (MessageDialogModel) model;
		this.dialogModel.addObserver(this);
		this.taDetailMessage.setCursor(null);
		this.taDetailMessage.setOpaque(false);
		this.taDetailMessage.setFont(UIManager.getFont("Label.font"));

		setResizable(false);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		updateLanguage();
	}

	@Override
	protected void configureListener(final Object listener) {
		final IDefaultDialogResult dialogListener = (IDefaultDialogResult) listener;

		this.btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (dialogListener != null) {
					dialogListener.actionPerformed(DialogMessage.OK);
				}
				dispose();
			}
		});
		this.btnYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (dialogListener != null) {
					dialogListener.actionPerformed(DialogMessage.YES);
				}
				dispose();
			}
		});
		this.btnNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (dialogListener != null) {
					dialogListener.actionPerformed(DialogMessage.NO);
				}
				dispose();
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (dialogListener != null) {
					dialogListener.actionPerformed(DialogMessage.CANCEL);
				}
				dispose();
			}
		});

		addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(final WindowEvent arg0) {
				setAlwaysOnTop(true);
			}
		});
	}

	@Override
	protected void createGUI(final JPanel contentPanel) {
		this.lblIcon = new JLabel();
		this.lblErrorMsg = new JLabel();

		this.pnlButtons = new JPanel();
		this.pnlButtons.setLayout(new GridLayout(1, 3));

		this.btnOk = new JButton();
		this.btnYes = new JButton();
		this.btnNo = new JButton();
		this.btnCancel = new JButton();

		this.taDetailMessage = new JLabel();
	}

	protected abstract void layoutButtonPanel(JPanel buttonPanel);

	@Override
	protected void layoutGUI(final JPanel contentPanel) {
		contentPanel.setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();

		final JPanel lineSeparator = new JPanel();
		lineSeparator.setBackground(ColorConstants.BORDER_COLOR);
		lineSeparator.setPreferredSize(LINE_SEPARATOR_SIZE);

		this.scDetailMessage = new JScrollPane();
		this.scDetailMessage.setViewportView(this.taDetailMessage);
		this.scDetailMessage.setBorder(null);

		GCUtil.configGC(gbc, 0, 0, GCUtil.NORTHWEST, GCUtil.NONE, 0.0, 0.0, 1, 2, ICON_INSETS);
		contentPanel.add(this.lblIcon, gbc);
		GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		contentPanel.add(this.lblErrorMsg, gbc);
		GCUtil.configGC(gbc, 1, 1, GCUtil.NORTH, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.LTR_INSETS);
		contentPanel.add(this.scDetailMessage, gbc);
		// Linie
		GCUtil.configGC(gbc, 0, 3, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 2, 1, InsetConstants.TOP_INSETS);
		contentPanel.add(lineSeparator, gbc);
		// Buttonpanel
		GCUtil.configGC(gbc, 0, 4, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 2, 1, InsetConstants.ALL_INSETS);
		contentPanel.add(this.pnlButtons, gbc);

		layoutButtonPanel(this.pnlButtons);
	}

	private void updateComponents() {
		final Dimension prefDetail = this.taDetailMessage.getPreferredSize();
		final Dimension prefMessage = this.lblErrorMsg.getPreferredSize();

		int width = Math.max(prefDetail.width, prefMessage.width);
		int height = Math.max(prefDetail.height, prefMessage.height);
		if (width > DETAIL_MESSAGE_MAXIMUM_SIZE.width) {
			width = DETAIL_MESSAGE_MAXIMUM_SIZE.width;
		} else {
			if (width < DETAIL_MESSAGE_MINIMUM_SIZE.width) {
				width = DETAIL_MESSAGE_MINIMUM_SIZE.width;
			}

		}
		if (height > DETAIL_MESSAGE_MAXIMUM_SIZE.height) {
			height = DETAIL_MESSAGE_MAXIMUM_SIZE.height;
		} else {
			if (height < DETAIL_MESSAGE_MINIMUM_SIZE.height) {
				height = DETAIL_MESSAGE_MINIMUM_SIZE.height;
			}

		}

		// this.setSize(width + WIDTH_ADDITION, height + HEIGHT_ADDITION);
		final int newWidth = width + WIDTH_ADDITION;
		final int newHeight = height + HEIGHT_ADDITION;
		final Rectangle bounds = this.getBounds();

		bounds.x = bounds.x - ((newWidth - bounds.width) / 2);
		bounds.y = bounds.y - ((newHeight - bounds.height) / 2);
		if (bounds.x < 0) {
			bounds.x = 0;
		}
		if (bounds.y < 0) {
			bounds.y = 0;
		}
		bounds.height = newHeight;
		bounds.width = newWidth;
		this.setBounds(bounds);
	}

	private void updateLanguage() {
		setTitle(this.dialogModel.getTitle());
		setHeaderText(this.dialogModel.getDialogHeaderText());
		this.lblIcon.setIcon(this.dialogModel.getIcon());
		this.lblErrorMsg.setText(this.dialogModel.getErrorMsg());
		String detailMessage = this.dialogModel.getDetailMsg();
		if (!detailMessage.toLowerCase().contains("<html>")) {
			detailMessage = "<html>" + detailMessage + "</html>";
			final BufferedReader reader = new BufferedReader(new StringReader(detailMessage));
			String line = null;
			String newMsg = "";
			try {
				while ((line = reader.readLine()) != null) {
					newMsg += line + "<br>";
				}
			} catch (final IOException e) {
				LOG.error(e.getMessage(), e);
			}
			detailMessage = newMsg;
		}
		this.taDetailMessage.setText(detailMessage);
		this.btnOk.setText(RESOURCE_MANAGER.getButtonLabel("ok"));
		this.btnYes.setText(RESOURCE_MANAGER.getButtonLabel("yes"));
		this.btnNo.setText(RESOURCE_MANAGER.getButtonLabel("no"));
		this.btnCancel.setText(RESOURCE_MANAGER.getButtonLabel("cancel"));
		updateComponents();
	}
}
