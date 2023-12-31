package coding.toast.bread._03.quartz;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("quartzJob")
			.incrementer(new RunIdIncrementer())
			.start(step1())
			.build();
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step1 ran!");
				return RepeatStatus.FINISHED;
			}).build();
	}
	
}
