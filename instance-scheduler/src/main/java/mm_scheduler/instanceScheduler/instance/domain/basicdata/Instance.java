package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import mm_scheduler.instanceScheduler.instance.domain.objective.ObjectiveCmax;
import mm_scheduler.instanceScheduler.instance.domain.objective.Objective;
import mm_scheduler.instanceScheduler.rules.OperationEDD;
import mm_scheduler.instanceScheduler.rules.OperationSPT;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * 
 * @author: hba
 * @description: 调度案例类，包含了所有和调度相关的信息：工件、工序、可选工艺、工时等
 * @date: 2019年12月26日
 *
 */
public class Instance implements Serializable {
	/**
	 * 单机
	 */
	public static final int SINGLE = 1;
	/**
	 * 并行机
	 */
	public static final int PARALLEL = 2;
	/**
	 * 流水
	 */
	public static final int FLOW = 3;
	/**
	 * 作业车间调度问题
	 */
	public static final int JSP = 4;
	/**
	 * 柔性作业车间调度问题
	 */
	public static final int FJSP = 5;
	/**
	 * 案例索引（从1开始）
	 */
	public long index;
	/**
	 * 案例编号
	 */
	public String instanceUid;
	/**
	 * 案例类型
	 */
	public int instanceType;
	/**
	 * 案例名称
	 */
	public String name;
	/**
	 * 总工时
	 */
	public double totalWorkTime;
	/**
	 * 总工序数
	 */
	public double totalOpNum;
	/**
	 * 零件数
	 */
	public int partNum;
	/**
	 * 机床数
	 */
	public int machineNum;
	/**
	 * 工序平均设备数
	 */
	protected float meanOpMachineNum;
	/**
	 * 可选设备集合（工序名称+可选设备列表）
	 */
	protected Map<String, List<CandidateProcess>> candidateProMap = new HashMap<>(); // 可选处理集合（可选设备以及对应的加工时间）
	/**
	 * 工序集合（工序名+工序）
	 */
	protected Map<String, Operation> operationMap = new HashMap<>();
	/**
	 * 设备集合（设备名+设备）
	 */
	protected Map<String, Machine> machineMap = new HashMap<>();
	/**
	 * 零件集合（零件名称+零件）
	 */
	protected Map<String, Part> partMap = new HashMap<>();

	/**
	 * 上一步的调度目标值
	 */
	protected double lastObjectiveValue;

	/**
	 * 就绪任务集合
	 */
	protected TreeSet<Operation> readyTaskS = new TreeSet<Operation>(new OperationSPT());
	/**
	 * 工序任务比较器
	 */
	protected OperationRule operationComparator = new OperationEDD();
	/**
	 * 算法名称 hba
	 * 
	 * @return 上午9:35:38
	 */
	protected String altorighmName;
	/**
	 * 是否保存动态分派
	 */
	protected boolean wirteDynamic = false;
	/**
	 * 已分派的总工时（计算奖励时使用）
	 */
	protected double totalAssignedTaskWork;
	/**
	 * 调度目标
	 */
	protected Objective objective;
	/**
	 * 案例调度可选的动作或规则集合
	 */
	protected List<OperationRule> operationRules = new ArrayList<OperationRule>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPartNum() {
		return partNum;
	}

	public void setPartNum(int partNum) {
		this.partNum = partNum;
	}

	public int getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(int machineNum) {
		this.machineNum = machineNum;
	}

	/**
	 * @return the meanOpMachineNum
	 */
	public float getMeanOpMachineNum() {
		return meanOpMachineNum;
	}

	/**
	 * @param meanOpMachineNum
	 *            the meanOpMachineNum to set
	 */
	public void setMeanOpMachineNum(float meanOpMachineNum) {
		this.meanOpMachineNum = meanOpMachineNum;
	}

	/**
	 * @return the candidateProMap
	 */
	public Map<String, List<CandidateProcess>> getCandidateProMap() {
		return candidateProMap;
	}

	/**
	 * @param candidateProMap
	 *            the candidateProMap to set
	 */
	public void setCandidateProMap(Map<String, List<CandidateProcess>> candidateProMap) {
		this.candidateProMap = candidateProMap;
	}

	/**
	 * @return the operationMap
	 */
	public Map<String, Operation> getOperationMap() {
		return operationMap;
	}

	/**
	 * @param operationMap
	 *            the operationMap to set
	 */
	public void setOperationMap(Map<String, Operation> operationMap) {
		this.operationMap = operationMap;
	}

	/**
	 * @return the machineMap
	 */
	public Map<String, Machine> getMachineMap() {
		return machineMap;
	}

	/**
	 * @param machineMap
	 *            the machineMap to set
	 */
	public void setMachineMap(Map<String, Machine> machineMap) {
		this.machineMap = machineMap;
	}

	/**
	 * @return the partMap
	 */
	public Map<String, Part> getPartMap() {
		return partMap;
	}

	/**
	 * @param partMap
	 *            the partMap to set
	 */
	public void setPartMap(Map<String, Part> partMap) {
		this.partMap = partMap;
	}

	public String getInstanceUid() {
		return instanceUid;
	}

