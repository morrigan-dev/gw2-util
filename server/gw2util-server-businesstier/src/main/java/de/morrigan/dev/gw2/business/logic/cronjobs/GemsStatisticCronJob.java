package de.morrigan.dev.gw2.business.logic.cronjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.factories.ServiceFactory;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;

public class GemsStatisticCronJob implements Job {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GemsStatisticCronJob.class);

	public static final String JOB_NAME = GemsStatisticCronJob.class.getName() + "_job";
	public static final String JOB_GROUP = "GW2-Utilities_job";
	public static final String TRIGGER_NAME = GemsStatisticCronJob.class.getName() + "_trigger";
	public static final String TRIGGER_GROUP = "GW2-Utilities_trigger";
	public static final String TRIGGER_VALUE = "0 0/5 * * * ? *";

	public GemsStatisticCronJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("context: {}", context);

		IJobService jobService = ServiceFactory.getInstance().getJobService();
		try {
			jobService.receiveGemsStatisticValue();
		} catch (ServiceException e) {
			throw new JobExecutionException(e);
		}
	}

}
