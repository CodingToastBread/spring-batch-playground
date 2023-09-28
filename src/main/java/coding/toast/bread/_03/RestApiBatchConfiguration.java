// package coding.toast.bread._03;
//
// import coding.toast.bread._01._01_00.incrementer.DailyJobTimeStamper;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.Setter;
// import org.springframework.batch.core.*;
// import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
// import org.springframework.batch.core.explore.JobExplorer;
// import org.springframework.batch.core.launch.JobLauncher;
// import org.springframework.batch.core.launch.support.RunIdIncrementer;
// import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
// import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
// import org.springframework.batch.core.repository.JobRestartException;
// import org.springframework.batch.repeat.RepeatStatus;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.util.Properties;
//
//
// /**
//  * NEED "org.springframework.boot: spring-boot-starter-web" DEPENDENCY
//  */
// @Configuration
// @RequiredArgsConstructor
// public class RestApiBatchConfiguration {
//
// 	private final JobBuilderFactory jobBuilderFactory;
// 	private final StepBuilderFactory stepBuilderFactory;
//
// 	@Bean
// 	public Job restJob() {
// 		return this.jobBuilderFactory.get("restJob")
// 			.incrementer(new RunIdIncrementer())
// 			.start(step1())
// 			.build();
// 	}
//
// 	@Bean
// 	public Step step1() {
// 		return this.stepBuilderFactory.get("step1")
// 			.tasklet((contribution, chunkContext) -> {
// 				System.out.println("step1 ran!");
// 				return RepeatStatus.FINISHED;
// 			}).build();
// 	}
//
// 	@RestController
// 	@RequiredArgsConstructor
// 	public static class JobLaunchingController {
// 		private final JobLauncher jobLauncher;
// 		private final ApplicationContext context;
// 		private final JobExplorer jobExplorer;
//
// 		@PostMapping(path = "/run")
// 		public ExitStatus runJob(@RequestBody JobLaunchRequest request) throws Exception {
// 			Job job = this.context.getBean(request.getName(), Job.class);
//
// 			// JobLauncherApplicationRunner.execute 를 자세히 보면
// 			// JobParameters parameters = getNextJobParameters(job, jobParameters);
// 			// JobExecution execution = this.jobLauncher.run(job, parameters);
// 			// ... 처럼 코딩되어 있다. 스프링부트 자동 Job 실행기도 결국은 run 전에 미리 NextJobParameter 를 받은 것이다!
// 			JobParameters jobParameters =
// 				new JobParametersBuilder(request.getJobParameters(), this.jobExplorer)
// 					.getNextJobParameters(job)
// 					.toJobParameters();
//
// 			return this.jobLauncher.run(job, jobParameters).getExitStatus();
// 		}
// 	}
//
// 	public static class JobLaunchRequest {
// 		private String name;
//
// 		private Properties jobParameters;
//
// 		public String getName() {
// 			return name;
// 		}
//
// 		public void setName(String name) {
// 			this.name = name;
// 		}
//
// 		public Properties getJobParamsProperties() {
// 			return jobParameters;
// 		}
//
// 		public void setJobParamsProperties(Properties jobParameters) {
// 			this.jobParameters = jobParameters;
// 		}
//
// 		public JobParameters getJobParameters() {
// 			Properties properties = new Properties();
// 			properties.putAll(this.jobParameters);
// 			return new JobParametersBuilder(properties).toJobParameters();
// 		}
// 	}
// }
