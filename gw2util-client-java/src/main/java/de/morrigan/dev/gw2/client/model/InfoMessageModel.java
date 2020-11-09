package de.morrigan.dev.gw2.client.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.utils.BitUtils;
import de.morrigan.dev.swing.models.AbstractModel;

public final class InfoMessageModel extends AbstractModel {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(InfoMessageModel.class);

	private static final InfoMessageModel INSTANCE = new InfoMessageModel();

	public static final long INFO_CHANGED = BitUtils.setLongBit(0);
	public static final long ALPHA_CHANGED = BitUtils.setLongBit(1);

	public static InfoMessageModel getInstance() {
		return INSTANCE;
	}

	private String message;
	private int alpha;
	private boolean isRunning;

	private Runnable timer = () -> {
		InfoMessageModel.this.isRunning = true;
		try {
			Thread.sleep(5000);
			InfoMessageModel.this.alpha = 255;
			while (InfoMessageModel.this.alpha > 0) {
				InfoMessageModel.this.alpha -= 5;
				if (InfoMessageModel.this.alpha < 0) {
					InfoMessageModel.this.alpha = 0;
				}
				syncViews(ALPHA_CHANGED);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {}

		long syncFlag = 0;
		InfoMessageModel.this.message = "";
		syncFlag |= INFO_CHANGED;
		InfoMessageModel.this.alpha = 255;
		syncFlag |= ALPHA_CHANGED;
		syncViews(syncFlag);

		InfoMessageModel.this.isRunning = false;
	};

	private InfoMessageModel() {
		super();
	}

	public int getAlpha() {
		return this.alpha;
	}

	public String getMessage() {
		return ((this.message == null) || this.message.isEmpty()) ? " " : this.message;
	}

	public void setMessage(String message) {
		LOG.debug("message: {}", message);
		if (checkValueChanged(this.message, message)) {
			this.message = message;
			if (!isChanging()) {
				syncViews(INFO_CHANGED);
			}
		}
	}

	public void startTimer() {
		if (!this.isRunning) {
			new Thread(this.timer).start();
		}
	}
}
