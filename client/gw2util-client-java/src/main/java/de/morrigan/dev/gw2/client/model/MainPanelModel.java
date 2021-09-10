package de.morrigan.dev.gw2.client.model;

import de.morrigan.dev.gw2.client.gw2.api.GW2APIModel;
import de.morrigan.dev.gw2.utils.exceptions.ErrorException;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

public class MainPanelModel extends AbstractModel {

  public static final long ALL_CHANGED = -1L;
  public static final long ITEM_LIST_CHANGED = BitUtil.setLongBit(0);
  public static final long SELECTED_ITEM_CHANGED = BitUtil.setLongBit(1);

  private GW2APIModel gw2APIModel = GW2APIModel.getInstance();
  private NavigationModel navModel = NavigationModel.getInstance();
  private AuthenticationModel authModel = AuthenticationModel.getInstance();
  private RightsModel rightsModel = RightsModel.getInstance();

  public MainPanelModel() {
    super();
  }

  @Override
  public void initialize() throws ErrorException {
    if (!isChanging()) {
      syncViews(ALL_CHANGED);
    }
  }

  public GW2APIModel getGw2APIModel() {
    return gw2APIModel;
  }

  public NavigationModel getNavModel() {
    return navModel;
  }

  public AuthenticationModel getAuthModel() {
    return authModel;
  }

  public RightsModel getRightsModel() {
    return rightsModel;
  }

}