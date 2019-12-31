/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import mm_scheduler.instanceScheduler.instance.domain.objective.ObjectiveTWT;
import mm_scheduler.instanceScheduler.rules.*;

/**
 * @author: hba
 * @description:单机调度案例
 * @date: 2019年12月17日
 *
 */
public class SMInstance extends Instance {
	/**
	 * 对工件和工序进行初始化 hba 下午6:34:33
	 */
	public void init() {
		super.init();
		// 案例初始化时设定优化目标
		this.objective = new ObjectiveTWT();
		// 初始化可选规则
		//this.operationRules.add(new OperationSPT());
		//this.operationRules.add(new OperationLPT());
		//this.operationRules.add(new OperationGW());
		//this.operationRules.add(new OperationLW());
		//this.operationRules.add(new OperationWSPT());
		//this.operationRules.add(new OperationWLPT());
		this.operationRules.add(new OperationEDD());
	}
}
