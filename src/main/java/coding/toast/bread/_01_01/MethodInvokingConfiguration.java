package coding.toast.bread._01_01;

import coding.toast.bread._01.incrementer.DailyJobTimeStamper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
@RequiredArgsConstructor
public class MethodInvokingConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	
	private final StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	public Job methodInvokingJob() {
		return jobBuilderFactory.get("methodInvokingJob")
			.start(methodInvokingStep())
			.incrementer(new DailyJobTimeStamper())
			.build();
		
	}
	
	@Bean
	public Step methodInvokingStep() {
		return stepBuilderFactory.get("methodInvokingStep")
			.tasklet(methodInvokingTasklet(null))
			.build();
	}
	
	@StepScope
	@Bean
	public MethodInvokingTaskletAdapter methodInvokingTasklet(@Value("#{jobParameters['name']}") String name) {
		MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();
		methodInvokingTaskletAdapter.setTargetObject(service());
		methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");
		methodInvokingTaskletAdapter.setArguments(new String[]{name});
		return methodInvokingTaskletAdapter;
	}
	
	@Bean
	public CustomService service() {
		return new CustomService();
	}
	
	public static class CustomService {
		public void serviceMethod(String message) {
			System.out.println("Service method was called, argument : " + message);
		}
	}
	
}
