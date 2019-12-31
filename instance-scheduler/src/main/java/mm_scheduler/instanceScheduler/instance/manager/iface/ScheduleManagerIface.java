/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.manager.iface;

import java.util.List;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolution;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * @author: hba
 * @description: 调度管理器接口
 * @date: 2019年12月18日
 *
 */
public interface ScheduleManagerIface {

	/**
	 * 
	 * @author: hba
	 * @description:调度入口，进行完整的调度过程(调度前处理，执行调度，调度后处理)
	 * @param instance
	 * @param mode 模式（针对具有学习功能的算法，通过该值可以确定是离线训练还是在线应用）
	 * @throws Exception
	 * @date: 2019年12月25日
	 *
	 */
	public void doSchedule(Instance instance, int mode) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 按照不同的模式进行调度
	 * @param instance
	 * @param mode 模式（针对具有学习功能的算法，通过该值可以确定是离线训练还是在线应用）
	 * @date: 2019年12月18日
	 *
	 */
	public void schedule(Instance instance, int mode) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 清除调度结果
	 * @param instance
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void clearScheduleResult(Instance instance) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 深度克隆出一个新scheme,主要用于计算
	 * @param instance
	 * @return
	 * @date: 2019年12月18日
	 *
	 */
	public Instance cloneInstance(Instance instance) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 调度前处理
	 * @param instance
	 * @param mode
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void beforeSchedule(Instance instance, int mode) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 调度后处理
	 * @param instance
	 * @param mode
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void afterSchedule(Instance instance, int mode) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description:调度就绪任务初始化
	 * @param instance
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void initReadyTasks(Instance instance) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 计算工序最早开始
	 * @param instance
	 * @param operation
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void calEarlyStart(Instance instance, Operation operation) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 单步调度执行(规则编号)
	 * @param instance
	 * @param action
	 * @return
	 * @date: 2019年12月18日
	 *
	 */
	public double scheduleStep(Instance instance, Integer action);

	/**
	 * 
	 * @author: hba
	 * @description: 单步调度执行(规则)
	 * @param instance
	 * @param operationRule
	 * @return
	 * @date: 2019年12月18日
	 *
	 */
	public double scheduleStep(Instance instance, OperationRule operationRule);
}