	public void setInstanceUid(String instanceUid) {
		this.instanceUid = instanceUid;
	}

	public int getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(int instanceType) {
		this.instanceType = instanceType;
	}

	public void reset() {
		// TODO Auto-generated method stub
		this.lastObjectiveValue = 0;
		this.getObjective().setObjectiveValue(0);
		this.totalAssignedTaskWork = 0;
		// 括号内可设置比较器
		readyTaskS = new TreeSet<Operation>(new OperationEDD());
		for (Part part : partMap.values()) {
			part.setCurrOp(part.getOpList().get(0));
			readyTaskS.add(part.getCurrOp());
		}
		for (Operation operation : operationMap.values()) {
			// 清除计划时间
			operation.setStart(0);
			operation.setFinish(0);
			// 清除已安排设备
			operation.setMachineID(-1);
			operation.setMachineName("");
			// 初始为未调度状态
			operation.setState(Operation.Unassigned_state);
			operation.setFixedMachineID(-1);
		}
		for (Machine machine : machineMap.values()) {
			// 清除设备已安排任务
			machine.getQueueList().clear();
			// 清除工时
			machine.setAssignedTaskWork(0);
			// 清除负荷率和利用率
			machine.setLoadRation(0);
			machine.setUtilRation(0);
		}
		for (Part part : partMap.values()) {
			part.setStart(0);
			part.setFinish(0);
		}

		// this.altorighmName = "";
		// this.wirteDynamic = false;
	}

	/**
	 * 对工件和工序进行初始化 hba 下午6:34:33
	 */
	public void init() {
		// 默认目标为制造期
		this.objective = new ObjectiveCmax();
		operationMap.clear();
		for (Part part : partMap.values()) {
			double remainWork = 0d;
			int iSize = part.getOpList().size();
			for (int j = iSize - 1; j >= 0; j--) {
				Operation operation = part.getOpList().get(j);
				remainWork += operation.getWorkTime();
				operation.setRemainWorkTime(remainWork);
				// 刷新剩余工序数
				operation.setRemainOpNum(iSize - j);
				operation.setPart(part);
				// System.out.println("工序：" +
				// operationMap.get(operation.getName()).getName());
				if (iSize > 1) {
					if (j == iSize - 1) {
						operation.setPrepOp(part.getOpList().get(j - 1));
					} else if (j == 0) {
						operation.setSuccOp(part.getOpList().get(j + 1));
					} else {
						operation.setPrepOp(part.getOpList().get(j - 1));
						operation.setSuccOp(part.getOpList().get(j + 1));
					}
				}
				operationMap.put(operation.getName(), operation);
			}
			part.setTotalWorkTime(remainWork);
		}
		class AssginedOpComp implements Comparator<Operation>, Serializable {
			@Override
			public int compare(Operation o1, Operation o2) {
				// TODO Auto-generated method stub
				if (o1.getStart() > o2.getStart()) {
					return 1;
				} else if (o1.getStart() < o2.getStart()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		// 设置机床上已安排任务的比较器
		for (Machine machine : machineMap.values()) {
			TreeSet<Operation> queueList = new TreeSet<Operation>(new AssginedOpComp());
			machine.setQueueList(queueList);
		}
	}

	public TreeSet<Operation> getReadyTaskS() {
		return readyTaskS;
	}

	public void setReadyTaskS(TreeSet<Operation> readyTaskS) {
		this.readyTaskS = readyTaskS;
	}

	public String getInstanceCategory() {
		return instanceType + "_" + partNum + "x" + machineNum + "sample";
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public OperationRule getOperationComparator() {
		return operationComparator;
	}

	public void setOperationComparator(OperationRule operationComparator) {
		this.operationComparator = operationComparator;
	}

	public double getTotalWorkTime() {
		return totalWorkTime;
	}

	public void setTotalWorkTime(double totalWorkTime) {
		this.totalWorkTime = totalWorkTime;
	}

	public double getTotalOpNum() {
		return totalOpNum;
	}

	public void setTotalOpNum(double totalOpNum) {
		this.totalOpNum = totalOpNum;
	}

	public String getAltorighmName() {
		return altorighmName;
	}

	public void setAltorighmName(String altorighmName) {
		this.altorighmName = altorighmName;
	}

	public boolean isWirteDynamic() {
		return wirteDynamic;
	}

	public void setWirteDynamic(boolean wirteDynamic) {
		this.wirteDynamic = wirteDynamic;
	}

	public double getTotalAssignedTaskWork() {
		return totalAssignedTaskWork;
	}

	public void setTotalAssignedTaskWork(double totalAssignedTaskWork) {
		this.totalAssignedTaskWork = totalAssignedTaskWork;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public List<OperationRule> getOperationRules() {
		return operationRules;
	}

	public void setOperationRules(List<OperationRule> operationRules) {
		this.operationRules = operationRules;
	}

	public double getLastObjectiveValue() {
		return lastObjectiveValue;
	}

	public void setLastObjectiveValue(double lastObjectiveValue) {
		this.lastObjectiveValue = lastObjectiveValue;
	}

	public Objective getObjective() {
		return objective;
	}
}
