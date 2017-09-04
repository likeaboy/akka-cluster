package org.jrocky.akka.cluster.worker;

import org.jrocky.akka.client.command.StartCommand;
import org.jrocky.akka.client.command.StopCommand;
import org.jrocky.akka.client.model.JobResult;
import org.jrocky.akka.client.model.Response;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import org.jrocky.akka.cluster.Constants;

/**
 * 任务处理线程
 * @author rocky
 *
 */
public class TaskWorker extends AbstractActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	//集群
	Cluster cluster = Cluster.get(getContext().system());

	/*private TaskManageService tmService;
	private TaskManageService untmService;*/

	// subscribe to cluster changes, MemberUp
	@Override
	public void preStart() {
		cluster.subscribe(self(), MemberUp.class);
		/*tmService = ApplicationContextWrapper.getInstance().getSpringContext()
				.getBean("TaskManageService", TaskManageService.class);
		untmService = ApplicationContextWrapper.getInstance()
				.getSpringContext()
				.getBean("UnTaskManageService", TaskManageService.class);*/
	}

	// re-subscribe when restart
	@Override
	public void postStop() {
		cluster.unsubscribe(self());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartCommand.class,
						command -> {
							// worker do
							String taskId = command.getJob().getTaskId();
							log.info("backed do [StartCommand] taskId=" + taskId);
							
							log.info("start task...");
							
							getSender().tell(
									new JobResult(Response.JOB_SUCCESS.code,
											"ok"), self());
						}).match(StopCommand.class, command -> {
							// worker do
							String taskId = command.getJob().getTaskId();
							log.info("backed do [StopCommand] taskId=" + taskId);
							log.info("stop task...");
							getSender().tell(
									new JobResult(Response.JOB_SUCCESS.code,
											"ok"), self());
						}).match(CurrentClusterState.class, state -> {
					for (Member member : state.getMembers()) {
						if (member.status().equals(MemberStatus.up())) {
							register(member);
						}
					}
				}).match(MemberUp.class, mUp -> {
					register(mUp.member());
				}).build();
	}

	void register(Member member) {
		if (member.hasRole("frontend"))
			getContext().actorSelection(member.address() + "/user/frontend")
					.tell(Constants.BACKEND_REGISTRATION, self());
	}
}
