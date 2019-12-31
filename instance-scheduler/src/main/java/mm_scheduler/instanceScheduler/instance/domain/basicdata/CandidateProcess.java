package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import java.io.Serializable;

/**
 * 
 * @author: hba
 * @description: 用于描述可选设备（可选工艺），machine是机床，duration是对应在机床上的加工时间，
 *               将准备时间和加工时间设置在此的原因是，在柔性作业车间调度中，由于机床能力不同，所以同一道工序在不同的机床上加工时间是不同的
 * @date: 2019年12月26日
 *
 */
public class CandidateProcess implements Serializable {
	/**
	 * 设备编号
	 */
	private int machineID; // 设备的ID
	// private Machine machine;
	/**
	 * 总工时
	 */
	private double duration;
	/**
	 * 准备时间
	 */
	private double setupTime;
	/**
	 * 加工时间
	 */
	private double runTime;
	/**
	 * 设备名称
	 */
	private String machineName; // 设备名称

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getMachineID() {
		return machineID;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	/**
	 * @return the setupTime
	 */
	public double getSetupTime() {
		return setupTime;
	}

	/**
	 * @param setupTime
	 *            the setupTime to set
	 */
	public void setSetupTime(double setupTime) {
		this.setupTime = setupTime;
	}

	/**
	 * @return the runTime
	 */
	public double getRunTime() {
		return runTime;
	}

	/**
	 * @param runTime
	 *            the runTime to set
	 */
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

}
