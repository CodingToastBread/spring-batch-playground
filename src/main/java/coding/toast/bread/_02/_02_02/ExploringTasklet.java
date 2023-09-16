package coding.toast.bread._02._02_02;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class ExploringTasklet implements Tasklet {

	private final JobExplorer explorer;

	public ExploringTasklet(JobExplorer explorer) {
		this.explorer = explorer;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String jobName = chunkContext.getStepContext().getJobName();

		List<JobInstance> instances = explorer.getJobInstances(jobName, 0, Integer.MAX_VALUE);

		System.out.printf("There are %d job instances for the job %s%n", instances.size(), jobName);

		System.out.println("They have had the following results");

		System.out.println("---------------------------------------------");

		for (JobInstance instance : instances) {
			List<JobExecution> jobExecutions = this.explorer.getJobExecutions(instance);

			System.out.printf("Instance %d had %d executions%n", instance.getInstanceId(), jobExecutions.size());

			for (JobExecution jobExecution : jobExecutions) {
				System.out.printf("Execution %d resulted in ExitStatus %s%n", jobExecution.getId(), jobExecution.getExitStatus());
			}
		}
		return RepeatStatus.FINISHED;
	}
}
