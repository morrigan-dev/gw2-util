package de.morrigan.dev.gw2.client.gui.admin;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.model.AdminModel;
import de.morrigan.dev.gw2.client.model.UserDTOTableModel;
import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.admin.UserGroupDTO;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.ComponentFactory;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class UserAdminPanel extends JPanel implements IObserver {

	private enum ListenerAction implements IListenerAction {
		USER_SELECTED, UPDATE_ITEMS
	}

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserAdminPanel.class);

	/** Handel auf den LabelManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	private static final int LIST_WIDTH = 250;

	private AdminModel model;

	private JScrollPane scUsers;
	private JTable tblUsers;

	private GW2Label lblFirstName;
	private GW2Label lblLastName;
	private GW2Label lblUsername;
	private GW2Label lblPassword;
	private GW2Label lblPasswordVerify;
	private GW2Label lblUserGroup;
	private GW2Label lblActiveState;
	private GW2Label lblClientDataSet;
	private GW2Label lblCreateDate;
	private GW2Label lblUpdateDate;

	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JPasswordField pfPasswordVerify;
	private JComboBox<UserGroupDTO> cbUserGroup;
	private JComboBox<ActiveState> cbActiveState;
	private JTextArea taClientDataSet;
	private JTextField tfCreateDate;
	private JTextField tfUpdateDate;
	private JButton btUpdateItems;

	private UserDTOTableModel tblUsersModel;

	public UserAdminPanel(AdminModel model) {
		super();
		this.model = model;

		createGUI();
		layoutGUI();
		configureGUI();
		configureListener();
		updateLanguage();

		this.model.addObserver(this);
	}

	public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
		LOG.debug("listenerAction: {}, event: {}", listenerAction, event);

		try {
			final ListenerAction action = (ListenerAction) listenerAction;
			switch (action) {
				case USER_SELECTED:
					final int selectedRow = this.tblUsers.getSelectedRow();
					final UserDTO selectedElement = this.tblUsersModel.getElementAt(selectedRow);
					this.model.setSelectedUserDTO(selectedElement);
				break;
				case UPDATE_ITEMS:
					this.model.updateItems();
				break;
				default:
					LOG.warn("Die Aktion {} ist nicht gemappt!", action);
				break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
		}
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);
		if (obs instanceof AdminModel) {
			final int selectedRow = this.tblUsers.getSelectedRow();
			if (selectedRow != -1) {
				this.tblUsersModel.fireTableRowsUpdated(selectedRow, selectedRow);
			}

			if ((updateFlag & AdminModel.USERS_CHANGED) != 0) {
				this.tblUsersModel.setDataList(this.model.getUsers());
			}
		}
	}

	private void configureGUI() {
		setOpaque(false);

		this.tblUsersModel = new UserDTOTableModel(new ArrayList<>());
		this.tblUsers.setModel(this.tblUsersModel);

		this.scUsers.setViewportView(this.tblUsers);
		this.scUsers.setPreferredSize(new Dimension(LIST_WIDTH, 10));
		this.scUsers.setOpaque(false);
		this.scUsers.getViewport().setOpaque(false);
		this.scUsers.setBorder(null);
		this.scUsers.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

	}

	private void configureListener() {
		this.tblUsers.getSelectionModel().addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) {
				handleListenerEvent(ListenerAction.USER_SELECTED, event);
			}
		});
		this.btUpdateItems.addActionListener(event -> handleListenerEvent(ListenerAction.UPDATE_ITEMS, event));
	}

	private void createGUI() {
		this.scUsers = new JScrollPane();
		this.tblUsers = ComponentFactory.getDefaultTable();

		this.lblFirstName = new GW2Label();
		this.lblLastName = new GW2Label();
		this.lblUsername = new GW2Label();
		this.lblPassword = new GW2Label();
		this.lblPasswordVerify = new GW2Label();
		this.lblUserGroup = new GW2Label();
		this.lblActiveState = new GW2Label();
		this.lblClientDataSet = new GW2Label();
		this.lblCreateDate = new GW2Label();
		this.lblUpdateDate = new GW2Label();

		this.tfFirstName = ComponentFactory.getDefaultJTextField();
		this.tfLastName = ComponentFactory.getDefaultJTextField();
		this.tfUsername = ComponentFactory.getDefaultJTextField();
		this.pfPassword = ComponentFactory.getDefaultJPasswordField();
		this.pfPasswordVerify = ComponentFactory.getDefaultJPasswordField();
		this.cbUserGroup = ComponentFactory.getGW2ComboBox();
		this.cbActiveState = ComponentFactory.getGW2ComboBox();
		this.taClientDataSet = ComponentFactory.getMultiLineLabel();
		this.tfCreateDate = ComponentFactory.getDefaultJTextField();
		this.tfUpdateDate = ComponentFactory.getDefaultJTextField();
		this.btUpdateItems = new JButton();
	}

	private void layoutGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		int y = 0;
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblFirstName, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.tfFirstName, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblLastName, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.tfLastName, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblUsername, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.tfUsername, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblPassword, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.pfPassword, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblPasswordVerify, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.pfPasswordVerify, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblUserGroup, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.cbUserGroup, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblActiveState, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.cbActiveState, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblClientDataSet, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.taClientDataSet, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblCreateDate, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.tfCreateDate, gbc);
		GCUtil.configGC(gbc, 1, y, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
		add(this.lblUpdateDate, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.tfUpdateDate, gbc);
		GCUtil.configGC(gbc, 2, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
		add(this.btUpdateItems, gbc);

		GCUtil.configGC(gbc, 3, y, GCUtil.WEST, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
		add(new JLabel(), gbc);
		GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.VERT, 0.0, 1.0, 1, y, InsetConstants.ALL_INSETS);
		add(this.scUsers, gbc);

	}

	private void updateLanguage() {
		this.lblFirstName.setText(RESOURCE_MANAGER.getLabelWithSeparator("firstName"));
		this.lblLastName.setText(RESOURCE_MANAGER.getLabelWithSeparator("lastName"));
		this.lblUsername.setText(RESOURCE_MANAGER.getLabelWithSeparator("username"));
		this.lblPassword.setText(RESOURCE_MANAGER.getLabelWithSeparator("password"));
		this.lblPasswordVerify.setText(RESOURCE_MANAGER.getLabelWithSeparator("passwordVerify"));
		this.lblUserGroup.setText(RESOURCE_MANAGER.getLabelWithSeparator("userGroup"));
		this.lblActiveState.setText(RESOURCE_MANAGER.getLabelWithSeparator("activeState"));
		this.lblClientDataSet.setText(RESOURCE_MANAGER.getLabelWithSeparator("clientDataSet"));
		this.lblCreateDate.setText(RESOURCE_MANAGER.getLabelWithSeparator("createDate"));
		this.lblUpdateDate.setText(RESOURCE_MANAGER.getLabelWithSeparator("updateDate"));
		this.btUpdateItems.setText(RESOURCE_MANAGER.getLabel("updateItems"));
	}

}
