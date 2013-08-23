package name.krestjaninoff.activiti.hello;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext applicationContext =
		    new ClassPathXmlApplicationContext("applicationContext.xml");

		RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeService");
		runtimeService.startProcessInstanceByKey("helloWorldProcess");

        TaskService taskService = (TaskService) applicationContext.getBean("taskService");
        IdentityService identityService = (IdentityService) applicationContext.getBean("identityService");

        Task userTask = taskService.createTaskQuery().list().get(0);
        User user = identityService.newUser("Sasha");
        userTask.setAssignee(user.getId());
        taskService.complete(userTask.getId());
    }
}
