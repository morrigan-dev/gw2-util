package de.morrigan.dev.gw2.client.model;

import de.morrigan.dev.gw2.utils.exceptions.ErrorException;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

public class MainPanelModel extends AbstractModel {

  public static final long ALL_CHANGED = -1L;
  public static final long ITEM_LIST_CHANGED = BitUtil.setLongBit(0);
  public static final long SELECTED_ITEM_CHANGED = BitUtil.setLongBit(1);

  public MainPanelModel() {
    super();
  }

  @Override
  public void initialize() throws ErrorException {
    if (!isChanging()) {
      syncViews(ALL_CHANGED);
    }
  }
}