package pt.paginasamarelas.scheduler;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskSchedulerApp {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("Spring-Scheduler.xml");
		//new ClassPathXmlApplicationContext("Spring-Scheduler.xml");
	}

}
