package org.jrocky.akka.cluster.master;
import akka.actor.ActorSystem;
import akka.actor.Props;
/**
 * MASTER节点启动器
 * @author rocky
 *
 */
public class MasterRunner {

	public static void main(String[] args) {
		//初始化配置
		MasterConfig mconfig = new MasterConfig(args);
		//master门面
		
		
		MasterFacadeHolder mHolder = MasterFacadeHolder.getInstance();
		mHolder.setConfig(mconfig);
		ActorSystem system = MasterFacadeHolder.getInstance().getMasterSystem();
		
		//创建任务分发器
		system.actorOf(Props.create(TaskDispatcher.class), "frontend");
	}

}
