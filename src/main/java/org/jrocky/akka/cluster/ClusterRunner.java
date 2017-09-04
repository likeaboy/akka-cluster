package org.jrocky.akka.cluster;

import java.util.concurrent.ConcurrentHashMap;

import org.jrocky.akka.client.AkkaClusterConfig;
import org.jrocky.akka.cluster.master.ClusterConfigHolder;
import org.jrocky.akka.cluster.master.MasterRunner;
import org.jrocky.akka.cluster.master.SeedRunner;
import org.jrocky.akka.cluster.worker.WorkerRunner;

/**
 * 集群启动器
 * @author rocky
 *
 */
public class ClusterRunner {
public static void main(String[] args) {
	// 加载spring配置文件
	ApplicationContextWrapper.getInstance().load();
	 AkkaClusterConfig config = ClusterConfigHolder.getConfig();
	 //启动服务节点，优先启动2551,2552两个种子节点，启动顺序固定且不能变，否则会产生集群脑裂現象
    SeedRunner.main(new String[] { String.valueOf(config.getSeedPortFirst())});
    SeedRunner.main(new String[] { String.valueOf(config.getSeedPortSecond())});
    WorkerRunner.main(new String[0]);
    WorkerRunner.main(new String[0]);
    WorkerRunner.main(new String[0]);
    
   
    //启动任务分发节点
  	 MasterRunner.main(new String[] {String.valueOf(config.getTaskDispatcherPort())});
   
}
}
