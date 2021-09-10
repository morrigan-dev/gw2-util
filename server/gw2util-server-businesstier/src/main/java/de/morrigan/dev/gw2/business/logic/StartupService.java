package de.morrigan.dev.gw2.business.logic;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.factories.ServiceFactory;
import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.logic.cronjobs.CronJobListener;
import de.morrigan.dev.gw2.business.logic.cronjobs.GemsStatisticCronJob;
import de.morrigan.dev.gw2.business.logic.cronjobs.ItemsCronJob;
import de.morrigan.dev.gw2.business.logic.cronjobs.RecipesCronJob;
import de.morrigan.dev.gw2.business.utils.UserGroupRightCache;
import de.morrigan.dev.gw2.dao.interfaces.IRightDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.entity.Right;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.UserGroup;
import de.morrigan.dev.gw2.factory.DBDAOFactory;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Startup
@Singleton
public class StartupService {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(StartupService.class);

	@PostConstruct
	void init() {
		startServices();
	}

	private void initGemsStatisticJob() throws SchedulerException {
		JobKey jobKey = new JobKey(GemsStatisticCronJob.JOB_NAME, GemsStatisticCronJob.JOB_GROUP);
		JobDetail job = JobBuilder.newJob(GemsStatisticCronJob.class).withIdentity(jobKey).build();

		TriggerKey triggerKey = new TriggerKey(GemsStatisticCronJob.TRIGGER_NAME, GemsStatisticCronJob.TRIGGER_GROUP);
		String triggerValue = GemsStatisticCronJob.TRIGGER_VALUE;
		Trigger trigger = TriggerBuilder.newTrigger() //
				.withIdentity(triggerKey) //
				.withSchedule(CronScheduleBuilder.cronSchedule(triggerValue)) //
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.getListenerManager().addJobListener(new CronJobListener(), KeyMatcher.keyEquals(jobKey));
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private void initItemsJob() throws SchedulerException {
		JobKey jobKey = new JobKey(ItemsCronJob.JOB_NAME, ItemsCronJob.JOB_GROUP);
		JobDetail job = JobBuilder.newJob(ItemsCronJob.class).withIdentity(jobKey).build();

		TriggerKey triggerKey = new TriggerKey(ItemsCronJob.TRIGGER_NAME, ItemsCronJob.TRIGGER_GROUP);
		String triggerValue = ItemsCronJob.TRIGGER_VALUE;
		Trigger trigger = TriggerBuilder.newTrigger() //
				.withIdentity(triggerKey) //
				.withSchedule(CronScheduleBuilder.cronSchedule(triggerValue)) //
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.getListenerManager().addJobListener(new CronJobListener(), KeyMatcher.keyEquals(jobKey));
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private void initRecipesJob() throws SchedulerException {
		JobKey jobKey = new JobKey(RecipesCronJob.JOB_NAME, RecipesCronJob.JOB_GROUP);
		JobDetail job = JobBuilder.newJob(RecipesCronJob.class).withIdentity(jobKey).build();

		TriggerKey triggerKey = new TriggerKey(RecipesCronJob.TRIGGER_NAME, RecipesCronJob.TRIGGER_GROUP);
		String triggerValue = RecipesCronJob.TRIGGER_VALUE;
		Trigger trigger = TriggerBuilder.newTrigger() //
				.withIdentity(triggerKey) //
				.withSchedule(CronScheduleBuilder.cronSchedule(triggerValue)) //
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.getListenerManager().addJobListener(new CronJobListener(), KeyMatcher.keyEquals(jobKey));
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	private void initSystemData() throws PersistenceException {
		List<Right> rights = createDefaultRights();
		List<UserGroup> userGroups = createDefaultUserGroups(rights);
		User admin = createAdminUser();
	}

	private List<Right> createDefaultRights() throws PersistenceException {
		IRightDAO rightDao = DBDAOFactory.getInstance().getRightDAO();
		List<Right> availableActiveRights = rightDao.getRightByActiveState(ActiveState.ACTIVE);
		if (availableActiveRights.isEmpty()) {
			Right retrieveCustomWaypoints = new Right(ActiveState.ACTIVE, "retrieveCustomWaypoints", "getWaypoints");
			Right editCustomWaypoints = new Right(ActiveState.ACTIVE, "editCustomWaypoints", "editWaypoints");
			availableActiveRights.add(rightDao.save(retrieveCustomWaypoints));
			availableActiveRights.add(rightDao.save(editCustomWaypoints));
		}
		return availableActiveRights;
	}

	private List<UserGroup> createDefaultUserGroups(List<Right> rights) throws PersistenceException {
		IUserGroupDAO userGroupDao = DBDAOFactory.getInstance().getUserGroupDAO();
		List<UserGroup> availableActiveUserGroups = userGroupDao.getUserGroupByActiveState(ActiveState.ACTIVE);
		if (availableActiveUserGroups.isEmpty()) {
			UserGroup adminUserGroup = new UserGroup(ActiveState.ACTIVE, "Admin");
			adminUserGroup.setRightSet(new HashSet<>(rights));
			UserGroup sdUserGroup = new UserGroup(ActiveState.ACTIVE, "SD-Member");
			sdUserGroup.setRightSet(new HashSet<>(rights));
			availableActiveUserGroups.add(userGroupDao.save(adminUserGroup));
			availableActiveUserGroups.add(userGroupDao.save(sdUserGroup));
		}
		return availableActiveUserGroups;
	}

	private User createAdminUser() throws PersistenceException {
		IUserDAO userDAO = DBDAOFactory.getInstance().getUserDAO();
		IUserGroupDAO userGroupDAO = DBDAOFactory.getInstance().getUserGroupDAO();
		User admin = userDAO.getUserByUsername("SysAdmin");
		if (admin == null) {
			UserGroup adminGroup = userGroupDAO.findByName("Admin");
			Date curDate = new Date();
			User sysAdmin = new User(ActiveState.NOT_ADMIN, "SysAdmin", "SysAdmin", "SysAdmin",
					"a25323682fc39c13852c5423bc856cafd776e7d5fdd29cb4c199b83c0802e289",
					"f29e6f64-af76-4428-b5ee-9a99bce44ab5", curDate, curDate, adminGroup);
			admin = userDAO.save(sysAdmin);
		}
		return admin;
	}

	private void loadUserGroupRightCache() throws ServiceException {
		final IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
		final UserGroupRightCache cache = UserGroupRightCache.getInstance();

		if (LOG.isInfoEnabled()) {
			LOG.info("Fülle Benutzergruppen-Rechte Cache...");
		}
		final List<UserGroup> userGroups = authService.getUserGroupsForCache();
		for (final UserGroup userGroup : userGroups) {
			final Set<Right> rights = userGroup.getRightSet();
			for (final Right right : rights) {
				cache.putRight(userGroup, right.getRightKey());
			}
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("{} Benutzergruppe(n) wurde(n) erfolgreich hinzugefügt.", userGroups.size());
		}
	}

	private void startServices() {
		try {
			initSystemData();
			//			initGemsStatisticJob();
			//			initItemsJob();
			//			initRecipesJob();
			loadUserGroupRightCache();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
