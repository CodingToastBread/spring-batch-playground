package coding.toast.bread._01_03;

import coding.toast.bread._01.incrementer.DailyJobTimeStamper;
import coding.toast.bread._01_03.decider.RandomDecider;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
@RequiredArgsConstructor
public class ConditionalJobConfig {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Tasklet passTasklet() {
		return (contribution, chunkContext) -> {
			// return RepeatStatus.FINISHED;
			throw new RuntimeException("This is a failure");
		};
	}
	
	@Bean
	public Tasklet successTasklet() {
		return (contribution, chunkContext) -> {
			System.out.println("Success!");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Tasklet failTasklet() {
		return (contribution, chunkContext) -> {
			System.out.println("Failure");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("conditionalJob3")
			// .incrementer(new DailyJobTimeStamper())
			.start(firstStep())
			// .next(decider())
			// .from(decider())
			// .on("FAILED").to(failureStep())
			// .from(decider())
			// .on("*").to(successStep())
			// .end()
			
			// .on("FAILED").fail()
			// .from(firstStep()).on("*").to(successStep())
			// .end()
			
			.on("FAILED").stopAndRestart(successStep())
			.from(firstStep())
			.on("*").to(successStep())
			.end()
			.build();
	}
	
	@Bean
	public JobExecutionDecider decider() {
		return new RandomDecider();
	}
	
	@Bean
	public Step firstStep() {
		return this.stepBuilderFactory.get("firstStep")
			.tasklet(passTasklet())
			.build();
	}
	
	@Bean
	public Step successStep() {
		return this.stepBuilderFactory.get("successStep")
			.tasklet(successTasklet())
			.build();
	}
	
	@Bean
	public Step failureStep() {
		return this.stepBuilderFactory.get("failureStep")
			.tasklet(failTasklet())
			.build();
	}
	
	
}
