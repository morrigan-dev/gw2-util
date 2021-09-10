package de.morrigan.dev.gw2.business.logic.cronjobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronJobListener implements JobListener {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(CronJobListener.class);

	private static final String LISTENER_NAME = CronJobListener.class.getName();

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOG.info("{} wurde abgebrochen.", context.getJobDetail().getKey().getName());
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		LOG.info("{} startet jetzt.", context.getJobDetail().getKey().getName());
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		LOG.info("{} wurde beendet.", context.getJobDetail().getKey().getName());
		if (jobException != null) {
			LOG.error(jobException.getMessage(), jobException);
		}
	}
}
