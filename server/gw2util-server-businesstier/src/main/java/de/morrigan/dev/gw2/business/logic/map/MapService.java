package de.morrigan.dev.gw2.business.logic.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.builder.interfaces.IWaypointBuilder;
import de.morrigan.dev.gw2.business.factories.BuilderFactory;
import de.morrigan.dev.gw2.business.factories.ServiceFactory;
import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.local.interfaces.IMapService;
import de.morrigan.dev.gw2.dao.interfaces.IWaypointDAO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.MapInfoDTO;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.map.WaypointDTO;
import de.morrigan.dev.gw2.dto.map.WaypointListWrapperDTO;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteMapService;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.Waypoint;
import de.morrigan.dev.gw2.factory.DBDAOFactory;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.gw2.utils.annotations.RightCheck.Type;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Stateless
@Local(IMapService.class)
@Remote(IRemoteMapService.class)
public class MapService implements IMapService {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(MapService.class);

	@RightCheck(rightKeys = { "editWaypoints" }, type = Type.METHOD)
	@Override
	public WaypointDTO createNewWaypoint(AuthenticateDTO authDTO, WaypointDTO newWaypoint) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		Validate.notNull(newWaypoint, "Der Parameter (newWaypoint) darf nicht null sein!");
		LOG.debug("authDTO: {}, newWaypoint: {}", authDTO, newWaypoint);

		try {
			IWaypointBuilder waypointBuilder = BuilderFactory.getInstance().getWaypointBuilder();
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();

			Waypoint waypointToSave = waypointBuilder.createNewEntity(newWaypoint, executingUser);
			Waypoint savedWaypoint = waypointDAO.save(waypointToSave);
			WaypointDTO updatedWaypointDTO = waypointBuilder.buildDTO(savedWaypoint);
			LOG.info("Neuer Spot erstellt ({}/{}) von {}", savedWaypoint.getWpType(), savedWaypoint.getWpSubType(),
					executingUser.getUserName());
			return updatedWaypointDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@RightCheck(rightKeys = { "editWaypoints" }, type = Type.METHOD)
	@Override
	public void deleteWaypoint(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, idOfWaypoint: {}", authDTO, idOfWaypoint);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();
			Waypoint waypointToDelete = waypointDAO.findById(idOfWaypoint);
			waypointDAO.setAuditInformation(waypointToDelete, executingUser, false);
			waypointDAO.markAsDeleted(waypointToDelete);
			LOG.info("Spot gelöscht ({}/{}) von {}", waypointToDelete.getWpType(), waypointToDelete.getWpSubType(),
					executingUser.getUserName());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@RightCheck(rightKeys = { "deleteWaypointByMapName" }, type = Type.METHOD)
	public void deleteWaypoints(AuthenticateDTO authDTO, String mapName, double fromLatitude, double fromLongitude,
			double toLatitude, double toLongitude) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, mapName: {}, fromLatitude: {}, fromLongitude: {}, toLatitude: {}, toLongitude: {}", authDTO,
				mapName, fromLatitude, fromLongitude, toLatitude, toLongitude);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();
			List<Waypoint> waypointsToDelete = waypointDAO.getWaypoints(fromLatitude, fromLongitude, toLatitude,
					toLongitude);
			for (Waypoint waypoint : waypointsToDelete) {
				if (!waypoint.isPermanent()) {
					waypointDAO.setAuditInformation(waypoint, executingUser, false);
					waypointDAO.markAsDeleted(waypoint);
				}
			}
			LOG.info("{} Spots ({}) gelöscht von {}", waypointsToDelete.size(), mapName, executingUser.getUserName());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@RightCheck(rightKeys = { "getWaypoints" }, type = Type.METHOD)
	@Override
	public WaypointListWrapperDTO getAllAvailableWaypoints(AuthenticateDTO authDTO) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}", authDTO);

