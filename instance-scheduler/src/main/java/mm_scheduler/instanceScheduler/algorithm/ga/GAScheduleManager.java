/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.ga;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.CandidateProcess;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Machine;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.instance.manager.ScheduleManager;
import mm_scheduler.instanceScheduler.rules.OperationPriority;
import mm_scheduler.instanceScheduler.rules.OperationSPT;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * @author: hba
 * @description:遗传算法的调度管理类，实现了自己的调度方法
 * @date: 2019年12月18日
 *
 */
public class GAScheduleManager extends ScheduleManager {
	private final static Log log = LogFactory.getLog(GAScheduleManager.class);

	@Override
	public double scheduleStep(Instance instance, OperationRule operationRule) {
		return 0;
	}

	/**
	 * 
	 * @author: hba
	 * @description: 按照规则序列进行调度
	 * @param instance
	 * @param rules
	 *            规则序号
	 * @return
	 * @throws Exception
	 * @date: 2019年12月12日
	 *
	 */
	public double scheduleRules(Instance instance, int[] rules) {
		try {
			this.beforeSchedule(instance, 0);
			for (int rule : rules) {
				Operation operation = null;
				// 选择规则
				InstanceUtil.setInstanceOperationComparator(instance, instance.getOperationRules().get(rule));
				// 初始化就绪任务
				this.initReadyTasks(instance);
				operation = instance.getReadyTaskS().first();
				// 选择工序
				if (operation.getState() == Operation.Unassigned_state) {
					try {
						assignManager.assignTask(instance, operation);
					} catch (Exception e) {
						log.error("分配任务" + operation + "时发生异常" + e.getMessage());
						e.printStackTrace();
					} finally {
						instance.getReadyTaskS().remove(operation);
					}
				} else {
					// 保险，防止一些地方出现死循环
					instance.getReadyTaskS().remove(operation);
				}
			}
			this.afterSchedule(instance, 0);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return instance.getObjective().getObjectiveValue();
	}

	/**
	 * 
	 * @author: hba
	 * @description: 按照工序优先级进行调度
	 * @param instance
	 * @param priorityArray
	 *            优先级序列
	 * @return
	 * @date: 2019年12月12日
	 *
	 */
	public double schedulePriority(Instance instance, int[] priorityArray) {
		int i = 0;
		try {
			this.beforeSchedule(instance, 0);

			for (Operation operation : instance.getOperationMap().values()) {
				operation.setOpPriority(priorityArray[i]);
				i++;
			}

			instance.setOperationComparator(new OperationPriority());
			this.schedule(instance, 0);
			this.afterSchedule(instance, 0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return instance.getObjective().getObjectiveValue();
	}

	@Override
	public void beforeSchedule(Instance instance, int mode) {
		// TODO Auto-generated method stub
		// 由于在problem中已经进行了初始化，这里就不再需要了
		// instance.init();
		instance.reset();

	}

	/**
	 * 
	 * @author: hba
	 * @description: 以工件编号为编码，其出现的次数为工序数
	 * @param instance
	 * @param jobs
	 *            工件序号
	 * @return
	 * @date: 2019年12月26日
	 *
	 */
	public Double scheduleJobs(Instance instance, int[] jobs) {
		instance.reset();
		HashMap<Integer, Integer> jobCount = new HashMap<>();
		for (int job : jobs) {
			Operation operation = null;
			int opIndex = -1;
			if (jobCount.containsKey(job)) {
				opIndex = jobCount.get(job) + 1;
			} else {
				opIndex = 0;
			}
			jobCount.put(job, opIndex);
			if (opIndex >= instance.getPartMap().get("P" + job).getOpList().size()) {
				System.out.println();
			}
			operation = instance.getPartMap().get("P" + job).getOpList().get(opIndex);
			if (operation.getState() == Operation.Unassigned_state) {
				try {
					assignManager.assignTask(instance, operation);
				} catch (Exception e) {
					log.error("分配任务" + operation + "时发生异常" + e.getMessage());
					e.printStackTrace();
				} finally {
					instance.getReadyTaskS().remove(operation);
				}
			} else {
				// 保险，防止一些地方出现死循环
				instance.getReadyTaskS().remove(operation);
			}
		}
		return instance.getObjective().getObjectiveValue();
	}

	@Override
	public void schedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		boolean isClear = true;
		this.initReadyTasks(instance);
		// 只要就绪任务集合不为空，则继续安排
		while (!instance.getReadyTaskS().isEmpty()) {
			// 获得最优先的任务
			Operation operation = instance.getReadyTaskS().first();
			// System.out.println("分派工序：" + operation.getName());
			// 分派任务
			assignManager.assignTask(instance, operation);
			// 用于保存每步分派结果，用于动态显示甘特图
			if (instance.isWirteDynamic()) {
				InstanceUtil.outputSolutionStep(instance, operation, isClear);
				isClear = false;
			}
		}
	}

	@Override
	public void initReadyTasks(Instance instance) throws Exception {
		// TODO Auto-generated method stub
		Comparator<Operation> operationTaskComparator = instance.getOperationComparator();
		if (operationTaskComparator == null)
			operationTaskComparator = new OperationPriority();
		// 设置工序优先级比较器
		TreeSet<Operation> readyTaskS = new TreeSet<Operation>(operationTaskComparator);
		// 重新排序
		readyTaskS.addAll(instance.getReadyTaskS());
		instance.setReadyTaskS(readyTaskS);
	}

	/**
	 * 
	 * @author: hba
	 * @description: 按照工序优先级和机床双层编码进行调度
	 * @param instance
	 * @param priorityArray
	 *            工件优先级和机床优先级序列
	 * @return
	 * @date: 2019年12月12日
	 *
	 */
	public double schedulePriority(Instance instance, int[][] priorityArray) {
		int i = 0;
		try {
			this.beforeSchedule(instance, 0);
			for (Operation operation : instance.getOperationMap().values()) {
				// 设置工序优先级
				operation.setOpPriority(priorityArray[0][i]);
				List<CandidateProcess> canList = instance.getCandidateProMap().get(operation.getName());
				// 设置固定机床
				// CandidateProcess candidateProcess = canList
				// .get(priorityArray[1][i] > canList.size() - 1 ?
				// canList.size() - 1 : priorityArray[1][i]);
				CandidateProcess candidateProcess = canList.get(priorityArray[1][i] % canList.size());
				operation.setFixedMachineID(candidateProcess.getMachineID());
				i++;
			}

			instance.setOperationComparator(new OperationPriority());
			this.schedule(instance, 0);
			this.afterSchedule(instance, 0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return instance.getObjective().getObjectiveValue();
	}

	@Override
	public void doSchedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		this.beforeSchedule(instance, mode);
		this.schedule(instance, mode);
		this.afterSchedule(instance, mode);
	}
}
