package mm_scheduler.instanceScheduler.instance.manager;

import java.util.List;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.CandidateProcess;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Machine;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.instance.manager.iface.AssignManagerIface;

/**
 * 
 * @author: hba
 * @description: 工序分配管理器
 * @date: 2019年12月12日
 *
 */
public class AssignManager implements AssignManagerIface {
	private final static Log log = LogFactory.getLog(AssignManager.class);

	/**
	 * 
	 * @author: hba
	 * @description:将工序安排到机床，默认采用插空方式
	 * @param instance
	 * @param operation
	 * @param insert
	 * @throws Exception
	 * @date: 2019年12月12日
	 *
	 */
	protected void assignOperation(Instance instance, Operation operation, boolean insert) throws Exception {
		// TODO Auto-generated method stub
		// 寻找可选机床
		List<CandidateProcess> candidateProcesses = instance.getCandidateProMap().get(operation.getName());
		Machine machine = null;
		CandidateProcess candidateProcess = null;
		// 刚好有一台可用机床，则直接分派
		if (candidateProcesses.size() == 1) {
			machine = instance.getMachineMap().get(candidateProcesses.get(0).getMachineName());
			candidateProcess = candidateProcesses.get(0);
		} else {
			// 多于一台机床的情况(如并行机或柔性作业车间调度)
			if (operation.getFixedMachineID() != -1) {
				machine = instance.getMachineMap().get("M" + operation.getFixedMachineID());
				candidateProcess = candidateProcesses.stream()
						.filter(can -> can.getMachineID() == operation.getFixedMachineID()).findAny().get();
			}
		}
		operation.setMachineID(machine.getID());
		operation.setMachineName(machine.getName());
		// operation.setRunTime(10);
		// operation.setPreTime(0);
		// 如果机床没有安排任何任务，则直接安排
		if (machine.getQueueList().size() == 0) {
			// 计划开始为最早开始
			operation.setStart(operation.getEarlyStart());
			operation.setFinish(operation.getStart() + candidateProcess.getDuration());
		} else {
			findSpace(operation, candidateProcess, machine, insert);
		}
		// 将工序添加到机床的队列中
		machine.getQueueList().add(operation);
		machine.setAssignedTaskWork(machine.getAssignedTaskWork() + candidateProcess.getDuration());
		instance.setTotalAssignedTaskWork(instance.getTotalAssignedTaskWork() + candidateProcess.getDuration());
		afterAssign(instance, operation);
		if (machine == null) {
			log.error("无可选机床！");
			throw new Exception(operation.getName() + "无可选机床");
		}
	}

	/**
	 * 
	 * @author: hba
	 * @description: 任务分派后处理，将任务从就绪任务集合中移除，设置工件的当前工序
	 * @param instance
	 * @param operation
	 * @date: 2019年12月12日
	 *
	 */
	public void afterAssign(Instance instance, Operation operation) throws Exception {
		// TODO Auto-generated method stub
		TreeSet<Operation> readyTasks = instance.getReadyTaskS();
		operation.setState(Operation.Assigned_State);
		// if (readyTasks.contains(operation)) {
		readyTasks.remove(operation);
		Operation nextOp = operation.getSuccOp();
		Part part = instance.getPartMap().get("P" + operation.getPartID());
		part.setCurrOp(nextOp);
		if (nextOp != null) {
			readyTasks.add(nextOp);
		} else {
			// 设置工序结束时间为工件结束时间
			part.setFinish(operation.getFinish());
		}
		// }
		instance.getObjective().calcValue(instance);
	}

	/**
	 * 
	 * @author: hba
	 * @description:插空处理，如果机床上没有安排任务，则工序可以从最早开始加工，如果有任务，则从第一个任务开始一次寻找空隙
	 * @param operation
	 * @param candidateProcess
	 * @param machine
	 * @param insert
	 * @date: 2019年12月12日
	 *
	 */
	private void findSpace(Operation operation, CandidateProcess candidateProcess, Machine machine, boolean insert) {
		// TODO Auto-generated method stub
		// 如果有任务了则需要判断插空
		double earlyStart = operation.getEarlyStart();
		double earlyFinish = earlyStart + candidateProcess.getDuration();
		boolean findSpace = false;
		Operation tempOp = new Operation();
		Operation formerOp = null;
		Operation latterOp = null;
		tempOp.setStart(earlyStart);
		tempOp.setFinish(earlyFinish);
		int findIndex = 0;
		// 只有需要插空时才寻找空隙
		if (insert) {
			while (!findSpace) {
				if (findIndex == 0) {
					// 第一次查找时使用一个虚拟工序
					Operation virOp = new Operation();
					virOp.setStart(0);
					virOp.setFinish(0);
					formerOp = virOp;
					latterOp = machine.getQueueList().first();
				}
				findSpace = insertOepration(tempOp, formerOp, latterOp);
				if (!findSpace) {
					tempOp.setStart(latterOp.getFinish() > earlyStart ? latterOp.getFinish() : earlyStart);
					tempOp.setFinish(tempOp.getStart() + candidateProcess.getDuration());
					formerOp = latterOp;
					latterOp = machine.getQueueList().higher(latterOp);
				} else {
					operation.setStart(tempOp.getStart());
					operation.setFinish(operation.getStart() + candidateProcess.getDuration());
				}
				findIndex++;
			}
		} else {
			// 不插空则直接选择机床上最后一道工序和工序前置工序的完成时间的较大值作为开始
			latterOp = machine.getQueueList().last();
			double startTime = latterOp.getFinish() > operation.getEarlyStart() ? latterOp.getFinish()
					: operation.getEarlyStart();
			operation.setStart(startTime);
			operation.setFinish(startTime + candidateProcess.getDuration());
		}
	}

