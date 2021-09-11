package de.morrigan.dev.gw2.client.model;

import java.math.BigDecimal;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.PreferencesModel;
import de.morrigan.dev.gw2.client.gw2.api.GW2APIModel;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.JNDIServiceFactory;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.gw2.utils.exceptions.ErrorException;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

public class MainPanelModel extends AbstractModel {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(MainPanelModel.class);

  public static final long ALL_CHANGED = -1L;
  public static final long MODEL_INITIALIZED = BitUtil.setLongBit(0);
  public static final long ITEM_LIST_CHANGED = BitUtil.setLongBit(1);
  public static final long SELECTED_ITEM_CHANGED = BitUtil.setLongBit(2);

  private GW2APIModel gw2APIModel = GW2APIModel.getInstance();
  private NavigationModel navModel = NavigationModel.getInstance();
  private AuthenticationModel authModel = AuthenticationModel.getInstance();
  private RightsModel rightsModel = RightsModel.getInstance();
  private PreferencesModel prefModel = PreferencesModel.getInstance();

  private IRemoteAuthenticationService authService;

  public MainPanelModel() {
    super();
  }

  @Override
  public void initialize() throws ErrorException {
    try {
      this.authService = JNDIServiceFactory.getInstance().getRemoteAuthenticationService();
    } catch (ServiceException e) {
      LOG.error(e.getMessage(), e);
      authService = createLocalPermissionDeniedAuthService();
    }

    if (!isChanging()) {
      syncViews(MODEL_INITIALIZED);
    }
  }

  public BigDecimal getVersion() {
    return authService.getVersion();
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

  public PreferencesModel getPrefModel() {
    return prefModel;
  }

  private IRemoteAuthenticationService createLocalPermissionDeniedAuthService() {
    return new IRemoteAuthenticationService() {

      @Override
      public void registerNewUser(AuthenticateDTO authDTO, String username, String password) throws ServiceException {
        // nothing to do
      }

      @Override
      public void logout(AuthenticateDTO auth) throws ServiceException {
        // nothing to do
      }

      @Override
      public BigDecimal getVersion() {
        return BigDecimal.ZERO;
      }

      @Override
      public Vector<String> getRightsOfUser(AuthenticateDTO authDTO) throws ServiceException {
        return new Vector<>();
      }

      @Override
      public String echo(String msg) {
        return msg;
      }

      @Override
      public AuthenticateDTO authenticate(AuthenticateDTO auth) throws ServiceException {
        return new AuthenticateDTO();
      }
    };
  }
}