		try {
			IWaypointBuilder waypointBuilder = BuilderFactory.getInstance().getWaypointBuilder();
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();

			List<WaypointDTO> resourceWPs = new ArrayList<>();
			// Lade und verarbeite Erz-Wegpunkte, Holz-Wegpunkte, Planzen-Wegpunkte
			WPType[] wpTypes = new WPType[] { WPType.ORE, WPType.WOOD, WPType.PLANT };
			List<Waypoint> resWPs = waypointDAO.getWaypoints(wpTypes, null, ActiveState.ACTIVE);
			for (Waypoint resWP : resWPs) {
				resourceWPs.add(waypointBuilder.buildDTO(resWP));
			}

			List<WaypointDTO> otherWPs = new ArrayList<>();
			// Lade und verarbeite Sonstige-Wegpunkte
			wpTypes = new WPType[] { WPType.OTHER };
			List<Waypoint> oWPs = waypointDAO.getWaypoints(wpTypes, null, ActiveState.ACTIVE);
			for (Waypoint oWP : oWPs) {
				otherWPs.add(waypointBuilder.buildDTO(oWP));
			}

			WaypointListWrapperDTO waypointWrapper = new WaypointListWrapperDTO(resourceWPs, otherWPs);
			LOG.info("{} Spots von {} abgerufen.", resWPs.size() + otherWPs.size(), authDTO.getUserName());
			return waypointWrapper;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@RightCheck(rightKeys = { "getWaypoints" }, type = Type.METHOD)
	public MapInfoDTO getMapInfo(AuthenticateDTO authDTO, String mapName, double fromLatitude, double fromLongitude,
			double toLatitude, double toLongitude) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, mapName: {}, fromLatitude: {}, fromLongitude: {}, toLatitude: {}, toLongitude: {}", authDTO,
				mapName, fromLatitude, fromLongitude, toLatitude, toLongitude);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();
			Map<WPSubType, Integer> resourceAmount = new HashMap<>();
			Date latestChange = null;
			Date nearestChange = null;

			WPSubType[] values = WPSubType.values();
			for (WPSubType wpSubType : values) {
				int amount = waypointDAO.getAmountOf(wpSubType, fromLatitude, fromLongitude, toLatitude, toLongitude);
				resourceAmount.put(wpSubType, amount);
			}

			LOG.info("Karteninformationen ({}) abgerufen von {}", mapName, executingUser.getUserName());
			return new MapInfoDTO(resourceAmount, latestChange, nearestChange);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@RightCheck(rightKeys = { "editWaypoints" }, type = Type.METHOD)
	public void markAsPermanent(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, idOfWaypoint: {}", authDTO, idOfWaypoint);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();
			Waypoint waypointToEdit = waypointDAO.findById(idOfWaypoint);
			waypointToEdit.setPermanent(true);
			waypointDAO.setAuditInformation(waypointToEdit, executingUser, false);
			waypointDAO.save(waypointToEdit);
			if (LOG.isInfoEnabled()) {
				LOG.info("Spot aktualisiert in permanent ({}/{}) von {}", waypointToEdit.getWpType(),
						waypointToEdit.getWpSubType(), executingUser.getUserName());
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@RightCheck(rightKeys = { "editWaypoints" }, type = Type.METHOD)
	public void markAsRich(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, idOfWaypoint: {}", authDTO, idOfWaypoint);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			IWaypointDAO waypointDAO = DBDAOFactory.getInstance().getWaypointDAO();
			Waypoint waypointToEdit = waypointDAO.findById(idOfWaypoint);
			waypointToEdit.setRich(true);
			waypointDAO.setAuditInformation(waypointToEdit, executingUser, false);
			waypointDAO.save(waypointToEdit);
			LOG.info("Spot aktualisiert in reichhaltig ({}/{}) von {}", waypointToEdit.getWpType(),
					waypointToEdit.getWpSubType(), executingUser.getUserName());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
