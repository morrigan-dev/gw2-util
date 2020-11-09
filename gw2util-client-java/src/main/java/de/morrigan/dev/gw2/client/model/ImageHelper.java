package de.morrigan.dev.gw2.client.model;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.resources.ImageManager;

public class ImageHelper {
	
	/** Handel auf den ImageManager */
	private static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

	private static final ImageHelper INSTANCE = new ImageHelper();

	public static final ImageHelper getInstance() {
		return INSTANCE;
	}

	private Map<WPSubType, Image> resOriginByWpSubType = new HashMap<WPSubType, Image>();

	private ImageHelper() {
		super();

		initMaps();
	}

	private void initMaps() {
		this.resOriginByWpSubType.put(WPSubType.ORICHALCUM, IMAGE_MANAGER.getImage(ImageManager.ORICHALCUM_ICON));
		this.resOriginByWpSubType.put(WPSubType.MITHRIL, IMAGE_MANAGER.getImage(ImageManager.MITHRIL_ICON));
		this.resOriginByWpSubType.put(WPSubType.PLATINUM, IMAGE_MANAGER.getImage(ImageManager.PLATINUM_ICON));
		this.resOriginByWpSubType.put(WPSubType.GOLD, IMAGE_MANAGER.getImage(ImageManager.GOLD_ICON));
		this.resOriginByWpSubType.put(WPSubType.SILVER, IMAGE_MANAGER.getImage(ImageManager.SILVER_ICON));
		this.resOriginByWpSubType.put(WPSubType.IRON, IMAGE_MANAGER.getImage(ImageManager.IRON_ICON));
		this.resOriginByWpSubType.put(WPSubType.COPPER, IMAGE_MANAGER.getImage(ImageManager.COPPER_ICON));

	}
}
