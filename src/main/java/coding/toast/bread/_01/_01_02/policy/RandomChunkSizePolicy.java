package coding.toast.bread._01._01_02.policy;

import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Random;

public class RandomChunkSizePolicy implements CompletionPolicy {
	
	private int chunkSize;
	private int totalProcessed;
	private final Random random = new Random();
	
	@Override
	public boolean isComplete(RepeatContext context, RepeatStatus result) {
		return RepeatStatus.FINISHED == result || isComplete(context);
	}
	
	@Override
	public boolean isComplete(RepeatContext context) {
		return this.totalProcessed >= chunkSize;
	}
	
	@Override
	public RepeatContext start(RepeatContext parent) {
		this.chunkSize = random.nextInt(20);
		this.totalProcessed = 0;
		
		System.out.println("The chunk size has been set to " + this.chunkSize);
		
		return parent;
	}
	
	@Override
	public void update(RepeatContext context) {
		this.totalProcessed++;
	}
}
