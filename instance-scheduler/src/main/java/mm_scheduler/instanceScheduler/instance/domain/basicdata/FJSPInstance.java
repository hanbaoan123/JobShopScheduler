/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import mm_scheduler.instanceScheduler.instance.domain.objective.ObjectiveCmax;
import mm_scheduler.instanceScheduler.instance.domain.objective.ObjectiveTWT;
import mm_scheduler.instanceScheduler.rules.OperationEDD;

/**
 * @author: hba
 * @description: 柔性作业车间调度案例
 * @date: 2019年12月23日
 *
 */
public class FJSPInstance extends Instance {
	/**
	 * @author hba
	 *
	 *         上午9:52:24
	 */
	public void init() {
		super.init();
		// 案例初始化时设定优化目标
		this.objective = new ObjectiveCmax();
	}

}
