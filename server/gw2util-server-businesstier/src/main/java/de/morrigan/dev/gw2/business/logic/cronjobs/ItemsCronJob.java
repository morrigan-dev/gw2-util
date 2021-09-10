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

public class ItemsCronJob implements Job {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ItemsCronJob.class);

	public static final String JOB_NAME = ItemsCronJob.class.getName() + "_job";
	public static final String JOB_GROUP = "GW2-Utilities_job";
	public static final String TRIGGER_NAME = ItemsCronJob.class.getName() + "_trigger";
	public static final String TRIGGER_GROUP = "GW2-Utilities_trigger";
	public static final String TRIGGER_VALUE = "0 21 18 * * ? *";

	private static final int ITEMS_PER_CALL = 100;

	public ItemsCronJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("context: {}", context);

		IJobService jobService = ServiceFactory.getInstance().getJobService();
		try {
			long[] receiveItemIds = jobService.receiveItemIds();
			for (int index = 0; index < receiveItemIds.length; index += ITEMS_PER_CALL) {
				int endIndex = index + ITEMS_PER_CALL;
				if (endIndex > receiveItemIds.length) {
					endIndex = receiveItemIds.length;
				}
				long[] idsToLoad = Arrays.copyOfRange(receiveItemIds, index, endIndex);
				LOG.info("Lade die nächsten " + ITEMS_PER_CALL + " Items...");
				jobService.receiveItems(idsToLoad);
			}
		} catch (ServiceException e) {
			throw new JobExecutionException(e);
		}
	}

}
