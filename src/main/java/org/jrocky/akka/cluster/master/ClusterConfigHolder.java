package org.jrocky.akka.cluster.master;

import org.jrocky.akka.client.AkkaClientConfig;
import org.jrocky.akka.client.AkkaClusterConfig;
import org.jrocky.akka.cluster.utils.MicroServiceContextUtils;

public class ClusterConfigHolder {

//	private static final AkkaClusterConfig config = MicroServiceContextUtils.getBean(AkkaClusterConfig.class, "clusterConfig");
	private static final AkkaClusterConfig config = new AkkaClusterConfig();
	
	public static AkkaClusterConfig getConfig(){
		return config;
	}
}
