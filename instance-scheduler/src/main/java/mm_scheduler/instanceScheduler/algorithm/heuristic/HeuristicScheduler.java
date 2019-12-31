/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.heuristic;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.manager.ScheduleManager;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * @author: hba
 * @description: 简单规则调度器入口
 * @date: 2019年12月18日
 *
 */
public class HeuristicScheduler {
	private ScheduleManager scheduleManager = new HeuristicScheduleManager();
	/**
	 * 是否执行多规则调度。默认不执行
	 */
	private boolean multiRules = false;
	/**
	 * 规则
	 */
	private OperationRule operationRule;
	/**
	 * 规则编号
	 */
	private int ruleID;

	public HeuristicScheduler(OperationRule operationRule) {
		super();
		this.operationRule = operationRule;
		multiRules = false;
	}

	public HeuristicScheduler(int ruleID) {
		super();
		this.ruleID = ruleID;
		multiRules = true;
	}

	/**
	 * 
	 * @author: hba
	 * @description: 执行调度
	 * @param instance
	 * @param mode
	 *            模式
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void schedule(Instance instance, int mode) throws Exception {
		long lStart = System.currentTimeMillis();
		try {
			// 设置比较器
			if (multiRules) {
				InstanceUtil.setInstanceOperationComparator(instance, ruleID);
			} else {
				InstanceUtil.setInstanceOperationComparator(instance, operationRule);
			}
			// 调度前处理
			scheduleManager.beforeSchedule(instance, mode);
			// 设置算法名称
			instance.setAltorighmName(instance.getOperationComparator().getRuleName());
			// 调度
			scheduleManager.schedule(instance, mode);
			// 调度后处理
			scheduleManager.afterSchedule(instance, mode);
			// 打印结果
			InstanceUtil.printResult(instance);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		long lEnd = System.currentTimeMillis();
		double diff = (lEnd - lStart) / 1000.0;// 得到两者的秒数
		System.out.println("调度用时:" + diff + "秒");
	}

	public OperationRule getOperationRule() {
		return operationRule;
	}

	public void setOperationRule(OperationRule operationRule) {
		this.operationRule = operationRule;
	}
}
