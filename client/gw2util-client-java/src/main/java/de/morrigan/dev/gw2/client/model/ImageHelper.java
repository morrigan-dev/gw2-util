package de.morrigan.dev.gw2.client.model;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.resources.ImageConstants;

public class ImageHelper {

	/** Handel auf den ImageManager */
	private static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

	private static final ImageHelper INSTANCE = new ImageHelper();

	public static final ImageHelper getInstance() {
		return INSTANCE;
	}

	private Map<WPSubType, Image> resOriginByWpSubType = new HashMap<>();

	private ImageHelper() {
		super();

		initMaps();
	}

	private void initMaps() {
		this.resOriginByWpSubType.put(WPSubType.ORICHALCUM, IMAGE_MANAGER.getImage(ImageConstants.ORICHALCUM_ICON));
		this.resOriginByWpSubType.put(WPSubType.MITHRIL, IMAGE_MANAGER.getImage(ImageConstants.MITHRIL_ICON));
		this.resOriginByWpSubType.put(WPSubType.PLATINUM, IMAGE_MANAGER.getImage(ImageConstants.PLATINUM_ICON));
		this.resOriginByWpSubType.put(WPSubType.GOLD, IMAGE_MANAGER.getImage(ImageConstants.GOLD_ICON));
		this.resOriginByWpSubType.put(WPSubType.SILVER, IMAGE_MANAGER.getImage(ImageConstants.SILVER_ICON));
		this.resOriginByWpSubType.put(WPSubType.IRON, IMAGE_MANAGER.getImage(ImageConstants.IRON_ICON));
		this.resOriginByWpSubType.put(WPSubType.COPPER, IMAGE_MANAGER.getImage(ImageConstants.COPPER_ICON));

	}
}
