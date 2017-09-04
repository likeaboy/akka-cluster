package org.jrocky.akka.cluster.master;

import akka.actor.ActorSystem;

/**
 * MASTER门面，所有对MASTER的操作都由此类来担当
 * @author rocky
 *
 */
public class MasterFacadeHolder {
	/**
	 * 集群名称
	 */
	public static final String CLUSTER_SYSTEM = ClusterConfigHolder.getConfig().getClusterSystem();
	
	private static final MasterFacadeHolder instance = new MasterFacadeHolder();
	/**
	 * master配置
	 */
	private MasterConfig mconfig;
	/**
	 * master system
	 */
	private ActorSystem masterSystem;
	
	private Object lock = new Object();

	private MasterFacadeHolder(){}
	
	public static MasterFacadeHolder getInstance() {
		return instance;
	}
	
	public void setConfig(MasterConfig mconfig){
		this.mconfig = mconfig;
	}

	public MasterConfig getConfig() {
		return mconfig;
	}

	public ActorSystem getMasterSystem() {
		if(masterSystem == null){
			synchronized (lock) {
				if(masterSystem == null){
					masterSystem = ActorSystem.create(MasterFacadeHolder.CLUSTER_SYSTEM, mconfig.getConfig());
				}
			}
		}
		return masterSystem;
	}
	
}
