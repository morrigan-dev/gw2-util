package de.morrigan.dev.gw2.client.gui.cards;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.EventObject;

import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXMapViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.AbstractView;
import de.morrigan.dev.gw2.client.gui.components.FilterPanel;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.map.GW2MapPanel;
import de.morrigan.dev.gw2.client.model.DynamicMapModel;
import de.morrigan.dev.gw2.client.model.RightsModel;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class DynamicMapCard extends AbstractView<DynamicMapModel> {

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(DynamicMapCard.class);

	private FilterPanel pnlFilter;
	private RightsModel rightsModel = RightsModel.getInstance();

	private GW2MapPanel pnlMap;
	private boolean normalMode;

	public DynamicMapCard(final Window mainWindow, final boolean normalMode) {
		super(mainWindow);

		try {

			this.normalMode = normalMode;
			this.model = new DynamicMapModel();

			createGUI();
			configureGUI();
			layoutGUI();
			configureListener();
			updateLanguage();

			this.rightsModel.updateViewByRights(this);

			this.model.addObserver(this);
			this.pnlMap.getModel().addObserver(this);
			this.rightsModel.addObserver(this);

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(getMainWindow(), e, null);
		}
	}

	@Override
	public void configureGUI() {
		this.pnlMap.setBorder(new LineBorder(Color.BLACK));
		setOpaque(false);
	}

	@Override
	public void configureListener() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentHidden(ComponentEvent event) {
				DynamicMapCard.this.pnlMap.getModel().setRunUpdater(false);
			}

			@Override
			public void componentShown(ComponentEvent event) {
				DynamicMapCard.this.pnlMap.getModel().setRunUpdater(true);
			}
		});
	}

	@Override
	public void createGUI() {
		this.pnlMap = new GW2MapPanel();
		this.pnlFilter = new FilterPanel();
	}

	public JXMapViewer getMapViewer() {
		return this.pnlMap.getMapViewer();
	}

	@Override
	public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
		// keine Listener vorhanden, die ein Event delegieren
	}

	public void initialize() throws AbstractException {
		this.pnlMap.initialize();
		this.model.initialize();
	}

	@Override
	public void layoutGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		if (this.normalMode) {
			GCUtil.configGC(gbc, 0, 0, GCUtil.NORTH, GCUtil.HORI, 1.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
			add(this.pnlFilter, gbc);
			GCUtil.configGC(gbc, 0, 1, GCUtil.NORTH, GCUtil.BOTH, 1.0, 1.0, 3, 1, InsetConstants.LTR_INSETS);
			add(this.pnlMap, gbc);
		} else {
			GCUtil.configGC(gbc, 0, 0, GCUtil.NORTH, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
			add(this.pnlMap, gbc);
		}
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);

		if (obs instanceof RightsModel) {
			if ((updateFlag & RightsModel.RIGHTS_CHANGED) != 0) {
				this.rightsModel.updateViewByRights(this);
			}
		}
	}

	@Override
	public void updateLanguage() {
		// keine GUI Elemente vorhanden die eine Beschriftung besitzen
	}
}
