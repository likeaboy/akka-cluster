package org.jrocky.akka.cluster.utils;

import org.jrocky.akka.cluster.ApplicationContextWrapper;

public class MicroServiceContextUtils {
	public static Object getBean(String name){
		return ApplicationContextWrapper.getInstance().getSpringContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clz){
		return ApplicationContextWrapper.getInstance().getSpringContext().getBean(clz);
	}
	
	public static <T> T getBean(Class<T> clz,String name){
		return ApplicationContextWrapper.getInstance().getSpringContext().getBean(name, clz);
	}
}
