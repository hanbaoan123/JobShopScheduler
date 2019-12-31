/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.domain.objective;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;

/**
 * @author: hba
 * @description:加权拖期目标
 * @date: 2019年12月17日
 *
 */
public class ObjectiveTWT extends Objective {
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
		for (Part part : instance.getPartMap().values()) {
			if (part.getFinish() > part.getDueDate()) {
				value += part.getWeight() * (part.getFinish() - part.getDueDate());
			}
		}
		objectiveValue = value;
		return objectiveValue;
	}

	public ObjectiveTWT() {
		super();
		// TODO Auto-generated constructor stub
		this.objectiveName = "总加权拖期(TWT)";
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}
}
