package mm_scheduler.instanceScheduler.instance.domain.solution;

import java.util.List;

/**
 * 
 * @author: hba
 * @description: 调度案例单步调度结果，即已安排的什么任务，在什么机床上，开始和结束时间是多少
 * @date: 下午8:05:07
 *
 */
public class InstanceSolutionStep {
	/**
	 * 任务号
	 */
	private String taskUid;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 开始时间
	 */
	private double start;
	/**
	 * 结束时间
	 */
	private double end;
	/**
	 * 设备索引
	 */
	private int deviceIndex;
	/**
	 * 机床总数
	 */
	private int machineNum;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 状态值
	 */
	private List state;
	/**
	 * 当前目标值
	 */
	private double currObjective;

	public String getTaskUid() {
		return taskUid;
	}

	public void setTaskUid(String taskUid) {
		this.taskUid = taskUid;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public int getDeviceIndex() {
		return deviceIndex;
	}

	public void setDeviceIndex(int deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(int machineNum) {
		this.machineNum = machineNum;
	}

	public double getCurrObjective() {
		return currObjective;
	}

	public void setCurrObjective(double currObjective) {
		this.currObjective = currObjective;
	}

	public List getState() {
		return state;
	}

	public void setState(List state) {
		this.state = state;
	}
}
