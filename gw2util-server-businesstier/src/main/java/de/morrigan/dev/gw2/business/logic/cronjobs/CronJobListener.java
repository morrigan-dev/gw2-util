package de.morrigan.dev.gw2.business.logic.cronjobs;

import org.jboss.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class CronJobListener implements JobListener {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(CronJobListener.class);

	private static final String LISTENER_NAME = CronJobListener.class.getName();

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOG.info(context.getJobDetail().getKey().getName() + " wurde abgebrochen.");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		LOG.info(context.getJobDetail().getKey().getName() + " startet jetzt.");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		LOG.info(context.getJobDetail().getKey().getName() + " wurde beendet.");
		if (jobException != null) {
			LOG.error(jobException.getMessage(), jobException);
		}
	}
}
