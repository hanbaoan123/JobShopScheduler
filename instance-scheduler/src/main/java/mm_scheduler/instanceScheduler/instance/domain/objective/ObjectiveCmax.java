/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.domain.objective;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;

/**
 * @author: hba
 * @description:制造期目标
 * @date: 2019年12月17日
 *
 */
public class ObjectiveCmax extends Objective {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mm_scheduler.instanceScheduler.instance.domain.iface.Objective#calcValue(
	 * mm_scheduler.instanceScheduler.instance.domain.Instance)
	 */
	@Override
	public double calcValue(Instance instance) {
		// TODO Auto-generated method stub
		double value = 0;
		for (Operation operation : instance.getOperationMap().values()) {
			if (operation.getFinish() > value) {
				value = operation.getFinish();
			}
		}
		this.objectiveValue = value;
		return value;
	}

	public ObjectiveCmax() {
		super();
		// TODO Auto-generated constructor stub
		this.objectiveName = "制造期(Cmax)";
	}
}
