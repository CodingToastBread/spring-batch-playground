package coding.toast.bread;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchPlaygroundApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPlaygroundApplication.class, args);
	}
	
}
