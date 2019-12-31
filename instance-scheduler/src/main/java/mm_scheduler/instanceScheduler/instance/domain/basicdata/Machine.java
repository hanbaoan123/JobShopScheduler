package mm_scheduler.instanceScheduler.instance.domain.basicdata;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import lombok.AllArgsConstructor;

/**
 * 
 * @author: hba
 * @description: 机床类
 * @date: 2019年12月26日
 *
 */
public class Machine implements Serializable {
	private int ID;
	private String name;
	/**
	 * 机床缓存，需要根据学习到的策略确定比较器
	 */
	transient private TreeSet<Operation> buffer = new TreeSet<Operation>();

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

	// 队列结果
	private TreeSet<Operation> queueList = new TreeSet<Operation>(new AssginedOpComp());

	public Machine(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	/**
	 * 机床已安排的工时
	 */
	private double assignedTaskWork;
	/**
	 * 利用率
	 */
	private double utilRation;
	/**
	 * 负荷率
	 */
	private double loadRation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public double getUtilRation() {
		return utilRation;
	}

	public void setUtilRation(double utilRation) {
		this.utilRation = utilRation;
	}

	public double getLoadRation() {
		return loadRation;
	}

	public void setLoadRation(double loadRation) {
		this.loadRation = loadRation;
	}

	public TreeSet<Operation> getQueueList() {
		return queueList;
	}

	public void setQueueList(TreeSet<Operation> queueList) {
		this.queueList = queueList;
	}

	public double getAssignedTaskWork() {
		return assignedTaskWork;
	}

	public void setAssignedTaskWork(double assignedTaskWork) {
		this.assignedTaskWork = assignedTaskWork;
	}

}
