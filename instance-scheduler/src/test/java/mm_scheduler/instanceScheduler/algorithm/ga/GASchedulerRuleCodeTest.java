/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.ga;

import java.util.List;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * @author: hba
 * @description: GA调度器测试(规则编码)，所使用的规则需要在对应类型案例的构造方法中添加,如{@link  SMInstance.class#init()}
 * @date: 2019年12月18日
 *
 */
public class GASchedulerRuleCodeTest {

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
			GAScheduler gaScheduler = new GAScheduler(instance, GAScheduler.RULE_CODE);
			gaScheduler.schedule();
		}
	}
}
