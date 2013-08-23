package name.krestjaninoff.activiti.hello.process;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.stereotype.Service;

@Service
public class SayHelloService {
	
	public void sayMale(ActivityExecution execution) {
		System.out.println("Client: male");
	}
	
	public void sayFemale(ActivityExecution execution) {
		System.out.println("Client: female");
	}
}
