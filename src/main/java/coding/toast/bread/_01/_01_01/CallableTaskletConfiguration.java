package coding.toast.bread._01._01_01;

import coding.toast.bread._01._01_00.incrementer.DailyJobTimeStamper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Callable;

// @Configuration
@RequiredArgsConstructor
public class CallableTaskletConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job callableJob() {
		return this.jobBuilderFactory.get("callableJob")
			.start(callableStep())
			.incrementer(new DailyJobTimeStamper())
			.build();
	}
	
	@Bean
	public Step callableStep() {
		return this.stepBuilderFactory.get("callableStep")
			.tasklet(tasklet())
			.build();
	}
	
	@Bean
	public Callable<RepeatStatus> callableObject() {
		return () -> {
			System.out.println("this was executed in another thread");
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
	public CallableTaskletAdapter tasklet() {
		CallableTaskletAdapter callableTaskletAdapter = new CallableTaskletAdapter();
		callableTaskletAdapter.setCallable(callableObject());
		return callableTaskletAdapter;
	}
	
	
	
}
