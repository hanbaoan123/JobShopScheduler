package mm_scheduler.instanceScheduler.instance.manager;

import java.util.Comparator;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Machine;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.instance.manager.iface.ScheduleManagerIface;
import mm_scheduler.instanceScheduler.rules.OperationSPT;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;
import mm_scheduler.instanceScheduler.util.FileHandle;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * 
 * @author: hba
 * @description: 案例调度管理器，所有的算法均以此为入口
 * @date: 2019年12月12日
 *
 */
public class ScheduleManager implements ScheduleManagerIface {
	public AssignManager assignManager = new AssignManager();
	FileHandle fileHandle = new FileHandle();
	private final static Log log = LogFactory.getLog(ScheduleManager.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#schedule(mm_scheduler.instanceScheduler.instance.
	 * domain.basicdata.Instance, int)
	 */
	@Override
	public void schedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		this.initReadyTasks(instance);
		// 只要就绪任务集合不为空，则继续安排
		while (!instance.getReadyTaskS().isEmpty()) {
			// 获得最优先的任务
			Operation operation = instance.getReadyTaskS().first();
			// 分派任务
			assignManager.assignTask(instance, operation);
			// 用于保存每步分派结果，用于动态显示甘特图
			if (instance.isWirteDynamic()) {
				InstanceUtil.outputSolutionStep(instance, operation);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#clearScheduleResult(mm_scheduler.instanceScheduler.
	 * instance.domain.basicdata.Instance)
	 */
	@Override
	public void clearScheduleResult(Instance instance) throws Exception {
		// TODO Auto-generated method stub
		instance.reset();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#cloneInstance(mm_scheduler.instanceScheduler.
	 * instance.domain.basicdata.Instance)
	 */
	@Override
	public Instance cloneInstance(Instance instance) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#beforeSchedule(mm_scheduler.instanceScheduler.
	 * instance.domain.basicdata.Instance, int)
	 */
	@Override
	public void beforeSchedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		instance.init();
		instance.reset();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#afterSchedule(mm_scheduler.instanceScheduler.
	 * instance.domain.basicdata.Instance, int)
	 */
	public void afterSchedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		// 设置工件的开始和结束
		for (Part part : instance.getPartMap().values()) {
			if (part.getOpList().size() > 0) {
				int opNum = part.getOpList().size();
				part.setStart(part.getOpList().get(0).getStart());
				part.setFinish(part.getOpList().get(opNum - 1).getFinish());
			}
		}
		double cmax = InstanceUtil.calcCmax(instance);
		// 结果输出
		// 利用率计算
		for (Machine machine : instance.getMachineMap().values()) {
			double util = 0;
			double load = 0;
			for (Operation operation : machine.getQueueList()) {
				load += operation.getWorkTime();
				util += operation.getRunTime() * operation.getPlanQty();
			}
			double loadRation = load / cmax;
			double utilRation = util / cmax;
			machine.setUtilRation(utilRation);
			machine.setLoadRation(loadRation);
		}
		instance.getObjective().calcValue(instance);
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
	@Override
	public void calEarlyStart(Instance instance, Operation operation) throws Exception {
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

	@Override
	public void initReadyTasks(Instance instance) throws Exception {
		// TODO Auto-generated method stub
		Comparator<Operation> operationTaskComparator = instance.getOperationComparator();
		if (operationTaskComparator == null)
			operationTaskComparator = new OperationSPT();
		// 设置工序优先级比较器
		TreeSet<Operation> readyTaskS = new TreeSet<Operation>(operationTaskComparator);
		// 重新排序
		readyTaskS.addAll(instance.getReadyTaskS());
		instance.setReadyTaskS(readyTaskS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#scheduleStep(mm_scheduler.instanceScheduler.instance
	 * .domain.basicdata.Instance, java.lang.Integer)
	 */
	@Override
	public double scheduleStep(Instance instance, Integer action) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#scheduleStep(mm_scheduler.instanceScheduler.instance
	 * .domain.basicdata.Instance,
	 * mm_scheduler.instanceScheduler.rules.basic.OperationRule)
	 */
	@Override
	public double scheduleStep(Instance instance, OperationRule operationRule) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mm_scheduler.instanceScheduler.instance.manager.iface.
	 * ScheduleManagerIface#doSchedule(mm_scheduler.instanceScheduler.instance.
	 * domain.basicdata.Instance, int)
	 */
	@Override
	public void doSchedule(Instance instance, int mode) throws Exception {
		// TODO Auto-generated method stub
		this.beforeSchedule(instance, mode);
		this.schedule(instance, mode);
		this.afterSchedule(instance, mode);
	}

}
