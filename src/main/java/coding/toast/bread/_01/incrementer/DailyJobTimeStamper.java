package coding.toast.bread._01.incrementer;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.time.Instant;
import java.util.Date;

public class DailyJobTimeStamper implements JobParametersIncrementer {
	@Override
	public JobParameters getNext(JobParameters parameters) {
		return new JobParametersBuilder(parameters)
			// .addDate("currentDate", Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS)))
			.addDate("currentDate", Date.from(Instant.now()))
			.toJobParameters();
	}
}
