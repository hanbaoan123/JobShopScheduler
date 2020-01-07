/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.ga;

import java.util.List;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * @author: hba
 * @description: GA调度器测试（优先级编码）
 * @date: 2019年12月18日
 *
 */
public class GASchedulerPriorityCodeTest {

	/**
	 * @author: hba
	 * @description:
	 * @param args
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		for (Instance instance : instances) {
			GAScheduler gaScheduler = new GAScheduler(instance, GAScheduler.PRIORITY_CODE);
			gaScheduler.schedule();
		}
	}
}
