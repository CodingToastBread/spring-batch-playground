package coding.toast.bread._03.quartz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@RequiredArgsConstructor
public class BatchScheduledJob extends QuartzJobBean {
	
	private final Job job;
	
	private final JobExplorer jobExplorer;
	
	private final JobLauncher jobLauncher;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
			.getNextJobParameters(this.job)
			.toJobParameters();
		
		try {
			this.jobLauncher.run(this.job, jobParameters);
		} catch (Exception e) {
			log.error("error Occurred!", e);
		}
	}
}
