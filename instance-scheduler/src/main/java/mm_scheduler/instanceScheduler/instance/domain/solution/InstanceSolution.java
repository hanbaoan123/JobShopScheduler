package mm_scheduler.instanceScheduler.instance.domain.solution;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author: hba
 * @description: 案例调度结果类
 * @date: 下午8:04:07
 *
 */
public class InstanceSolution {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 创建时间
	 */
	private String creteTime = formatter.format(new Date());
	/**
	 * 案例名称
	 */
	private String instanceName;
	/**
	 * 算法名称
	 */
	private String algorithmName;
	/**
	 * 目标数量,默认为单目标
	 */
	private int objectiveNum = 1;
	/**
	 * 调度结果数组
	 */
	private double[] objectives = new double[objectiveNum];
	/**
	 * 求解时间(分钟)
	 */
	private double calcTime;

	/**
	 * 默认构造函数
	 */
	public InstanceSolution() {

	}

	/**
	 * 设置目标个数的构造函数
	 * 
	 * @param objectiveNum
	 */
	public InstanceSolution(int objectiveNum) {
		this.objectiveNum = objectiveNum;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public int getObjectiveNum() {
		return objectiveNum;
	}

	public void setObjectiveNum(int objectiveNum) {
		this.objectiveNum = objectiveNum;
		objectives = new double[objectiveNum];
	}

	public double[] getObjectives() {
		return objectives;
	}

	public void setObjectives(double[] objectives) {
		this.objectives = objectives;
	}

	public double getCalcTime() {
		return calcTime;
	}

	public void setCalcTime(double calcTime) {
		this.calcTime = calcTime;
	}

	public String getCreteTime() {
		return creteTime;
	}
}
