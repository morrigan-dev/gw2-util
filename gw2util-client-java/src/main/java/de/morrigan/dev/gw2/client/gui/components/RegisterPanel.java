package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.client.model.RegisterModel;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.ComponentFactory;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class RegisterPanel extends JPanel implements IStructuredView, IObserver {

	private static enum ListenerAction implements IListenerAction {
		REGISTER_CLICKED
	}

	private static final long serialVersionUID = 1L;

	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(RegisterPanel.class);
	
	/** Handel auf den LabelManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	private GW2Label lblUsername;
	private JTextField tfUsername;
	private GW2Label lblPassword;
	private JPasswordField pfPassword;
	private JButton btRegister;

	private RegisterModel model;

	public RegisterPanel() {
		super();

		createGUI();
		layoutGUI();
		configureGUI();
		configureListener();
		updateLanguage();

		this.model = new RegisterModel();
		this.model.addObserver(this);
	}

	@Override
	public void configureGUI() {
		setOpaque(false);
		this.btRegister.setOpaque(false);
		this.btRegister.setBackground(new Color(0, 0, 0, 0));

		this.tfUsername.setBackground(new Color(29, 28, 24, 150));
		this.tfUsername.setForeground(new Color(222, 222, 222));
		this.tfUsername.setBorder(new LineBorder(Color.BLACK, 2));
		this.pfPassword.setForeground(new Color(222, 222, 222));
		this.pfPassword.setBackground(new Color(29, 28, 24, 150));
		this.pfPassword.setBorder(new LineBorder(Color.BLACK, 2));
	}

	@Override
	public void configureListener() {
		this.btRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				handleListenerEvent(ListenerAction.REGISTER_CLICKED, event);
			}
		});

	}

	@Override
	public void createGUI() {
		this.lblUsername = new GW2Label();
		this.tfUsername = ComponentFactory.getDefaultJTextField();
		this.lblPassword = new GW2Label();
		this.pfPassword = ComponentFactory.getDefaultJPasswordField();
		this.btRegister = new JButton();
	}

	@Override
	public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
		LOG.debug("listenerAction: {}, event: {}", listenerAction, event);
		try {
			final ListenerAction action = (ListenerAction) listenerAction;
			switch (action) {
				case REGISTER_CLICKED:
					doRegister();
					break;

				default:
					LOG.warn("Die Aktion " + action + " ist nicht gemappt!");
					break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
		}
	}

	@Override
	public void layoutGUI() {

		setLayout(new GridBagLayout());

		final GridBagConstraints gbc = new GridBagConstraints();

		GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
		add(this.lblUsername, gbc);
		GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.tfUsername, gbc);
		GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		add(this.lblPassword, gbc);
		GCUtil.configGC(gbc, 1, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.pfPassword, gbc);
		GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
		add(this.btRegister, gbc);
		GCUtil.configGC(gbc, 2, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 3, InsetConstants.NO_INSETS);
		add(new JLabel(), gbc);
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLanguage() {
		this.lblUsername.setText(RESOURCE_MANAGER.getLabelWithSeparator("username"));
		this.lblPassword.setText(RESOURCE_MANAGER.getLabelWithSeparator("password"));
		this.btRegister.setText(RESOURCE_MANAGER.getButtonLabel("register"));
	}

	private void doRegister() throws ServiceException {
		String username = this.tfUsername.getText();
		String password = new String(this.pfPassword.getPassword());
		this.model.register(username, password);
	}

}
