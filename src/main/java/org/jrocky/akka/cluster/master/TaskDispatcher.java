package org.jrocky.akka.cluster.master;

import java.util.ArrayList;
import java.util.List;

import org.jrocky.akka.client.command.BaseCommand;
import org.jrocky.akka.client.model.JobResult;
import org.jrocky.akka.client.model.Response;
import org.jrocky.akka.cluster.Constants;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 任务分发器
 * @author rocky
 *
 */
public class TaskDispatcher extends AbstractActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	// 服务集合
	List<ActorRef> backends = new ArrayList<>();
	int jobCounter = 0;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(BaseCommand.class,
						cmmand -> backends.isEmpty(),
						cmmand -> {
							sender().tell(
									new JobResult(
											Response.SERVICE_UNAVAILABLE.code,
											"服务不可用"), sender());
						})
				.matchEquals(Constants.BACKEND_REGISTRATION, message -> {
					getContext().watch(sender());
					backends.add(sender());
				})
				.match(Terminated.class, terminated -> {
					backends.remove(terminated.getActor());
				})
				.match(BaseCommand.class,
						cmmand -> {
							log.info("message arrive task dispacher : "
									+ cmmand.getJob().getTaskId());
							jobCounter++;
							backends.get(jobCounter % backends.size()).forward(
									cmmand, getContext());
							log.info("current backend service count {}",backends.size());
						}).build();
	}
}
