package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author: hba
 * @description: 工件类
 * @date: 2019年12月26日
 *
 */
public class Part implements Serializable {
	/**
	 * 零件号
	 */
	private int ID;
	/**
	 * 零件名称
	 */
	private String name;
	/**
	 * 工序总数
	 */
	private int operationNum; // 工序数量
	/**
	 * 工序列表
	 */
	private List<Operation> opList; // 工艺路线
	/**
	 * 计划开始
	 */
	private double start;
	/**
	 * 计划结束
	 */
	private double finish;
	/**
	 * 权重
	 */
	private double weight;// 权重
	/**
	 * 交货期
	 */
	private Double dueDate;// 交货期
	/**
	 * 计划数，默认为1
	 */
	private int planQty = 1;
	/**
	 * 当前工序
	 */
	private Operation currOp;
	/**
	 * 总工时
	 */
	private double totalWorkTime;

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
	 * @return the operationNum
	 */
	public int getOperationNum() {
		return operationNum;
	}

	/**
	 * @param operationNum
	 *            the operationNum to set
	 */
	public void setOperationNum(int operationNum) {
		this.operationNum = operationNum;
	}

	/**
	 * @return the opList
	 */
	public List<Operation> getOpList() {
		return opList;
	}

	/**
	 * @param opList
	 *            the opList to set
	 */
	public void setOpList(List<Operation> opList) {
		this.opList = opList;
	}

	/**
	 * @return the start
	 */
	public double getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(double start) {
		this.start = start;
	}

	/**
	 * @return the finish
	 */
	public double getFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(double finish) {
		this.finish = finish;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the dueDate
	 */
	public Double getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Double dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the planQty
	 */
	public int getPlanQty() {
		return planQty;
	}

	/**
	 * @param planQty
	 *            the planQty to set
	 */
	public void setPlanQty(int planQty) {
		this.planQty = planQty;
	}

	public Operation getCurrOp() {
		return currOp;
	}

	public void setCurrOp(Operation currOp) {
		this.currOp = currOp;
	}

	public double getTotalWorkTime() {
		return totalWorkTime;
	}

	public void setTotalWorkTime(double totalWorkTime) {
		this.totalWorkTime = totalWorkTime;
	}
}
