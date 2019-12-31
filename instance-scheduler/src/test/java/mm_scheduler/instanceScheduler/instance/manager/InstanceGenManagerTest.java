/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.manager;

/**
 * @author: hba
 * @description:单机benchmarks格式化测试
 * @date: 2019年12月16日
 *
 */
public class InstanceGenManagerTest {

	/**
	 * @author: hba
	 * @description:
	 * @param args
	 * @throws Exception
	 * @date: 2019年12月16日
	 *
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InstanceGenManager instanceGenManager = new InstanceGenManager();
		instanceGenManager.genSingleMachineInstance("D:\\Personal\\Desktop\\单机调度案例\\wt40.txt", 125, 40);
		instanceGenManager.genSingleMachineInstance("D:\\Personal\\Desktop\\单机调度案例\\wt50.txt", 125, 50);
		instanceGenManager.genSingleMachineInstance("D:\\Personal\\Desktop\\单机调度案例\\wt100.txt", 125, 100);
	}
}
