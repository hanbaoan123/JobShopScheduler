/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.heuristic;

import java.util.List;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.FileHandle;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * @author: hba
 * @description: 多个简单规则调度器测试
 * @date: 2019年12月18日
 *
 */
public class MultiHeuristicSchedulerTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		for (Instance instance : instances) {
			for (int ruleID = 1; ruleID <= 34; ruleID++) {
				// 一次执行运行多个启发式规则,如果有自定义的规则，需要先在InstanceUtil的setInstanceOperationComparator(Instance
				// instance, int actionId)方法中注册
				HeuristicScheduler heuristicScheduler = new HeuristicScheduler(ruleID);
				heuristicScheduler.schedule(instance, 0);
				InstanceUtil.outputInstanceSolution(instance, instance.getOperationComparator().getRuleName());
			}
		}
	}
}
