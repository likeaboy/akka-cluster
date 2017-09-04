package org.jrocky.akka.cluster;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Context Wrapper
 * @author rocky
 *
 */
public class ApplicationContextWrapper {

	private volatile static ApplicationContextWrapper instance = new ApplicationContextWrapper();
	private ApplicationContext context = null;
	private boolean init = false;
	
	private ApplicationContextWrapper(){
		init();
	}
	
	private void init(){
		String[] xmls = new String[]{
				"micro-service.xml",
				"classpath*:/spring/dao/*",
				"classpath*:/spring/service/*"
		};
		//加载spirng配置文件
		context= new ClassPathXmlApplicationContext(xmls);
		init = true;
	}
	
	public static ApplicationContextWrapper getInstance(){
		return instance;
	}
	
	public ApplicationContext getSpringContext(){
		return context;
	}
	
	public void load(){
		if(!init)init();
	}
	
}
