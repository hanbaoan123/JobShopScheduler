package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import java.io.Serializable;

/**
 * 
 * @author: hba
 * @description: 工序类
 * @date: 2019年12月26日
 *
 */
public class Operation implements Serializable {
	/**
	 * 已分派状态
	 */
	public static final int Assigned_State = 1;
	/**
	 * 未分派状态
	 */
	public static final int Unassigned_state = 0;
	/**
	 * 工序号
	 */
	private int ID;
	/**
	 * 零件号
	 */
	private int partID;
	/**
	 * 零件
	 */
	private Part part;
	/**
	 * 工序名称
	 */
	private String name;
	/**
	 * 工序顺序（排序用）
	 */
	private int seq;
	/**
	 * 准备时间
	 */
	private double preTime;
	/**
	 * 单件时间
	 */
	private double runTime; // 选择工时
	/**
	 * 设备编号
	 */
	private int machineID; // 选择设备
	/**
	 * 固定的机床编号，用于解码时确定已安排的机床(-1表示未被安排机床)
	 */
	private int fixedMachineID = -1;
	/**
	 * 设备名称
	 */
	private String machineName; // 选择设备
	/**
	 * 可选设备数量
	 */
	private int candidateProNum; // 可选设备数量
	/**
	 * 权重
	 */
	private double weight;// 权重
	/**
	 * 交货期
	 */
	private int dueDate;
	/**
	 * 最早开始
	 */
	private double earlyStart;
	/**
	 * 开始
	 */
	private double start;
	/**
	 * 结束
	 */
	private double finish;
	/**
	 * 计划数，默认为1
	 */
	private int planQty = 1;
	/**
	 * 前置工序
	 */
	private Operation prepOp;
	/**
	 * 后置工序
	 */
	private Operation SuccOp;
	/**
	 * 状态0未调度 1已调度
	 */
	private int state;
	/**
	 * 剩余工序数
	 */
	private int remainOpNum;
	/**
	 * 剩余工时
	 */
	private double remainWorkTime;

	/**
	 * 工序优先级
	 */
	private double opPriority;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the partID
	 */
	public int getPartID() {
		return partID;
	}

	/**
	 * @param partID
	 *            the partID to set
	 */
	public void setPartID(int partID) {
		this.partID = partID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the preTime
	 */
	public double getPreTime() {
		return preTime;
	}

	/**
	 * @param preTime
	 *            the preTime to set
	 */
	public void setPreTime(double preTime) {
		this.preTime = preTime;
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

	/**
	 * @return the machineID
	 */
	public int getMachineID() {
		return machineID;
	}

	/**
	 * @param machineID
	 *            the machineID to set
	 */
	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * @param machineName
	 *            the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * @return the candidateProNum
	 */
	public int getCandidateProNum() {
		return candidateProNum;
	}

	/**
	 * @param candidateProNum
	 *            the candidateProNum to set
	 */
	public void setCandidateProNum(int candidateProNum) {
		this.candidateProNum = candidateProNum;
	}

	/**
	 * @return the dueDate
	 */
	public int getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(int dueDate) {
		this.dueDate = dueDate;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getFinish() {
		return finish;
	}

	public void setFinish(double finish) {
		this.finish = finish;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getPlanQty() {
		return planQty;
	}

	/**
	 * 临时使用，因为可能对于不同的设备工时不同 hba
	 * 
	 * @return 下午3:42:57
	 */
	public double getWorkTime() {
		return this.planQty * this.runTime + this.preTime;
	}

	public void setPlanQty(int planQty) {
		this.planQty = planQty;
	}

	public Operation getPrepOp() {
		return prepOp;
	}

	public void setPrepOp(Operation prepOp) {
		this.prepOp = prepOp;
	}

	public Operation getSuccOp() {
		return SuccOp;
	}

	public void setSuccOp(Operation succOp) {
		SuccOp = succOp;
	}

	public double getEarlyStart() {
		return earlyStart;
	}

	public void setEarlyStart(double earlyStart) {
		this.earlyStart = earlyStart;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRemainOpNum() {
		return remainOpNum;
	}

	public void setRemainOpNum(int remainOpNum) {
		this.remainOpNum = remainOpNum;
	}

	public double getRemainWorkTime() {
		return remainWorkTime;
	}

	public void setRemainWorkTime(double remainWorkTime) {
		this.remainWorkTime = remainWorkTime;
	}

	public double getOpPriority() {
		return opPriority;
	}

	public void setOpPriority(double opPriority) {
		this.opPriority = opPriority;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public int getFixedMachineID() {
		return fixedMachineID;
	}

	public void setFixedMachineID(int fixedMachineID) {
		this.fixedMachineID = fixedMachineID;
	}
}
