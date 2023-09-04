// package coding.toast.bread._01_01;
//
// import coding.toast.bread._01.incrementer.DailyJobTimeStamper;
// import lombok.RequiredArgsConstructor;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.Step;
// import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepScope;
// import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// @RequiredArgsConstructor
// public class SystemCommandJobConfig {
//
// 	private final JobBuilderFactory jobBuilderFactory;
//
// 	private final StepBuilderFactory stepBuilderFactory;
//
// 	@Bean
// 	public Job systemCommandJob() {
// 		return jobBuilderFactory.get("systemCommandJob")
// 			.start(systemCommandStep())
// 			.incrementer(new DailyJobTimeStamper())
// 			.build();
//
// 	}
//
// 	@Bean
// 	public Step systemCommandStep() {
// 		return stepBuilderFactory.get("systemCommandStep")
// 			.tasklet(systemCommandTasklet(null))
// 			.build();
// 	}
//
// 	// https://stackoverflow.com/questions/62333601/use-shell-command-in-spring-batch-systemcommandtasklet
// 	@StepScope
// 	@Bean
// 	public SystemCommandTasklet systemCommandTasklet(@Value("#{jobParameters['name']}") String name) {
// 		SystemCommandTasklet systemCommand = new SystemCommandTasklet();
// 		// systemCommand.setWorkingDirectory("C://study");
// 		systemCommand.setCommand("echo hi");
// 		systemCommand.setTimeout(5000);
// 		systemCommand.setInterruptOnCancel(true);
// 		return systemCommand;
// 	}
//
// }
