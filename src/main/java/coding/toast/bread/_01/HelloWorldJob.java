package coding.toast.bread._01;

import coding.toast.bread._01.incrementer.DailyJobTimeStamper;
import coding.toast.bread._01.listener.JobLoggerListenerBeanStyle;
import coding.toast.bread._01.validator.ParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// @Configuration
@RequiredArgsConstructor
public class HelloWorldJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("basicJob")
			.start(step1())
			.validator(validator())
			// .incrementer(new RunIdIncrementer())
			.incrementer(new DailyJobTimeStamper())
			.listener(JobListenerFactoryBean.getListener(new JobLoggerListenerBeanStyle()))
			.build();
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
			.tasklet(helloWorldTasklet(null, null))
			.listener(promotionListener())
			.build();
	}
	
	@StepScope
	@Bean
	public Tasklet helloWorldTasklet(
						@Value("#{jobParameters['fileName']}") String fileName,
						@Value("#{jobParameters['name']}") String name) {
		
		return (contribution, chunkContext) -> {
			// Object name = chunkContext.getStepContext().getJobParameters()
			// 	.get("name");
			System.out.println("fileName = " + fileName);
			System.out.println("name = " + name);
			
			ExecutionContext jobExecutionContext = chunkContext.getStepContext()
				.getStepExecution()
				.getJobExecution()
				.getExecutionContext();
			
			jobExecutionContext.put("job.user.name", "dailyCode111");
			
			ExecutionContext stepExecutionContext =
				chunkContext.getStepContext()
					.getStepExecution()
					.getExecutionContext();
			
			stepExecutionContext.put("step.user.name", "dailyCode222");
			stepExecutionContext.put("step.promotion.name", "promoteKey");
			
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public CompositeJobParametersValidator validator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		
		// fileName, name 을 제외한 파라미터가 들어오면 에러가 남
		DefaultJobParametersValidator defaultValidator = new DefaultJobParametersValidator(
			new String[]{"fileName"},
			new String[]{"name", "run.id", "currentDate"}
		);
		
		validator.setValidators(List.of(new ParameterValidator(), defaultValidator));
		return validator;
	}
	
	@Bean
	public StepExecutionListener promotionListener() {
		ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
		listener.setKeys(new String[]{"step.promotion.name"});
		return listener;
	}
	
}
