package coding.toast.bread._01._01_02;

import coding.toast.bread._01._01_00.incrementer.DailyJobTimeStamper;
import coding.toast.bread._01._01_02.policy.RandomChunkSizePolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.UUID;

// @Configuration
@RequiredArgsConstructor
public class CustomChunkBatchConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job chunkBatchJob() {
		return this.jobBuilderFactory.get("customChunkBatchJob")
			.start(step1())
			.incrementer(new DailyJobTimeStamper())
			.build();
	}

	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("customChunkBatchStep")
			// .<String, String>chunk(3)
			.<String, String>chunk(new RandomChunkSizePolicy())
			.reader(itemReader())
			.writer(itemWriter()).build();
	}
	
	@Bean
	public ListItemReader<String> itemReader() {
		ArrayList<String> items = new ArrayList<>(100);
		for (int i = 0; i < 100; i++) {
			items.add(UUID.randomUUID().toString());
		}
		return new ListItemReader<>(items);
	}
	
	@Bean
	public ItemWriter<String> itemWriter() {
		return items -> {
			for (String item : items) {
				System.out.println(">> current item = " + item);
			}
		};
	}

	
	@Bean
	public CompletionPolicy completionPolicy() {
		CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
		
		policy.setPolicies(
			new CompletionPolicy[]{
				new TimeoutTerminationPolicy(1L),
				new SimpleCompletionPolicy(3)
			}
		);
		
		return policy;
	}
}















