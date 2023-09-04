package coding.toast.bread._01.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * need to wrap with JobListenerFactoryBean.getListener(new JobLoggerListenerBeanStyle)
 */
public class JobLoggerListenerBeanStyle /*implements JobExecutionListener*/ {
	
	private static String START_MESSAGE = "%s is beginning execution%n";
	private static String END_MESSAGE = "%s has completed with the status %s%n";
	
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		System.out.printf(START_MESSAGE, jobExecution.getJobInstance().getJobName());
	}
	
	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		System.out.printf(END_MESSAGE,
			jobExecution.getJobInstance().getJobName(),
			jobExecution.getStatus());
	}
	
}
