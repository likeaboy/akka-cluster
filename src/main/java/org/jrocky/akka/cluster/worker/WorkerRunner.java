package org.jrocky.akka.cluster.worker;

import org.jrocky.akka.cluster.master.MasterFacadeHolder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;


public class WorkerRunner {

  public static void main(String[] args) {
    // Override the configuration of the port when specified as program argument
    final String port = args.length > 0 ? args[0] : "0";
    final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
      withFallback(ConfigFactory.load());

    ActorSystem system = ActorSystem.create(MasterFacadeHolder.CLUSTER_SYSTEM, config);

    int backendServiceCount = 0;
    while(backendServiceCount < 10){
    	system.actorOf(Props.create(TaskWorker.class), "backend" + backendServiceCount);
    	backendServiceCount++;
    }
  }

}
