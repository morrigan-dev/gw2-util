package de.morrigan.dev.gw2.client.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.admin.UserDetailDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.JNDIServiceFactory;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteUserAdminService;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

public class AdminModel extends AbstractModel {

  private enum Field {
    ACTIVE_STATE, FIRST_NAME, LAST_NAME, USER_NAME, CREATE_DATE, UPDATE_DATE, USER_GROUP, CLIENT_DATA;
  }

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(AdminModel.class);

  public static final long USERS_CHANGED = BitUtil.setLongBit(0);

  public static final long SELECTED_USER_CHANGED = BitUtil.setLongBit(1);
  public static final long ACTIVE_STATE_CHANGED = BitUtil.setLongBit(2);
  public static final long FIRST_NAME_CHANGED = BitUtil.setLongBit(3);
  public static final long LAST_NAME_CHANGED = BitUtil.setLongBit(4);
  public static final long USER_NAME_CHANGED = BitUtil.setLongBit(5);
  public static final long USER_GROUP_CHANGED = de.morrigan.dev.utils.BitUtil.setLongBit(6);

  private AuthenticationModel authModel = AuthenticationModel.getInstance();
  private List<UserDTO> users = new ArrayList<>();
  private UserDTO selectedUserDTO;

  private UserDetailDTO selectedUserDetailDTO;

  private IRemoteUserAdminService userAdminService;

  public AdminModel() {
    super();
  }

  public List<UserDTO> getUsers() {
    return this.users;
  }

  @Override
  public void initialize() throws AbstractException {
    long syncFlag = 0;

    this.userAdminService = JNDIServiceFactory.getInstance().getRemoteUserAdminService();

    this.users = this.userAdminService.getUserForAdministration(this.authModel.getAuthDTO());
    syncFlag |= USERS_CHANGED;

    if (!isChanging()) {
      syncViews(syncFlag);
    }
  }

  public void setSelectedUserDTO(UserDTO selectedUserDTO) {
    LOG.debug("selectedUserDTO: {}", selectedUserDTO);
    if (checkValueChanged(this.selectedUserDTO, selectedUserDTO)) {
      long syncFlag = 0;

      this.selectedUserDTO = selectedUserDTO;
      syncFlag |= SELECTED_USER_CHANGED;

      syncFlag |= loadUserDetails(selectedUserDTO);

      if (!isChanging()) {
        syncViews(syncFlag);
      }
    }
  }

  public void updateItems() throws ServiceException {
    this.userAdminService.updateItems(this.authModel.getAuthDTO());
  }

  @SuppressWarnings("unchecked")
  private <T> T getUserDetailDTOValue(Field field, T valueIfNull) {
    LOG.debug("field: {}, valueIfNull: {}", field, valueIfNull);
    T result = valueIfNull;
    try {
      if (this.selectedUserDetailDTO != null) {
        switch (field) {
          case ACTIVE_STATE:
            result = (T) this.selectedUserDetailDTO.getActiveState();
          break;
          case FIRST_NAME:
            result = (T) this.selectedUserDetailDTO.getFirstName();
          break;
          case LAST_NAME:
            result = (T) this.selectedUserDetailDTO.getLastName();
          break;
          case USER_NAME:
            result = (T) this.selectedUserDetailDTO.getUserName();
          break;
          case CREATE_DATE:
            result = (T) this.selectedUserDetailDTO.getCreateDate();
          break;
          case UPDATE_DATE:
            result = (T) this.selectedUserDetailDTO.getUpdateDate();
          break;
          case USER_GROUP:
            result = (T) this.selectedUserDetailDTO.getUserGroup();
          break;
          case CLIENT_DATA:
            result = (T) this.selectedUserDetailDTO.getClientData();
          break;

          default:
            LOG.warn("Für das Field " + field + " fehlt das Mapping!");
          break;
        }
      }
    } catch (ClassCastException e) {
      LOG.error(e.getMessage(), e);
    }
    return result;
  }

  private long loadUserDetails(UserDTO selectedUserDTO) {
    LOG.debug("selectedUserDTO: {}", selectedUserDTO);

    long syncFlag = 0;

    return syncFlag;
  }
}
