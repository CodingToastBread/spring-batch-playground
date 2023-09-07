package coding.toast.bread._01._01_03;

import coding.toast.bread._01._01_00.incrementer.DailyJobTimeStamper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExternalConditionalJobConfig {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	public Tasklet loadStockFile() {
		return (contribution, chunkContext) -> {
			System.out.println("The Stock file has been loaded");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Tasklet loadCustomerFile() {
		return (contribution, chunkContext) -> {
			System.out.println("The customer file has been loaded");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Tasklet updateStart() {
		return (contribution, chunkContext) -> {
			System.out.println("Starting update!");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Tasklet runBatchTasklet() {
		return (contribution, chunkContext) -> {
			System.out.println("The batch has been run");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public Step loadFileStep() {
		return this.stepBuilderFactory.get("loadFileStep")
			.tasklet(loadStockFile())
			.build();
	}
	
	@Bean
	public Step loadCustomerStep() {
		return this.stepBuilderFactory.get("loadCustomerStep")
			.tasklet(loadCustomerFile())
			.build();
	}
	
	@Bean
	public Step updateStartStep() {
		return this.stepBuilderFactory.get("updateStartStep")
			.tasklet(updateStart())
			.build();
	}
	
	@Bean
	public Step runBatch() {
		return this.stepBuilderFactory.get("runBatch")
			.tasklet(runBatchTasklet())
			.build();
	}
	
	@Bean
	public Flow preProcessingFlow() {
		return new FlowBuilder<Flow>("preProcessingFlow")
			.start(loadFileStep())
			.next(loadCustomerStep())
			.next(updateStartStep())
			.build();
	}
	
	@Bean
	public Step initializeStep() {
		return this.stepBuilderFactory.get("initializeStep")
			.flow(preProcessingFlow())
			.build();
	}
	
	/* using only external Flow Bean
	@Bean
	public Job conditionalStepLogicJob() {
		return this.jobBuilderFactory
			.get("conditionalStepLogicJob")
			.incrementer(new DailyJobTimeStamper())
			.start(preProcessingFlow())
			.next(runBatch())
			.end()
			.build();
	}*/
	
	@Bean
	public Job conditionalStepLogicJob() {
		return this.jobBuilderFactory
			.get("conditionalStepLogicJob2")
			.incrementer(new DailyJobTimeStamper())
			.start(initializeStep())
			.next(runBatch())
			.build();
	}
}
