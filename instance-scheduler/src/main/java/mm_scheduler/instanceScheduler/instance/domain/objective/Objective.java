/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.domain.objective;

import lombok.Getter;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;

/**
 * @author: hba
 * @description:调度目标基类
 * @date: 2019年12月17日
 *
 */
public class Objective {
	/**
	 * 目标名称
	 */
	public String objectiveName;
	/**
	 * 目标值
	 */
	public double objectiveValue;
	/**
	 * 上次目标值（用于单步调度）
	 */
	public double lastObjectiveValue;

	/**
	 * 
	 * @author: hba
	 * @description:计算目标值
	 * @param instance
	 * @return
	 * @date: 2019年12月17日
	 *
	 */
	public double calcValue(Instance instance) {
		return this.objectiveValue;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public double getObjectiveValue() {
		return objectiveValue;
	}

	public void setObjectiveValue(double objectiveValue) {
		this.objectiveValue = objectiveValue;
	}

	public double getLastObjectiveValue() {
		return lastObjectiveValue;
	}

	public void setLastObjectiveValue(double lastObjectiveValue) {
		this.lastObjectiveValue = lastObjectiveValue;
	}
}
