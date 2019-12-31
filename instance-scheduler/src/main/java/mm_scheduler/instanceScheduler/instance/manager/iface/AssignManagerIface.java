/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.manager.iface;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;

/**
 * @author: hba
 * @description: 工序分派管理器接口
 * @date: 2019年12月18日
 *
 */
public interface AssignManagerIface {
	/**
	 * 
	 * @author: hba
	 * @description: 工序分派
	 * @param operation
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void assignTask(Operation operation) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 工序分派后处理
	 * @param operation
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void afterAssign(Instance instance,Operation operation) throws Exception;

	/**
	 * 
	 * @author: hba
	 * @description: 从就绪任务集合中移除已调度工序
	 * @param operation
	 * @date: 2019年12月18日
	 *
	 */
	public void removeFromReadyTasks(Operation operation);
}
