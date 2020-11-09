package de.morrigan.dev.gw2.business.logic.cronjobs;

import java.util.Arrays;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.factories.ServiceFactory;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;

public class RecipesCronJob implements Job {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(RecipesCronJob.class);

	public static final String JOB_NAME = RecipesCronJob.class.getName() + "_job";
	public static final String JOB_GROUP = "GW2-Utilities_job";
	public static final String TRIGGER_NAME = RecipesCronJob.class.getName() + "_trigger";
	public static final String TRIGGER_GROUP = "GW2-Utilities_trigger";
	public static final String TRIGGER_VALUE = "0 05 20 * * ? *";

	public RecipesCronJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("context: {}", context);

		IJobService jobService = ServiceFactory.getInstance().getJobService();
		try {
			long[] receivedRecipesIds = jobService.receiveRecipesIds();
			int steps = 100;
			for (int index = 0; index < receivedRecipesIds.length; index += steps) {
				int endIndex = index + steps;
				if (endIndex > receivedRecipesIds.length) {
					endIndex = receivedRecipesIds.length;
				}
				long[] idsToLoad = Arrays.copyOfRange(receivedRecipesIds, index, endIndex);
				LOG.info("Lade die nächsten " + steps + " Rezepte...");
				jobService.receiveRecipes(idsToLoad);
			}
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

}
