package org.jrocky.akka.cluster.master;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
/**
 * 主节点配置
 * @author rocky
 *
 */
public class MasterConfig {
	
	private Config config;
	
	public MasterConfig(String[] args){
		// Override the configuration of the port when specified as program argument
	    final String port = args.length > 0 ? args[0] : "0";
	    this.config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
	      withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).
	      withFallback(ConfigFactory.load());
	}

	public Config getConfig() {
		return config;
	}
}