	/**
	 * 
	 * @author: hba
	 * @description:判断机床的空隙是否可以放下当前空虚
	 * @param operation
	 * @param formerOp
	 *            机床上的前一道工序
	 * @param latterOp
	 *            机床上的后一道工序
	 * @return boolean
	 * @date: 2019年12月12日
	 *
	 */
	private boolean insertOepration(Operation operation, Operation formerOp, Operation latterOp) {
		// TODO Auto-generated method stub
		double start = 0;
		double finish = 0;
		double workTime = operation.getFinish() - operation.getStart();
		if (latterOp == null) {
			start = operation.getStart() > formerOp.getFinish() ? operation.getStart() : formerOp.getFinish();
			finish = start + workTime;
			operation.setStart(start);
			operation.setFinish(finish);
			return true;
		}
		if (latterOp.getStart() < operation.getFinish()) {
			return false;
		}
		start = operation.getStart() > formerOp.getFinish() ? operation.getStart() : formerOp.getFinish();
		if (latterOp.getStart() - start >= workTime) {
			operation.setStart(start);
			operation.setFinish(start + workTime);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @author: hba
	 * @description: 计算工序的最早开始
	 * @param instance
	 * @param operation
	 * @date: 2019年12月12日
	 *
	 */
	private void calEarlyStart(Instance instance, Operation operation) {
		// TODO Auto-generated method stub
		// 计算最早开始，目的是从该时间起在设备上寻找插空，如果是工件的首道工序，则为0
		if (operation.getID() == 1) {
			operation.setEarlyStart(0);
		} else {
			Operation predOp = operation.getPrepOp();
			operation.setEarlyStart(predOp.getFinish());
			if (predOp.getMachineID() == -1) {
				log.error("前置工序" + predOp.getName() + "未分派成功！");
			}
		}
	}

	/**
	 * 不考虑插空的分派 hba
	 * 
	 * @param instance
	 * @param operation
	 * @param insert
	 *            上午11:36:19
	 */
	public void assignTask(Instance instance, Operation operation, boolean insert) {
		// TODO Auto-generated method stub
		// 计算最早开始
		calEarlyStart(instance, operation);
		// 分派任务
		try {
			assignOperation(instance, operation, insert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author: hba
	 * @description: 默认执行插空
	 * @param instance
	 * @param operation
	 * @date: 2019年12月12日
	 *
	 */
	public void assignTask(Instance instance, Operation operation) {
		// TODO Auto-generated method stub
		assignTask(instance, operation, true);
	}

	private void calEarlyStart(Instance instance, Operation operation, boolean insert) {
		// TODO Auto-generated method stub
		double earlyStart = 0;
		// 计算最早开始
		if (operation.getPrepOp() != null) {
			earlyStart = operation.getPrepOp().getFinish();
		}
		Machine machine = instance.getMachineMap().get(operation.getMachineName());
		if (machine.getQueueList().size() > 0) {
			earlyStart = machine.getQueueList().last().getFinish() > earlyStart
					? machine.getQueueList().last().getFinish() : earlyStart;
		}
		operation.setEarlyStart(earlyStart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mm_scheduler.instanceScheduler.instance.manager.iface.AssignManagerIface#
	 * assignTask(mm_scheduler.instanceScheduler.instance.domain.basicdata.
	 * Operation)
	 */
	@Override
	public void assignTask(Operation operation) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mm_scheduler.instanceScheduler.instance.manager.iface.AssignManagerIface#
	 * removeFromReadyTasks(mm_scheduler.instanceScheduler.instance.domain.
	 * basicdata.Operation)
	 */
	@Override
	public void removeFromReadyTasks(Operation operation) {
		// TODO Auto-generated method stub

	}
}
